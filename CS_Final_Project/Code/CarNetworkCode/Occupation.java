import java.util.*;
import java.util.Collections;
public class Occupation{
    private double speed;
    private boolean direction;
    private double beginTime;
    private double endTime;
    private Path myPath;
    
    private ArrayList<LineSegment> lineSegs;
    
    public Occupation(Path p,Occupation existingOcc,double enterTime,Boolean dir,double sp){
        this.beginTime=enterTime;
        this.direction=dir;
        this.myPath=p;
        this.speed=sp;
        
        this.firstInt(existingOcc);
        //calculate occupation here
        
    }
    private Point firstInt(Occupation occ){
        
    }
    
    private intersect
        
    public double getEndTime(){
        
    }
    
}