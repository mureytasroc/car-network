import java.awt.*;
import java.util.*;

class Map{
    ArrayList<Path> myPaths;
    private double myWidth;
    private double myHeight;
    
    
    public Map(int w, int h){
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
    public void update(){
        for(int i=0;i<myPaths.size();i++){
            myPaths.get(i).show();
        }
    }
}