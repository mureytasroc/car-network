import java.util.*;
import java.util.Collections;
public class Occupation{
    private double speed;
    private boolean direction;
    private double beginTime;
    private double endTime;
    private Path myPath;
    public Occupation(Path myPath,double time, boolean direction,Car c){
        this.speed=c.getSpeed();
        this.direction=direction;
        this.beginTime=time;
        this.endTime=beginTime+(myPath.getDistance(c)/this.speed);
        this.myPath=myPath;
        this.myPath.addOccupation(this);
    }
    public void update(){
        this.endTime-=1;
        this.beginTime-=1;
        if (this.endTime<0){
            myPath.removeOccupation(this);
        }
    }
    public double[] hasCrash(double startTime,double velocity){
        
        if (startTime>endTime){
            return null;
        }
        else{
        double distance=myPath.getDistance();
        double finishTime=startTime+distance/velocity;
        if(finishTime<beginTime){
            return null;
        }
            else{
                double xInt = (velocity*startTime-speed*beginTime)/(velocity-speed);
                double yInt = velocity*(xInt-startTime);
                return new double[]{xInt,yInt};
            }
            
        }
        
    }
    
}