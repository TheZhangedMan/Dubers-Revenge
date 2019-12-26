import javax.swing.JFrame;
import java.awt.Toolkit;
import java.util.Random;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;

class Game extends JFrame {

  //class variable (non-static)
  static int maxX, maxY, GridToScreenRatio;
  static double x, y;
  private static GamePanel gamePanel;
  private Mammal[] entities;
  private Projectile[] projectiles;
  private Obstacle[] obstacles;
  private Cash[] cash;
  private Human duber;
  private Random random = new Random();
  private Spawner spawner;
  private Camera scroll;
  private int waveNum = 0;
  private int elapse = 0;
  Game(String title) {

    super(title);
    
    cursor();
    
    entities = new Mammal[255];
    projectiles = new Projectile[255];
    obstacles = new Obstacle[255];
    cash = new Cash[255];
    scroll = new Camera(0, 0);
    spawner = new Spawner(entities, projectiles, obstacles, cash);
    spawner.spawnHuman(900, 600, 100, 25, 2); //change coords
    entities = spawner.getEntities();
    duber = ((Human)entities[0]);
    
    spawner.spawnNorth(0, 0, 4500, 280, 200);

    //insert other walls
    obstacles = spawner.getObstacles();
    
    // Set the frame to full screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());

    gamePanel = new GamePanel(duber, spawner, scroll, entities, projectiles, obstacles, cash);
    this.add(new GamePanel(duber, spawner, scroll, entities, projectiles, obstacles, cash));

    Keys keyListener = new Keys(duber);
    this.addKeyListener(keyListener);

    Mouse mouseListener = new Mouse(duber, spawner, scroll);
    this.addMouseListener(mouseListener);

    this.requestFocusInWindow(); //make sure the frame has focus

    this.setVisible(true);

    this.add(new GamePanel(duber, spawner, scroll, entities, projectiles, obstacles, cash));

    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    t.start();
  }


  public void refresh() {
    this.repaint();
    scroll.xFollow(duber, Toolkit.getDefaultToolkit().getScreenSize().width / 2);
    scroll.yFollow(duber, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
  }

  //the main gameloop - this is where the game state is updated
  public void animate() {

    while(true){
      this.x = (Math.random()*1024);  //update coords
      this.y = (Math.random()*768);
      try{ Thread.sleep(500);} catch (Exception exc){}  //delay

      if(elapse%30==0 || elapse==0) {
        int num = random.nextInt(10)+1;//from 1 to 10
        System.out.println("Spawn "+ num); //remove later
        nextWave(num);
        waveNum++;
      }
      elapse++;
      this.repaint();
    }
  }
  
  public void cursor(){
    setCursor(Cursor.getDefaultCursor());
    Image crosshair = Toolkit.getDefaultToolkit().getImage("crosshair.png");
    Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(crosshair, new Point(this.getX(), this.getY()-5), "img");
    this.setCursor(c);
  }
  
  public void nextWave(int number) {
    waveNum++;
    int range = 75;
    for (int i = 0; i < number; i++) {
      int xRange = random.nextInt(401)+100;//x Ranges from 100-500
      int yRange = random.nextInt(451)+50;//y Ranges from 50-500

      spawner.spawnZombie(xRange, yRange, 100, 10, 1);
    }
  }
}
