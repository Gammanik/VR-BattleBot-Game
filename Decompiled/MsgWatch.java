import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MsgWatch extends Thread
{
  private BatBot bb;
  private java.net.Socket echoSocket = null;
  private java.io.PrintWriter out = null;
  private BufferedReader in = null;
  private BufferedReader stdIn;
  private int sleeptime = 0;
  public ArrayList<String> info = new ArrayList();
  public int pid;
  public int movecnt = 0;
  public int shotcnt = 0;
  
  public MsgWatch() {}
  
  public void SetMsgWatch(BatBot bb, int id, int st)
  {
    this.bb = bb;
    pid = id;
    sleeptime = st;
  }
  
  public String[] token(String text)
  {
    java.util.StringTokenizer st = new java.util.StringTokenizer(text);
    
    String[] ret = new String[st.countTokens()];
    for (int i = 0; i < ret.length; i++)
      ret[i] = st.nextToken();
    return ret;
  }
  
  public void MakeServerCon(java.net.ServerSocket server)
  {
    try
    {
      echoSocket = server.accept();
      out = new java.io.PrintWriter(echoSocket.getOutputStream(), true);
      in = new BufferedReader(new java.io.InputStreamReader(echoSocket.getInputStream()));
      System.out.println("Pid " + pid + ": connected");
    } catch (java.io.IOException e) {
      System.err.println("Couldn't get I/O for the connection to: localhost");
    }
  }
  

  public void writeLine(String to)
  {
    out.println(to);
    out.flush();
  }
  
  public String getLine()
  {
    String from;
    try
    {
      from = in.readLine();
    } catch (java.io.IOException e) { String from;
      System.out.println("Socket error " + e);
      from = "AWGH!!!";
    }
    
    return from;
  }
  
  public int convertInt(String str) {
    int r = 0;
    try {
      r = Integer.parseInt(str);
    } catch (NumberFormatException e) {
      r = 0;
    }
    return r;
  }
  
  public void gamepaused() {
    while (bb.bp.isPaused) {
      try
      {
        Thread.yield();
        Thread.sleep(sleeptime);
      } catch (InterruptedException e) {
        System.out.println("Socket error " + e);
      }
    }
  }
  

  public void run()
  {
    System.out.println("Thread is started");
    

    gamepaused();
    try {
      System.out.println(pid + " is Running!");
      writeLine("Status " + bb.bp.bots[pid].box.x + " " + bb.bp.bots[pid].box.y + " " + movecnt + " " + shotcnt + " " + bb.bp.bots[pid].hp);
      


      String line;
      


      while ((line = in.readLine()) != null)
      {
        String[] action = token(line);
        
        Todo item = new Todo();
        
        if (action.length <= 0) {
          action = new String[1];
          action[0] = "baddata";
        }
        
        if ((action[0].equals("noop")) && (action.length == 1)) {
          if (movecnt < 0) movecnt += 1;
          if (shotcnt < 0) { shotcnt += 1;
          }
        }
        else if ((action[0].equals("DeathBlossom")) && (action.length == 1)) {
          pid = pid;
          type = action[0];
          bb.addq(item);
        } else if ((action[0].equals("move")) && (action.length == 3))
        {
          if (movecnt == 0) {
            pid = pid;
            type = action[0];
            x = convertInt(action[1]);
            y = convertInt(action[2]);
            bb.addq(item);
            if (shotcnt != 0) shotcnt += 1;
          }
          movecnt = (-bb.bp.bots[pid].moverate);
        } else if (((action[0].equals("shot")) || 
          (action[0].equals("fire")) || 
          (action[0].equals("hunt"))) && (action.length == 2))
        {
          if ((BatBot.pnum == 1) || (bb.bp.botsalive > 1))
          {
            if (shotcnt == 0) {
              if (action[0].equals("fire")) action[0] = "shot";
              pid = pid;
              type = action[0];
              x = bb.bp.bots[pid].box.x;
              y = bb.bp.bots[pid].box.y;
              direction = convertInt(action[1]);
              if (action[0].equals("hunt")) { stype = 1;
              }
              
              bb.addq(item);
              movecnt -= bb.bp.bots[pid].shotpower;
            }
            shotcnt = (-bb.bp.bots[pid].shotrate);

          }
          else if (movecnt < 0) { movecnt += 1;
          }
        } else if (action[0].equals("scan")) {
          if (action.length == 1)
          {


            double sd = bb.bp.bots[pid].scandist * 1.0D;
            double xy = sd / 2.0D - 5.0D;
            Ellipse2D.Double cir = new Ellipse2D.Double(bb.bp.bots[pid].box.x - xy, bb.bp.bots[pid].box.y - xy, sd, sd);
            
            for (int i = 0; i < BatBot.pnum; i++) {
              if ((bb.bp.bots[i].alive) && (pid != i) && 
                (cir.getBounds2D().intersects(bb.bp.bots[i].getBounds2())))
              {
                writeLine("scan bot " + i + " " + bb.bp.bots[i].box.x + " " + bb.bp.bots[i].box.y);
              }
            }
            

            Iterator<Shot> it = bb.bp.shots.iterator();
            while (it.hasNext()) {
              Shot shot = (Shot)it.next();
              if ((!done) && 
                (cir.getBounds2D().intersects(shot.getBounds2())))
              {


                writeLine("scan shot " + pid + " " + id + "  " + powerlevel + " " + bullet.x + " " + bullet.y);
              }
            }
            
            if ((bb.bp.isPowerUp) && 
              (cir.getBounds2D().intersects(bb.bp.powerUp.getBounds2())))
            {
              writeLine("scan powerup " + bb.bp.powerUp.type + " " + bb.bp.powerUp.box.x + " " + bb.bp.powerUp.box.y);
            }
            


            if (movecnt < 0) movecnt += 1;
            if (shotcnt < 0) shotcnt += 1;
          }
          writeLine("scan done");
        } else {
          writeLine("Info BadCmd");
        }
        

        Thread.yield();
        Thread.sleep(sleeptime);
        gamepaused();
        
        Iterator<String> it = info.iterator();
        while (it.hasNext()) {
          writeLine("Info " + (String)it.next());
          it.remove();
        }
        if (!bb.bp.bots[pid].alive) {
          writeLine("Info Dead");
          break;
        }
        
        if (bb.bp.gameOver) {
          writeLine("Info GameOver");
          break;
        }
        writeLine("Status " + bb.bp.bots[pid].box.x + " " + bb.bp.bots[pid].box.y + " " + movecnt + " " + shotcnt + " " + bb.bp.bots[pid].hp);


      }
      



    }
    catch (Exception e)
    {


      System.out.println("Socket closed " + e);
    }
  }
}