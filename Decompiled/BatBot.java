import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BatBot extends JFrame implements java.awt.event.WindowListener
{
  private static final int port = 3012;
  private static final int playernum = 1;
  public BattlePanel bp;
  private JTextField[] PBox;
  private ServerSocket server;
  public MsgWatch[] pSocket;
  public int sleeptime = 100;
  private static BlockingQueue<Todo> myq;
  public static int pnum;
  public int pwidth;
  public int pheight;
  
  public BatBot(int pw, int ph) {
    super("The Battle Bot");
    pwidth = pw;
    pheight = ph;
    myq = new java.util.concurrent.LinkedBlockingQueue();
    try {
      ServerSocket server = new ServerSocket(3012);
      

      pSocket = new MsgWatch[pnum];
      for (int i = 0; i < pnum; i++) {
        pSocket[i] = new MsgWatch();
        pSocket[i].SetMsgWatch(this, i, sleeptime);
        pSocket[i].MakeServerCon(server);
      }
      



      server.close();
    } catch (java.io.IOException e) {
      System.err.println("Couldn't get I/O for the connection to: localhost");
    }
    


    makeGUI();
    
    addWindowListener(this);
    pack();
    setResizable(false);
    setVisible(true); }
  
  public Color pickcolor(int pid) { Color me;
    Color me;
    Color me;
    Color me;
    Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; Color me; switch (pid) {
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
  
  public synchronized Todo getq() {
    Todo i;
    Todo i;
    if (myq.size() > 0) {
      i = (Todo)myq.remove();
    } else {
      i = new Todo();
      pid = -1;
    }
    return i;
  }
  
  public synchronized void addq(Todo item) { myq.offer(item); }
  

  private void makeGUI()
  {
    Container c = getContentPane();
    
    bp = new BattlePanel(this, pwidth, pheight);
    c.add(bp, "Center");
    
    JPanel ctrls = new JPanel();
    ctrls.setLayout(new javax.swing.BoxLayout(ctrls, 0));
    
    PBox = new JTextField[pnum];
    for (int i = 0; i < pnum; i++) {
      PBox[i] = new JTextField("P " + i + ": ");
      PBox[i].setEditable(false);
      PBox[i].setForeground(pickcolor(i));
      PBox[i].setBackground(Color.white);
      ctrls.add(PBox[i]);
    }
    




    c.add(ctrls, "South");
  }
  
  public void setPBox(int pid, int no)
  {
    PBox[pid].setText("P " + pid + ": " + no);
  }
  


  public void windowActivated(WindowEvent e) {}
  


  public void windowDeactivated(WindowEvent e) {}
  


  public void windowDeiconified(WindowEvent e) {}
  


  public void windowIconified(WindowEvent e) {}
  

  public void windowClosing(WindowEvent e)
  {
    bp.stopGame();
  }
  

  public void windowClosed(WindowEvent e) {}
  
  public void windowOpened(WindowEvent e) {}
  
  public static void main(String[] args)
  {
    pnum = 1;
    int pw = 500;int ph = 500;
    if (args.length != 0) {
      pnum = Integer.parseInt(args[0]);
      System.out.println("Number of players is " + pnum);
      if (args.length >= 3) {
        pw = Integer.parseInt(args[1]);
        ph = Integer.parseInt(args[2]);
        System.out.println(" board size: " + pw + "," + ph);
      }
    }
    new BatBot(pw, ph);
  }
}