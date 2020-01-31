import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Explosion.java]
* a detonation generated by a rocket
* subclass to Detonation
* contains overridden draw and getFrames methods
* @author Ethan Zhang
**/
class Explosion extends Detonation {
  private BufferedImage sheet;
  private BufferedImage[] sprites;
  private final int size = 500, rows = 7, columns = 10;
  
  /**
  * Explosion constructor 
  * inherits xCoordinate, yCoordinate from Entity
  * sets hitbox
  */
  Explosion(int xCord, int yCord) {
    super(xCord, yCord);
    sprites = new BufferedImage[rows * columns];
    loadSprites();
    seteWidth(sprites[0].getWidth());
    seteHeight(sprites[0].getHeight());
    setHitbox(new Rectangle(xCord + 60, yCord + 60, size - 120, size - 120));
  }
  
  /**
  * loadSprites
  * attempts to open the necessary image(s)
  * catches error if not possible
  */
  private void loadSprites() {
    try {
      sheet = ImageIO.read(new File("explosionSheet.png"));
      
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          sprites[(i * columns) + j] = sheet.getSubimage(j * size, i * size, size, size);
        }
      }
      
    } catch(Exception e) { System.out.println("Error Loading 'explosionSheet.png'...");}
  }
  
  @Override
  public void draw(Graphics g) {
    g.drawImage(sprites[getSprite()], getxCord(), getyCord(), null);
  }
  
  @Override
  public int getFrames() {
    return sprites.length;
  }
}