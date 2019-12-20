//Graphics &GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class Map extends JFrame {
  
  //class variable (non-static)
  static int maxX, maxY, GridToScreenRatio;
  static double x, y;
  private int[][] map;
  static GamePanel gamePanel;
  
  Human duber = new Human(Toolkit.getDefaultToolkit().getScreenSize().width / 2, 
                          Toolkit.getDefaultToolkit().getScreenSize().height / 2, 
                          100, 5, 2);
  Zombie zombie = new Zombie(100, 100, 100, 10, 1);
  
  Map(String title, int[][] map2) {
    
    super(title);
    map = map2;
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    
    this.maxX = Toolkit.getDefaultToolkit().getScreenSize().width;
    this.maxY = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    GridToScreenRatio = (maxY) / (map.length + 1);
    gamePanel = new GamePanel();
    this.add(new GamePanel());
    
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
    
    this.add(new GamePanel());
    //Start the game loop in a separate thread
    //Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop
    //t.start();
    
  }
  
  
  public void refresh() {
    this.repaint();
  }
  
  //the main gameloop - this is where the game state is updated
  public void animate() {
    
    while(true){
      this.x = (Math.random()*1024);  //update coords
      this.y = (Math.random()*768);
      this.repaint();
    }
  }
  
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  private class GamePanel extends JPanel {
    
    
    
    
    GamePanel() {
      addMouseListener(new MyMouseListener());
      addKeyListener(new MyKeyListener());
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g); //required
      setDoubleBuffered(true);
      //g.setColor(Color.BLUE); //There are many graphics commands that Java can use
      //g.fillRect((int)x, (int)y, 50, 50); //notice the x,y variables that we control from our animate method
      for(int i = 0; i < 25; i++) {
        for(int j = 0; j < 25; j++) {
          if(map[i][j] == 1) {
            g.setColor(Color.green);
            g.fillRect(j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          } else if (map[i][j] == 2){//if(map[i][j] == 2) {
            g.setColor(Color.cyan);
            g.fillRect(j * GridToScreenRatio, i * GridToScreenRatio, GridToScreenRatio, GridToScreenRatio);
          } 
        }
      }
      zombie.draw(g);
      
      if (zombie.getCooldown() > 0) {
        zombie.setCooldown(zombie.getCooldown() - 1);
      }
      
      if (duber != null) {
        duber.draw(g);
        duber.move();
        if (zombie.hitbox.intersects(duber.hitbox)) {
          zombie.xDirection = 0;
          zombie.yDirection = 0;
          if (zombie.getCooldown() == 0) {
            zombie.attack(duber);
          }
        } else if ((zombie.xCord >= duber.xCord) && (zombie.yCord >= duber.yCord)) {
          zombie.xDirection = -zombie.speed;
          zombie.yDirection = -zombie.speed;
          zombie.move();
        } else if ((zombie.xCord <= duber.xCord) && (zombie.yCord >= duber.yCord)) {
          zombie.xDirection = zombie.speed;
          zombie.yDirection = -zombie.speed;
          zombie.move();
        } else if ((zombie.xCord <= duber.xCord) && (zombie.yCord <= duber.yCord)) {
          zombie.xDirection = zombie.speed;
          zombie.yDirection = zombie.speed;
          zombie.move();
        } else if ((zombie.xCord >= duber.xCord) && (zombie.yCord <= duber.yCord)) {
          zombie.xDirection = -zombie.speed;
          zombie.yDirection = zombie.speed;
          zombie.move();
        }
        
        if (duber.health <= 0) {
          duber = null;
        }
      }
    }
    
  }
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    
    public void keyTyped(KeyEvent e) {
    }
    
    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      
      if(e.getKeyChar() == 'a' ){
        duber.xDirection = -duber.speed;
      } else if(e.getKeyChar() == 's' ){  //If 'S' is pressed
        duber.yDirection = duber.speed;
      } else if(e.getKeyChar() == 'd' ){  //If 'D' is pressed
        duber.xDirection = duber.speed;
      } else if(e.getKeyChar() == 'w' ){  //If 'W' is pressed
        duber.yDirection = -duber.speed;
      }
    }
    
    public void keyReleased(KeyEvent e) {
      
      if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
        duber.xDirection=0;
      } else if(e.getKeyChar() == 's' ){
        duber.yDirection=0;
      } else if(e.getKeyChar() == 'd' ){
        duber.xDirection=0;
      } else if(e.getKeyChar() == 'w' ){
        duber.yDirection=0;
      }  //note - would be better to make player class and pass in map, test movement in there
      
    }
    
  } //end of keyboard listener
  
  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      System.out.println("Mouse Clicked");
      System.out.println("X:"+e.getX() + " y:"+e.getY());
    }
    
    public void mousePressed(MouseEvent e) {
      
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
  } //end of mouselistener
  
}
