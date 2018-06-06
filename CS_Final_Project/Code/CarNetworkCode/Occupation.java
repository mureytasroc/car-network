import java.util.*;
import java.util.Collections;
public class Occupation{
    
    //vvv below variables are for continuous occupation
    private double speed;
    private boolean direction;
    private double beginTime;
    private double endTime;
    //^^^
    
    private boolean continuous;
    private Path myPath;
    private ArrayList<LineSegment> lineSegs;
    
    public Occupation(){
        continuous=false;
        lineSegs=new ArrayList<LineSegment>();
    }
    
    public void add(Occupation addend){
        for(LineSegment l: addend.getLS()){
            ExtraMethods.sortAddAsc(l,this.lineSegs);
        }
    }
    
    public Occupation(Path p,Occupation existingOcc,double enterTime,Boolean dir,double sp, Car c){ //continuous occupation constructor from Path getTime
        
        continuous=true;
        ArrayList<LineSegment>EOlineSegs=new ArrayList<LineSegment>(existingOcc.getLS());
        this.beginTime=enterTime;
        this.direction=dir;
        this.myPath=p;
        this.speed=sp;
        this.lineSegs=new ArrayList<LineSegment>();
        Point start;
        Point end;
        
        if(dir){
            start=new Point(enterTime,0);
                end = new Point(enterTime+p.getDistance()/sp,p.getDistance());
        }
        else{
            start=new Point(enterTime,p.getDistance());
            end = new Point(enterTime+p.getDistance()/sp,0);
        }
        LineSegment curSeg=new LineSegment(start,end);
        //System.out.println(start.getX());
        //System.out.println(end.getX());
        if(EOlineSegs.size()==0){//if there are no existing occupations
        this.lineSegs.add(curSeg);
        //System.out.println("hey"+curSeg);
        endTime=end.getX();
        }
        else{
  //there are existing occupations
        Point curStart=new Point(start);
        boolean keep=true;
            LineSegment finishLine;
            if(dir){
                finishLine = new LineSegment(new Point(enterTime,p.getDistance()),new Point(Double.MAX_VALUE,p.getDistance()));}
            else{
                finishLine = new LineSegment(new Point(enterTime,0),new Point(Double.MAX_VALUE,0));
            }

        while(keep){
            
            LineSegment collider=curSeg.first(EOlineSegs);
            //System.out.println(collider);
            //System.out.println("hey");
            if (collider==null){lineSegs.add(curSeg);endTime=end.getX();keep=false;}
            else{
                //System.out.println(c.getTD());
                if((collider.isOppositeSlope(curSeg))||(Math.abs(collider.getSlope())>=Math.abs(curSeg.getSlope()))){
                    this.endTime=Double.POSITIVE_INFINITY;
                    keep=false;
                }
                else{
                    Point collision=collider.getIntersection(curSeg);
                    Point holder=curSeg.endPointMinus(c.getTD(),collision, collider);
                    lineSegs.add(new LineSegment(curStart,holder));
                    curStart=holder;
                           Line temp = new Line(curStart,collider.getSlope());
                        end=finishLine.getIntersection(temp);
                    curSeg=new LineSegment(curStart,end);
                    //System.out.println("hey"+curSeg);
                       }
                    
                    
                
            }
            
        }
        
        }
        
    }//ADD CHECKING IF COLLISION IS RESOLVED SO U CAN GO UP
    
        
    public double getEndTime(){
        return this.endTime;
    }
    
    public ArrayList<LineSegment> getLS(){
        
        return this.lineSegs;
    }
    
    
}