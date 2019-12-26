//////////Temporary///

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;

class Cash {
  
  private BufferedImage sprite;
  private int xCord;
  private int yCord;
  private int eWidth;
  private int eHeight;
  private Rectangle hitbox;
  
  Cash(int xCord, int yCord){
    this.xCord = xCord;
    this.yCord = yCord;
    loadSprite();
    eWidth = sprite.getWidth();
    eHeight = sprite.getHeight();
    hitbox = new Rectangle(xCord, yCord, eWidth, eHeight);
  }
  
  public void loadSprite() {
    try {
      sprite = ImageIO.read(new File("cash.png"));
    } catch(Exception e) { System.out.println("Error Loading Sprite...");};
  }
  
  public void draw(Graphics g) {
    
    g.drawImage(sprite, xCord, yCord, null);
    g.setColor(Color.RED);
    g.drawRect(xCord, yCord, eWidth, eHeight);
    
//    g.drawImage(sprite, xCord, yCord+3, null);
//    g.setColor(Color.RED);
//    g.drawRect(xCord, yCord+3, eWidth, eHeight);
    
  }
  
}