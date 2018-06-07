import java.awt.*;
import java.util.*;

public class Path{
    public static void main(String[] args){
        
    }
    
    private Color leftColor, rightColor, lmColor, rmColor;
    private LineSegment lineSeg;
    private double width;
    private Map map;
    
    Path(Map m,Point p1, Point p2,double w){
        this.map=m;
        this.width=w;
        this.lineSeg=new LineSegment(p1,p2);
        this.leftColor = new Color(255,0,0);
        this.rightColor = new Color(0,255,0);
        this.lmColor = new Color(255,0,255);
        this.rmColor = new Color(0,255,255);
    }
    public void show(){
        StdDraw.setPenColor(this.leftColor);
        
        StdDraw.line()
    }
    
}