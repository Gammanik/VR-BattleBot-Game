import java.awt.Color;

public class myColor
{
  java.util.ArrayList<Color> list;
  Color black;
  
  public myColor()
  {
    black = Color.black;
    
    list = new java.util.ArrayList();
    list.add(new Color(255, 0, 0));
    list.add(new Color(0, 0, 255));
    list.add(new Color(0, 128, 0));
    list.add(new Color(255, 105, 0));
    list.add(new Color(255, 204, 0));
    list.add(new Color(128, 0, 128));
    list.add(new Color(137, 104, 89));
    list.add(new Color(128, 128, 128));
    list.add(new Color(0, 128, 128));
    list.add(new Color(128, 0, 128));
    list.add(new Color(128, 128, 0));
    

    list.add(new Color(128, 0, 0));
    list.add(new Color(0, 0, 128));
    list.add(new Color(85, 107, 47));
    list.add(new Color(169, 84, 0));
    list.add(new Color(218, 165, 32));
    list.add(new Color(109, 31, 148));
    list.add(new Color(73, 56, 41));
    list.add(new Color(96, 96, 96));
    list.add(new Color(0, 96, 96));
    list.add(new Color(96, 0, 96));
    list.add(new Color(70, 130, 180));
    

    list.add(new Color(255, 192, 203));
    list.add(new Color(0, 128, 255));
    list.add(new Color(106, 212, 0));
    list.add(new Color(255, 155, 66));
    list.add(new Color(199, 129, 70));
    list.add(new Color(189, 122, 246));
    list.add(new Color(129, 108, 91));
    list.add(new Color(211, 211, 211));
    list.add(new Color(0, 255, 255));
    list.add(new Color(255, 0, 255));
    list.add(new Color(51, 153, 204));
  }
  


  public Color pickColor(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < list.size())) {
      return (Color)list.get(paramInt);
    }
    return black;
  }
  
  public int pickColorRed(int paramInt) {
    if ((paramInt >= 0) && (paramInt < list.size())) {
      return ((Color)list.get(paramInt)).getRed();
    }
    return black.getRed();
  }
  
  public int pickColorGreen(int paramInt) { if ((paramInt >= 0) && (paramInt < list.size())) {
      return ((Color)list.get(paramInt)).getGreen();
    }
    return black.getGreen();
  }
  
  public int pickColorBlue(int paramInt) { if ((paramInt >= 0) && (paramInt < list.size())) {
      return ((Color)list.get(paramInt)).getBlue();
    }
    return black.getBlue();
  }
}