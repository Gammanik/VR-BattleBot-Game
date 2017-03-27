import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

public class bot
{
  Rectangle box;
  java.awt.geom.Ellipse2D.Double cir;
  int pid;
  String name;
  int dir_x;
  int dir_y;
  int PWidth;
  int PHeight;
  int shotsleft = 1;
  int shotpower = 1;
  int shotrate = 1;
  int shotdist = 1000;
  int shotspeed = 2;
  int moverate = 1;
  int hp = 0;
  int scandist = 100;
  Color me;
  boolean alive = true;
  
  public String info() {
    String stuff = name + " " + hp + " " + moverate + " " + scandist + " " + shotpower + " " + shotrate + " " + shotdist;
    return stuff;
  }
  
  public void randset() {
    Random r = new Random();
    int x = r.nextInt(PWidth - 10);
    int y = r.nextInt(PHeight - 10);
    box.x = x;
    box.y = y;
  }
  
  public bot(int PW, int PH, int id) {
    Random r = new Random();
    int x = r.nextInt(PW - 10);
    int y = r.nextInt(PH - 10);
    
    box = new Rectangle(x, y, 10, 10);
    cir = new java.awt.geom.Ellipse2D.Double(x * 1.0D, y * 1.0D, 100.0D, 100.0D);
    pid = id;
    dir_x = 1;
    dir_y = 0;
    PWidth = PW;
    PHeight = PH;
    shotsleft = 1;
    moverate = 1;
    hp = 1;
    alive = true;
    
    switch (pid) {
    case 0:  me = new Color(255, 0, 0); break;
    case 1:  me = new Color(0, 0, 255); break;
    case 2:  me = new Color(0, 255, 255); break;
    case 3:  me = new Color(255, 105, 0); break;
    case 4:  me = new Color(0, 128, 0); break;
    case 5:  me = new Color(255, 0, 255); break;
    case 6:  me = new Color(255, 192, 203); break;
    case 7:  me = new Color(255, 204, 0); break;
    case 8:  me = new Color(128, 128, 128); break;
    case 9:  me = new Color(211, 211, 211); break;
    case 10:  me = new Color(169, 169, 169); break;
    case 11:  me = new Color(0, 255, 0); break;
    case 12:  me = new Color(128, 0, 0); break;
    case 13:  me = new Color(128, 0, 128); break;
    case 14:  me = new Color(128, 128, 0); break;
    case 15:  me = new Color(0, 0, 128); break;
    case 16:  me = new Color(218, 165, 32); break;
    case 17:  me = new Color(85, 107, 47); break;
    case 18:  me = new Color(240, 230, 140); break;
    case 19:  me = new Color(70, 130, 180); break;
    case 20:  me = new Color(153, 50, 204); break;
    case 21:  me = new Color(129, 108, 91); break;
    case 22:  me = new Color(73, 56, 41); break;
    case 23:  me = new Color(104, 30, 126); break;
    case 24:  me = new Color(189, 122, 246); break;
    default:  me = Color.black;
    }
  }
  
  public boolean setBot(String n, int shp, int sp, int scan) {
    name = n;
    if (shp + sp + scan <= 5)
    {
      hp = (shp + 1);
      if (hp > 5) { hp = 1;return false; }
      moverate = (hp - 1);
      

      shotpower = (sp + 1);
      if (shotpower > 5) { shotpower = 1;return false; }
      shotdist = ((int)Math.round(
        Math.sqrt(Math.pow(PWidth, 2.0D) + Math.pow(PHeight, 2.0D)) / (shotpower * 1.0D)));
      
      shotrate = (shotpower * 5 * 2 - 1);
      
      scan++;
      if (scan > 5) { scan = 1;return false; }
      scandist = (scan * 100);
      
      System.out.println("hp = " + hp + " mr=" + moverate + " sp=" + shotpower + " dist=" + shotdist + " sr=" + shotrate + " scan=" + scandist);
      return true;
    }
    return false;
  }
  


  public void move(int x, int y)
  {
    int dir_x = 0;int dir_y = 0;
    if (x > 0) {
      dir_x = 1;
    } else if (x < 0)
      dir_x = -1;
    if (y > 0) {
      dir_y = 1;
    } else if (y < 0) {
      dir_y = -1;
    }
    int tx = box.x + dir_x;
    int ty = box.y + dir_y;
    if ((tx <= PWidth - 10) && (tx >= 0) && (ty <= PHeight - 10) && (ty >= 0))
    {
      box.x = tx;
      box.y = ty;
      cir.x = tx;
      cir.y = ty;
    }
  }
  
  public boolean hit(int shotpower)
  {
    hp -= shotpower;
    
    if (hp <= 0) {
      alive = false;
    }
    return alive;
  }
  
  public boolean addHP(int howmuch) {
    if (hp > 0)
      hp += howmuch;
    if (hp > 5) hp = 5;
    return true;
  }
  
  public boolean improvespeed(int howmuch) {
    if (hp > 0)
      moverate -= howmuch;
    if (moverate < 0) moverate = 0;
    return true;
  }
  
  public boolean improvefire(int howmuch) {
    if (hp > 0)
      shotrate -= howmuch;
    if (shotrate < 1) shotrate = 1;
    return true;
  }
  
  public boolean improvefirepower(int howmuch) {
    if (hp > 0)
      shotpower += howmuch;
    if (shotpower > 5) shotpower = 5;
    return true;
  }
  
  public boolean improvefiremove(int howmuch) {
    if (hp > 0)
      shotspeed += howmuch;
    if (shotspeed > 7) shotspeed = 7;
    return true;
  }
  
  public void draw(java.awt.Graphics g) {
    g.setColor(me);
    g.fillRect(box.x, box.y, box.width, box.height);
    int xy = (int)(scandist / 2.0D - 5.0D);
    g.drawOval(box.x - xy, box.y - xy, scandist, scandist);
  }
  
  public Rectangle getBounds()
  {
    Rectangle r = new Rectangle(box.x, box.y, box.width, box.height);
    return r;
  }
  
  public java.awt.geom.Rectangle2D getBounds2()
  {
    java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Double(box.x, box.y, box.width, box.height);
    return r;
  }
}