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
        EOlineSegs=new ArrayList<LineSegment>(existingOcc.getLS());
        this.beginTime=enterTime;
        this.direction=dir;
        this.myPath=p;
        this.speed=sp;
        this.lineSegs=new ArrayList<LineSegment>();
        Point firstInt=null;
        Point start;
        Line end;
        Line thisL;
        
        if(EOlineSegs.size()==0){//if there are no existing occupations
            
            if(dir){
                start=new Point(enterTime,0);
                end = new Point(enterTime+p.getDistance()/sp,p.getDistance());
                lineSegs.add(new Line(start,end));
            }
            else{
                start=new Point(enterTime,p.getDistance());
                end = new Point(enterTime+p.getDistance()/sp,0);
                lineSegs.add(new Line(start,end));
            }
            thisLineSeg=new LineSegment(start,endP);
            //lineSegs.sortedAdd(thisLineSeg);
            ExtraMethods.sortAddAsc(thisLineSeg,lineSegs);
        }
        else{//there are existing occupations
            Point jaggedEnd;
        while(jaggedEnd.getY()<p.getDistance()){
        for(int i=0;i<EOlineSegs.size();i++){
            Point intP = thisLineSeg.getIntersection(EOlineSegs.get(i));
            if(intP!=null){
                firstInt=intP;
                break;
            }
        }
            
            
        }
        if(firstInt==null){//no intersections
            ExtraMethods.sortAddAsc(thisLineSeg,lineSegs);
        }
        else{//there is at least one collision that needs to be managed //check if intersected line is opposite sign slope (if it is set endtime to infinity)
            
            
            
            firstInt=new Point(firstInt.getX(),firstInt.getY()-c.getTD());
            ExtraMethods.sortAddAsc(new LineSegment(start,firstInt),lineSegs);
            //lineSegs
            
            LineSegment firstLS = new LineSegment(new Point(),new Point());
            ExtraMethods.sortAddAsc(firstLS,lineSegs);
        }
        //calculate occupation here
        
            
        }
        
    }
    
        
    public double getEndTime(){
        
    }
    
    public ArrayList<LineSegment> getLS(){
        return this.lineSegs;
    }
    
}