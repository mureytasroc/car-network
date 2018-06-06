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
    
    public Occupation(Path p,Occupation existingOcc,double enterTime,Boolean dir,double sp){ //continuous occupation constructor from Path getTime
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
        if(EOlineSegs.size()==0){
        this.lineSegs.add(curSeg);
        endTime=end.getX();
        }
        else{//if there are no existing occupations
  //there are existing occupations
        Point curStart=start;
        while(true){
            LineSegment collider=curSeg.first(EOlineSegs);
            if (collider==null){lineSegs.add(curSeg);endTime=end.getX();break;}
            else{
                Point collision=collider.getIntersection(curSeg);
                lineSegs.add(new LineSegment(curStart,collision));
                curStart=collision;
                curSeg=new LineSegment(collision,end);
            }
            
        }
        /*if(firstInt==null){//no intersections
            ExtraMethods.sortAddAsc(thisLineSeg,lineSegs);
        }
        else{//there is at least one collision that needs to be managed //check if intersected line is opposite sign slope (if it is set endtime to infinity)
            
            
            
            firstInt=new Point(firstInt.getX(),firstInt.getY()-c.getTD());
            ExtraMethods.sortAddAsc(new LineSegment(start,firstInt),lineSegs);
            //lineSegs
            
            LineSegment firstLS = new LineSegment(new Point(),new Point());
            ExtraMethods.sortAddAsc(firstLS,lineSegs);
        }*/
        //calculate occupation here
        
            
        }
        
    }
    
        
    public double getEndTime(){
        return this.endTime;
    }
    
    public ArrayList<LineSegment> getLS(){
        return this.lineSegs;
    }
    
}