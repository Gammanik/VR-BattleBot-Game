import java.awt.Graphics;
import java.awt.Rectangle;

public class Shot
{
  Rectangle bullet;
  int powerlevel;
  int speed;
  int distance;
  int direction;
  int pid;
  int PWidth;
  int PHeight;
  static int idcnt = 0;
  int id;
  double RadianAngle;
  double x;
  double y; boolean done = false;
  int type;
  int target;
  
  public Shot() { pid = 0;
    powerlevel = 0;
    speed = 0;
    distance = 0;
    direction = -1;
    id = -1;
    type = 0;
    target = -1;
  }
  
  public Shot(int oid, int PW, int PH, int sx, int sy, int pl, int dir, int stype, BattlePanel bp, int optdist, int shotspeed) { pid = oid;
    PWidth = PW;
    PHeight = PH;
    powerlevel = pl;
    type = stype;
    
    speed = shotspeed;
    
    distance = ((int)Math.round(
      Math.sqrt(Math.pow(PWidth, 2.0D) + Math.pow(PHeight, 2.0D)) / (pl * 1.0D)));
    
    if (stype == 2)
    {
      if (distance > optdist) {
        distance = optdist;
      }
      if (oid == dir) {
        System.out.println("Why are we firing at ourselfs?");
      }
      else
      {
        target = dir;
      }
      stype = 1;
      type = 1;
    }
    

    id = (idcnt++);
    sx += 5;sy += 5;
    
    if (type == 1)
    {


      if (BatBot.pnum >= dir)
      {

        if (bots[dir].alive)
        {
          target = dir;
          
          direction = Angle(sx, sy, bots[target].box.x + 5, bots[target].box.y + 5);
        }
        else
        {
          stype = 0;
          direction = dir;
        }
      } else
        direction = dir;
      stype = 0;
    }
    else {
      dir -= 90; if (dir < 0) dir = 360 + dir;
      direction = dir;
    }
    
    RadianAngle = (dir * 3.141592653589793D / 180.0D);
    bullet = new Rectangle(sx, sy, powerlevel + 1, powerlevel + 1);
    x = (sx * 1.0D);
    y = (sy * 1.0D);
  }
  
  public void hit() { done = true;
    distance = 5;
  }
  
  public int Angle(int x1, int y1, int x2, int y2) {
    float dx = x2 - x1;
    float dy = y2 - y1;
    double angle = 0.0D;
    

    if (dx == 0.0D) {
      if (dy == 0.0D) {
        angle = 0.0D;
      } else if (dy > 0.0D) {
        angle = 1.5707963267948966D;
      } else
        angle = 4.71238898038469D;
    } else if (dy == 0.0D) {
      if (dx > 0.0D) {
        angle = 0.0D;
      } else {
        angle = 3.141592653589793D;
      }
    } else if (dx < 0.0D) {
      angle = Math.atan(dy / dx) + 3.141592653589793D;
    } else if (dy < 0.0D) {
      angle = Math.atan(dy / dx) + 6.283185307179586D;
    } else {
      angle = Math.atan(dy / dx);
    }
    

    angle = angle * 180.0D / 3.141592653589793D;
    

    return (int)angle;
  }
  
  public boolean move(BattlePanel bp) {
    distance -= speed;
    

    if (distance <= 0) {
      done = true;
      return done;
    }
    if (type == 1)
    {

      if (bots[target].alive) {
        direction = Angle(bullet.x, bullet.y, bots[target].box.x + 5, bots[target].box.y + 5);
        
        RadianAngle = (direction * 3.141592653589793D / 180.0D);
      } else {
        type = 0;
      }
    }
    
    x += speed * 1.0D * Math.cos(RadianAngle);
    y += speed * 1.0D * Math.sin(RadianAngle);
    

    bullet.x = ((int)x);bullet.y = ((int)y);
    
    if ((bullet.x > PWidth) || (bullet.x < 0) || (bullet.y > PHeight) || (bullet.y < 0))
    {
      return true;
    }
    return false;
  }
  
  public void draw(Graphics g)
  {
    if (done) {
      g.setColor(java.awt.Color.red);
    } else {
      g.setColor(java.awt.Color.black);
    }
    g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
  }
  
  public Rectangle getBounds()
  {
    Rectangle r = new Rectangle(bullet.x, bullet.y, bullet.width, bullet.height);
    return r;
  }
  
  public java.awt.geom.Rectangle2D getBounds2() {
    java.awt.geom.Rectangle2D.Double r = new java.awt.geom.Rectangle2D.Double(bullet.x, bullet.y, bullet.width, bullet.height);
    return r;
  }
}