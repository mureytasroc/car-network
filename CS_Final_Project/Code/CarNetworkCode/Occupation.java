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
        lineSegs=new ArrayList<LineSegment>(existingOcc.getLS());
        this.beginTime=enterTime;
        this.direction=dir;
        this.myPath=p;
        this.speed=sp;
        LineSegment thisLineSeg;
        Point firstInt=null;
        Point start;
        Line end;
        Line thisL;
        
        if(lineSegs.size()==0){//if there are no existing occupations
            if(dir){
                start=new Point(enterTime,0);
                end = new Line(new Point(0,p.getDistance()),0);
                thisL = new Line(new Point(enterTime,0),sp);
            }
            else{
                start=new Point(enterTime,p.getDistance());
                end = new Line(new Point(0,0),0);
                thisL = new Line(start,-sp);
            }
            Point endP=end.getIntersection(thisL);
            thisLineSeg=new LineSegment(start,endP);
            //lineSegs.sortedAdd(thisLineSeg);
            ExtraMethods.sortAddAsc(thisLineSeg,lineSegs);
        }
        else{//there are existing occupations
            Point jaggedEnd;
        while(jaggedEnd.getY()<p.getDistance()){
        for(int i=0;i<lineSegs.size();i++){
            Point intP = thisLineSeg.getIntersection(lineSegs.get(i));
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