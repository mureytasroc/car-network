import java.awt.*;
import java.util.*;

public class Map{
    ArrayList<Path> myPaths;
    private double myWidth;
    private double myHeight;
    
    public static void main(String[] args){
        Map m = new Map(300,300);
        m.addPath(new Path(m,new Point(0.2,0.5),new Point(0.4,0.7),0.1));
    }
    
    public Map(int w, int h){
        StdDraw.setCanvasSize(w,h);
        StdDraw.setXScale(0.0,1.0);
        StdDraw.setYScale(0.0,1.0);
        this.myWidth=(double)w;
        this.myHeight=(double)h;
        myPaths=new ArrayList<Path>();
    }
    
    public ArrayList<Path> getPaths(){
        return this.myPaths;
    }
    public double getWidth(){
        return this.myWidth;
    }
    public double getHeight(){
        return this.myHeight;
    }
    public void addPath(Path p){
        myPaths.add(p);
    }
}