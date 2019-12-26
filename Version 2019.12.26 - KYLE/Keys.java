import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener{

  private Human duber;
  Keys(Human duber){
    this.duber = duber;
  }
  
  public void keyTyped(KeyEvent e) {
  }

  public void keyPressed(KeyEvent e) {
    if ((e.getKeyChar() == 'a') && (duber.getxCord() > 0)) {  //If 'A' is pressed
      duber.setxDirection(-duber.getSpeed());
    } else if (e.getKeyChar() == 's' ){  //If 'S' is pressed
      duber.setyDirection(duber.getSpeed());
    } else if (e.getKeyChar() == 'd' ){  //If 'D' is pressed
      duber.setxDirection(duber.getSpeed());
    } else if ((e.getKeyChar() == 'w') && (duber.getyCord() > 0)) { //If 'W' is pressed
      duber.setyDirection(-duber.getSpeed());
    }
  }
    
  public void keyReleased(KeyEvent e) {
      
    if(e.getKeyChar() == 'a' ){    //Good time to use a Switch statement
      duber.setxDirection(0);
    } else if(e.getKeyChar() == 's' ){
      duber.setyDirection(0);
    } else if(e.getKeyChar() == 'd' ){
      duber.setxDirection(0);
    } else if(e.getKeyChar() == 'w' ){
      duber.setyDirection(0);
    }  //note - would be better to make player class and pass in map, test movement in there
  }
}