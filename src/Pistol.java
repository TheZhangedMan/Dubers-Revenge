import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;

/**
* [Pistol.java]
* a weak but reliable weapon that shoots bullets
* subclass to Weapon
* contains overridden draw, move and reload methods
* @author Kyle Zhou, Ethan Zhang
* Kyle Zhou - Reloading, Revisions
* Ethan Zhang - Foundation, Spritework
**/

class Pistol extends Weapon {
  private BufferedImage sheet, spriteRight, spriteLeft;
  
  /**
  * Pistol constructor 
  * inherits xCoordinate, yCoordinate, damage, totalAmmo, clip and reloadSpeed from weapon
  */
  Pistol(int xCord, int yCord, int damage, int totalAmmo, int clip, long reloadSpeed){
    super(xCord, yCord, damage, totalAmmo, clip, reloadSpeed);
    loadSprites();
    setDirection(true);
  }
  
  /**
  * loadSprites 
  * attempts to load all images required for pistol
  * catches error if not possible
  */
  private void loadSprites() {
    try {
      sheet = ImageIO.read(new File("pistolSheet.png"));
      spriteRight = sheet.getSubimage(0, 0, 36, 20);
      spriteLeft = sheet.getSubimage(36, 0, 36, 20);
    } catch(Exception e) {System.out.println("Error Loading 'pistolSheet.png'...");};
  }
  
  @Override 
  public void draw(Graphics g) {
    if (getDirection() == true) {
      g.drawImage(spriteRight, getxCord(), getyCord(), null);
    } else {
      g.drawImage(spriteLeft, getxCord(), getyCord(), null);
    }
  }
  
  @Override
  public void move(Human duber) {
    if (duber.getMoveLock() == false){
      setxCord(duber.getxCentre());
      setyCord(duber.getyCentre() - 10);
    }
  }
  
  @Override
  public void reload(){
    if ((getClip() < getOriginalClip()) && (getTotalAmmo() > 0)) {
      if (getTotalAmmo()+getClip() >= getOriginalClip()){
        setTotalAmmo(getTotalAmmo()-(getOriginalClip()-getClip()));
        setClip(getClip()+(getOriginalClip()-getClip()));
        if (getTotalAmmo() < 0){
          setTotalAmmo(0);
        }
      } else {
        setClip(getClip()+getTotalAmmo());
        setTotalAmmo(0);
      }
      setReloading(false);
      setShootingLock(false);
    }
  }
}