import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D.Double;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BattlePanel extends javax.swing.JPanel implements Runnable
{
  private static int PWIDTH = 500;
  private static int PHEIGHT = 500;
  private static int Pdistance = 50;
  
  private Thread animator;
  
  public boolean running = false;
  public boolean isPaused = false;
  
  public BatBot bbTop;
  
  public bot[] bots;
  public int botsalive;
  public ArrayList<Shot> shots = new ArrayList();
  
  public boolean isPowerUp = false;
  public PowerUp powerUp = null;
  
  public Random r;
  
  public boolean gameOver = false;
  private int score = 0;
  
  private java.awt.Font font;
  
  private FontMetrics metrics;
  private Graphics dbg;
  private java.awt.Image dbImage = null;
  
  public int convertInt(String str) {
    int r = 0;
    try {
      r = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      r = 0;
    }
    return r;
  }
  
  public int distance(bot one, bot two) {
    return (int)Math.sqrt(Math.pow(box.x - box.x, 2.0D) + 
      Math.pow(box.y - box.y, 2.0D));
  }
  


  public BattlePanel(BatBot bc, int pw, int ph)
  {
    bbTop = bc;
    PWIDTH = pw;
    PHEIGHT = ph;
    
    r = new Random();
    
    setBackground(Color.white);
    setPreferredSize(new java.awt.Dimension(PWIDTH, PHEIGHT));
    
    setFocusable(true);
    requestFocus();
    readyForTermination();
    

    bots = new bot[BatBot.pnum];
    for (int i = 0; i < BatBot.pnum; i++) {
      bots[i] = new bot(PWIDTH, PHEIGHT, i);
      boolean toclose = true;
      while (toclose) {
        toclose = false;
        for (int j = 0; j < i; j++) {
          if (distance(bots[i], bots[j]) < Pdistance)
            toclose = true;
        }
        if (toclose)
          bots[i].randset();
      }
      System.out.println("Reseting!");
    }
    
    botsalive = BatBot.pnum;
    





    font = new java.awt.Font("SansSerif", 1, 24);
    metrics = getFontMetrics(font);
  }
  


  private void readyForTermination()
  {
    addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyPressed(KeyEvent e)
      {
        int keyCode = e.getKeyCode();
        if ((keyCode != 27) && (keyCode != 81) && (keyCode != 35)) { if (keyCode == 67)
          {
            if (!e.isControlDown()) {} }
        } else { running = false;
        }
      }
    });
  }
  

  public void addNotify()
  {
    super.addNotify();
    startGame();
  }
  

  private void startGame()
  {
    if ((animator == null) || (!running)) {
      animator = new Thread(this);
      animator.start();
    }
  }
  





  public void resumeGame()
  {
    isPaused = false;System.out.println("Game Running");
  }
  
  public void pauseGame()
  {
    isPaused = true;System.out.println("Game Paused");
  }
  


  public void stopGame() { running = false; }
  
  public Color pickcolor(int pid) { Color me;
    Color me;
    Color me;
    Color me;
    Color me;
    Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; switch (pid) {
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
    
    return me;
  }
  

  public void run()
  {
    System.out.println("start animation loop");
    

    for (int i = 0; i < BatBot.pnum; i++)
    {


      bbTop.pSocket[i].writeLine("setup " + i + " " + PWIDTH + " " + PHEIGHT + " " + BatBot.pnum);
      System.out.println("Pid " + i + ": wrote setup line");
      
      boolean infodone = false;
      while (!infodone) {
        String[] str = bbTop.pSocket[i].token(bbTop.pSocket[i].getLine());
        if (str.length == 4) {
          infodone = bots[i].setBot(str[0], convertInt(str[1]), 
            convertInt(str[2]), 
            convertInt(str[3]));
          System.out.println("Pid " + i + ": " + str[0] + " " + str[1] + " " + str[2] + " " + str[3]);
        }
        if (!infodone) {
          bbTop.pSocket[i].writeLine("setup error");
          System.out.println("Pid: " + i + ": setup error(" + str[0] + " " + str[1] + " " + str[2] + " " + str[3] + ")");
        }
      }
      bbTop.pSocket[i].writeLine(bots[i].info());
      System.out.println("Done Sending info to " + i);
      bbTop.setPBox(i, bots[i].hp);
    }
    running = true;
    for (int i = 0; i < BatBot.pnum; i++) {
      System.out.println("starting listening thread " + i);
      bbTop.pSocket[i].start();
    }
    System.out.println("Running!");
    while (running)
    {
      gameUpdate();
      gameRender();
      paintScreen();
      Thread.yield();
      try {
        Thread.sleep(50L);
      } catch (InterruptedException localInterruptedException) {}
    }
    System.exit(0);
  }
  

  public void db(int playerid)
  {
    double sd = bots[playerid].scandist * 1.0D;
    double xy = sd / 2.0D - 5.0D;
    Ellipse2D.Double cir = new Ellipse2D.Double(bots[playerid].box.x - xy, bots[playerid].box.y - xy, sd, sd);
    
    for (int i = 0; i < BatBot.pnum; i++) {
      if ((bots[i].alive) && (playerid != i) && 
        (cir.getBounds2D().intersects(bots[i].getBounds2())))
      {

        bbTop.pSocket[playerid].shotcnt -= 10 * bots[playerid].shotrate;
        bbTop.pSocket[playerid].movecnt -= 2 * bots[playerid].shotpower;
      }
    }
    

    Iterator<Shot> it = shots.iterator();
    while (it.hasNext()) {
      Shot shot = (Shot)it.next();
      if ((!done) && (pid != playerid) && 
        (cir.getBounds2D().intersects(shot.getBounds2()))) {
        shot.hit();
        bbTop.pSocket[playerid].shotcnt -= 10 * powerlevel;
        bbTop.pSocket[playerid].movecnt -= 2 * powerlevel;
      }
    }
    if (bbTop.pSocket[playerid].shotcnt > -20)
      bbTop.pSocket[playerid].shotcnt = -20;
    if (bbTop.pSocket[playerid].movecnt > -10)
      bbTop.pSocket[playerid].movecnt = -10;
    System.out.println("DB: " + playerid + " m=" + bbTop.pSocket[playerid].movecnt + " s=" + bbTop.pSocket[playerid].movecnt);
  }
  



  public void applyPowerUp(int shotpid)
  {
    if ((shotpid == BatBot.pnum) && (powerUp.type != 4)) {
      return;
    }
    
    switch (powerUp.type) {
    case 0: 
      bots[shotpid].addHP(1);
      bbTop.setPBox(shotpid, bots[shotpid].hp);
      
      bbTop.pSocket[shotpid].info.add("PowerUp ArmorUp");
      break;
    case 1: 
      bots[shotpid].improvespeed(1);
      bbTop.pSocket[shotpid].info.add("PowerUp MoveFaster");
      break;
    case 2: 
      bots[shotpid].improvefire(2);
      bbTop.pSocket[shotpid].info.add("PowerUp FireFaster");
      break;
    case 3: 
      for (int angle = 0; angle <= 360; angle += 15) {
        Todo item = new Todo();
        pid = shotpid;type = "shot";x = bots[shotpid].box.x;y = bots[shotpid].box.y;direction = angle;
        bbTop.addq(item);
      }
      break;
    case 4: 
      for (int angle = 0; angle <= 360; angle += 15) {
        Todo item = new Todo();
        pid = BatBot.pnum;type = "shot";x = powerUp.box.x;y = powerUp.box.y;direction = angle;
        bbTop.addq(item);
      }
      
      break;
    case 5: 
      bots[shotpid].improvefirepower(1);
      bbTop.pSocket[shotpid].info.add("PowerUp FireUp");
      break;
    case 6: 
      bots[shotpid].improvefiremove(1);
      bbTop.pSocket[shotpid].info.add("PowerUp FireMoveFaster");
      break;
    case 7: 
      bots[shotpid].randset();
      bbTop.pSocket[shotpid].info.add("PowerUp Teleport");
      break;
    default:  System.out.println("Bad power number " + powerUp.type);
    }
    
  }
  
  private void gameUpdate()
  {
    if ((!isPaused) && (!gameOver))
    {

      if (!isPowerUp)
      {
        if (r.nextInt(100) > 90)
        {
          powerUp = new PowerUp(0, PWIDTH, PHEIGHT, this);
          isPowerUp = true;
        }
      }
      


      Todo item = bbTop.getq();
      while (pid >= 0) {
        if (type.equals("move")) {
          bots[pid].move(x, y);
        } else if (type.equals("shot")) {
          if (pid == BatBot.pnum)
          {
            shots.add(new Shot(pid, PWIDTH, PHEIGHT, x, y, bots[0].shotpower, direction, stype, this, 0, 2));
          } else {
            shots.add(new Shot(pid, PWIDTH, PHEIGHT, x, y, bots[pid].shotpower, direction, stype, this, 0, bots[pid].shotspeed));
          }
        } else if (!type.equals("hunt"))
        {
          if (!type.equals("DeathBlossom")) {}
        }
        
        item = bbTop.getq();
      }
      

      Iterator<Shot> it = shots.iterator();
      while (it.hasNext()) {
        Shot shot = (Shot)it.next();
        boolean done = shot.move(this);
        if (!done) {
          for (int i = 0; i < BatBot.pnum; i++) {
            if ((bots[i].alive) && (pid != i) && 
              (shot.getBounds().intersects(bots[i].getBounds())))
            {

              if (!bots[i].hit(powerlevel))
              {
                botsalive -= 1;
                System.out.println("Bots alive is " + botsalive);
                for (int j = 0; j < BatBot.pnum; j++) {
                  if (bots[j].alive) {
                    bbTop.pSocket[j].info.add("Alive " + botsalive);
                  }
                }
              }
              bbTop.pSocket[i].info.add("hit by " + pid + " " + id);
              shot.hit();
              bbTop.setPBox(i, bots[i].hp);
            }
          }
          

          Iterator<Shot> it1 = shots.iterator();
          while ((it1.hasNext()) && (!done)) {
            Shot shot1 = (Shot)it1.next();
            if ((pid != pid) && 
              (shot.getBounds().intersects(shot1.getBounds())))
            {
              shot.hit();
              shot1.hit();
            }
          }
          

          if ((isPowerUp) && 
            (shot.getBounds().intersects(powerUp.getBounds())))
          {
            powerUp.hit();
            
            applyPowerUp(pid);
            isPowerUp = false;
            powerUp = null;
            shot.hit();
          }
        }
        


        if (done)
          it.remove();
      }
      if ((botsalive <= 1) && (BatBot.pnum > 1) && (shots.isEmpty())) {
        gameOver = true;
      }
    }
  }
  
  private void gameRender()
  {
    if (dbImage == null) {
      dbImage = createImage(PWIDTH, PHEIGHT);
      if (dbImage == null) {
        System.out.println("dbImage is null");
        return;
      }
      dbg = dbImage.getGraphics();
    }
    

    dbg.setColor(Color.white);
    dbg.fillRect(0, 0, PWIDTH, PHEIGHT);
    
    dbg.setColor(Color.blue);
    dbg.setFont(font);
    

    if (isPowerUp) {
      powerUp.draw(dbg);
    }
    

    for (int i = 0; i < BatBot.pnum; i++) {
      if (bots[i].alive) {
        bots[i].draw(dbg);
      }
    }
    
    Iterator<Shot> it = shots.iterator();
    while (it.hasNext()) {
      Shot shot = (Shot)it.next();
      shot.draw(dbg);
    }
    
    if (gameOver) {
      gameOverMessage(dbg);
    }
  }
  
  private void gameOverMessage(Graphics g)
  {
    int winnerpid = -1;
    for (int i = 0; i < BatBot.pnum; i++) {
      if (bots[i].alive)
        winnerpid = i;
    }
    String msg;
    String msg;
    if (winnerpid >= 0) {
      msg = "Game Over. " + bots[winnerpid].name + " wins ";
    } else
      msg = "Game Over. No winner";
    int x = (PWIDTH - metrics.stringWidth(msg)) / 2;
    int y = (PHEIGHT - metrics.getHeight()) / 2;
    g.setColor(Color.red);
    g.setFont(font);
    g.drawString(msg, x, y);
  }
  


  private void paintScreen()
  {
    try
    {
      Graphics g = getGraphics();
      if ((g != null) && (dbImage != null))
        g.drawImage(dbImage, 0, 0, null);
      g.dispose();
    }
    catch (Exception e) {
      System.out.println("Graphics context error: " + e);
    }
  }
}