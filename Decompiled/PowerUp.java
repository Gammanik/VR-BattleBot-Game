import java.awt.Color;
import java.awt.Rectangle;

public class PowerUp
{
  Rectangle box;
  int powerlevel;
  int PWidth;
  int PHeight;
  double x;
  double y;
  boolean done = false;
  
  int type;
  int pid;
  Color me;
  int powernum = 8;
  
  public PowerUp() {
    type = -1;
    pid = -1;
  }
  
  public PowerUp(int oid, int PW, int PH, BattlePanel bp) { pid = oid;
    PWidth = PW;
    PHeight = PH;
    java.util.Random r = new java.util.Random();
    int x = r.nextInt(PW - 10);
    int y = r.nextInt(PH - 10);
    box = new Rectangle(x, y, 10, 10);
    










    type = r.nextInt(powernum);
    

    switch (type) {
    case 0:  me = new Color(255, 0, 0); break;
    case 1:  me = new Color(0, 0, 255); break;
    case 2:  me = new Color(128, 0, 128); break;
    case 3:  me = new Color(255, 165, 0); break;
    case 4:  me = new Color(0, 128, 0); break;
    case 5:  me = new Color(255, 0, 255); break;
    case 6:  me = new Color(255, 192, 203); break;
    case 7:  me = new Color(255, 255, 0); break;
    case 8:  me = new Color(128, 128, 128); break;
    case 9:  me = new Color(211, 211, 211); break;
    case 10:  me = new Color(169, 169, 169); break;
    case 11:  me = new Color(0, 255, 0); break;
    case 12:  me = new Color(128, 128, 0); break;
    case 13:  me = new Color(0, 255, 255); break;
    case 14:  me = new Color(128, 128, 0); break;
    case 15:  me = new Color(0, 0, 128); break;
    case 16:  me = new Color(218, 165, 32); break;
    case 17:  me = new Color(85, 107, 47); break;
    case 18:  me = new Color(240, 230, 140); break;
    case 19:  me = new Color(70, 130, 180); break;
    case 20:  me = new Color(153, 50, 204); break;
    default:  me = Color.black;
    }
    System.out.println("Powerup type is " + type);
  }
  
  public void hit() {
    done = true;
  }
  
  public boolean move(BattlePanel bp) {
    return true;
  }
  
  public void draw(java.awt.Graphics g) {
    g.setColor(me);
    
    g.drawRect(box.x, box.y, box.width, box.height);
    g.drawLine(box.x, box.y, box.x + box.width, box.y + box.height);
    g.drawLine(box.x + box.width, box.y, box.x, box.y + box.height);
  }
  
  public Rectangle getBounds()
  {
    Rectangle r = new Rectangle(box.x, box.y, box.width, box.height);
    return r;
  }
  
  public java.awt.geom.Rectangle2D getBounds2() {
    java.awt.geom.Rectangle2D.Double r = new java.awt.geom.Rectangle2D.Double(box.x, box.y, box.width, box.height);
    return r;
  }
}