import java.util.*;
import java.util.Collections;
public class Occupation{
    
    public static void main(String[] args){
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        ls.add(new LineSegment(new Point(-1,0), new Point(3,5)));
        ls.add(new LineSegment(new Point(3,5), new Point(8,7)));
        ls.add(new LineSegment(new Point(8,7), new Point(20,10)));
        ls.add(new LineSegment(new Point(19.5,10), new Point(23,10)));
        
        ExtraMethods.printLSarray(ls);
        System.out.println("\n\n\n");
        /*ls.add(new LineSegment(new Point(-1,10), new Point(3,5)));
        ls.add(new LineSegment(new Point(3,5), new Point(8,3)));
        ls.add(new LineSegment(new Point(8,3), new Point(20,0)));*/
        
        
        Occupation occ = new Occupation(ls);
        
        Occupation next = new Occupation(new Path(10),occ,5,true,2,new Car(0.5,2));
        ExtraMethods.printLSarray(next.getLS());
        System.out.println(next.getEndTime());
        System.out.println(next.getPio().getLS());

    }
    
    //vvv below variables are for continuous occupation
    private double speed;
    private boolean direction;
    private double beginTime;
    private double endTime;
    //^^^
    
    private PIO myPio=null;
    private double entarTime;
    
    private boolean continuous;
    private Path myPath;
    private ArrayList<LineSegment> lineSegs;
    
    public Occupation(){
        continuous=false;
        lineSegs=new ArrayList<LineSegment>();
    }
    public Occupation(ArrayList<LineSegment> ls){
        continuous=false;
        lineSegs=new ArrayList<LineSegment>(ls);
    }
    
    public void add(Occupation addend){
        for(LineSegment l: addend.getLS()){
            this.lineSegs.add(l);
        }
    }
    
    public Occupation(Path p,Occupation existingOcc,double enterTime,Boolean dir,double sp, Car c){ //continuous occupation constructor from Path getTime
        this.entarTime=enterTime;
        continuous=true;
        ArrayList<LineSegment>EOlineSegs=new ArrayList<LineSegment>(existingOcc.getLS());
        this.beginTime=enterTime;
        this.direction=dir;
        this.myPath=p;
        this.speed=sp;
        this.lineSegs=new ArrayList<LineSegment>();
        Point start;
        Point end;
        //System.out.println(start+end);
        if(dir){
            
            start=new Point(enterTime,0);
                end = new Point(enterTime+p.getDistance()/sp,p.getDistance());
        }
        else{
            start=new Point(enterTime,p.getDistance());
            end = new Point(enterTime+p.getDistance()/sp,0);
        }
        LineSegment curSeg=new LineSegment(start,end);
        
        if(EOlineSegs.size()==0){//if there are no existing occupations
            
        this.lineSegs.add(curSeg);
        //System.out.println("hey"+curSeg);
            
            LineSegment pio = new LineSegment(new Point(end.getX()-c.getBufferData().get(1),end.getY()),new Point(endTime,end.getY()));
            endTime=pio.rightEndPoint().getX();//end.getX()+c.getBufferData().get(1)+c.getBufferData().get(2);
            if(dir){
                //p.getEnd().addPIO(pio);
                myPio = new PIO(p,p.getEnd(),pio);
            }
            else{
                //p.getStart().addPIO(pio);
                myPio = new PIO(p,p.getStart(),pio);
            }
            
        
            
        }
        else{
  //there are existing occupations
        Point curStart=new Point(start);
<<<<<<< HEAD
            
        

=======
            if(curStart.getX()<enterTime||curStart.getY()>p.getDistance()){
                this.lineSegs.add(curSeg);
                endTime=end.getX();
            }
            else{
>>>>>>> parent of 2f721b0... m
        boolean keep=true;
            Line finishLine;
            if(dir){
                finishLine = new Line(new Point(enterTime,p.getDistance()),0);}
            else{
                finishLine = new Line(new Point(enterTime,0),0);
            }

            boolean endOfFollow=false;
            Line temp;
            boolean segReachedEnd=false;
            long counter=0;
            LineSegment ppio=null;
            
        while(keep){
            counter++;
            if(counter>50){
                System.out.println("Occupation 122");
                this.endTime=Double.POSITIVE_INFINITY;
                    keep=false;
                break;
            }
            //System.out.println(curSeg);
            //ExtraMethods.printLSarray(existingOcc.getLS());
            LineSegment collider=curSeg.first(EOlineSegs);
            
            //System.out.println("hey");
            if (collider==null){
                keep=false;
                if(segReachedEnd){
                    if(ppio==null){
                        Point csr = new Point(curSeg.rightEndPoint());
                        
                        LineSegment pStartLS = new LineSegment(new Point(csr.getX()-c.getBufferData().get(1),csr.getY()),new Point(csr.getX()+c.getBufferData().get(1)+c.getBufferData().get(2),csr.getY()));
                        
                        Point latest = pStartLS.getLatestEPCollision(EOlineSegs);
                        
                        int secCounter=0;
                            boolean breakOut=false;
                        while(latest!=null){
                            secCounter++;
                            if(secCounter>30){
                                breakOut=true;
                                break;
                            }
                            csr=new Point(latest.getX()+0.01+c.getBufferData().get(1),latest.getY());
                            pStartLS = new LineSegment(new Point(csr.getX()-c.getBufferData().get(1),csr.getY()),new Point(csr.getX()+c.getBufferData().get(1)+c.getBufferData().get(2),csr.getY()));
                            latest = pStartLS.getLatestEPCollision(EOlineSegs);
                            
                        }
                        if(breakOut){
                            this.endTime=Double.POSITIVE_INFINITY;
                            System.out.println("Occupation 156");
                            keep=false;
                            break;
                        }
                        
                        
                        ppio=new LineSegment(pStartLS);
                        end=new Point(csr);
                        curSeg=new LineSegment(curSeg.leftEndPoint(),end);
                    }
                    else{
                    lineSegs.add(curSeg);
                    endTime=ppio.rightEndPoint().getX();//end.getX();
                        
                        if(dir){
                //p.getEnd().addPIO(ppio);
                            myPio = new PIO(p,p.getEnd(),ppio);
            }
            else{
                //p.getStart().addPIO(ppio);
                myPio = new PIO(p,p.getStart(),ppio);
            }
                        

                        
                        
                    keep=false;
                    break;
                    }
                }
                else if(endOfFollow){
                    lineSegs.add(curSeg);
                    double slop=sp;
                    if(!dir){
                        slop*=-1;
                    }
                    Line tempest=new Line(curSeg.rightEndPoint(),slop);
                    end=tempest.getIntersection(finishLine);
                    curSeg=new LineSegment(curSeg.rightEndPoint(),end);
                   
                    
                    
                    
                    endOfFollow=false;
                    segReachedEnd=true;
                    
                }
            }
            else{
                segReachedEnd=false;
                //System.out.println(c.getTD());
                if((collider.isOppositeSlope(curSeg))){
                    this.endTime=Double.POSITIVE_INFINITY;
                    System.out.println("Occupation 209");
                    keep=false;
                    break;
                }
                else if(curSeg.getSlope()<0&&collider.getSlope()<curSeg.getSlope()){
                    this.endTime=Double.POSITIVE_INFINITY;
                    System.out.println("Occupation 215");
                    keep=false;
                    break;
                }
                else if(curSeg.getSlope()>0&&collider.getSlope()>curSeg.getSlope()){
                    this.endTime=Double.POSITIVE_INFINITY;
                    System.out.println("Occupation 221");
                    keep=false;
                    break;
                }
                else{
                    
                    Point collision=collider.getIntersection(curSeg);
                    if (collider==null){System.out.println("collider is null");}
                    if (collision==null){
                        System.out.println("collision is null");
                    }
                                    
                    System.out.println(c.getBufferData());               
                    Point holder=curSeg.endPointMinus(c.getBufferData().get(0), collider);
                    if (curSeg==null){
                        System.out.println("curSeg is null");
                    }
                    if (holder==null){
                        System.out.println("holder is null");
                    }
                    if(holder==null){
                        System.out.println("NO SPACE FOR PROPER TRAILING DISTANCE");
                        this.endTime=Double.POSITIVE_INFINITY;
                        System.out.println("Occupation 244");
                    keep=false;
                        break;
                    }
                    else if(!curSeg.isInRange(holder.getX())){
                        System.out.println("NO SPACE FOR PROPER TRAILING DISTANCE");
                        this.endTime=Double.POSITIVE_INFINITY;
                    keep=false;
                        break;
                    }
                    lineSegs.add(new LineSegment(curStart,holder));
                    curStart=holder;
                           temp = new Line(curStart,collider.getSlope());
                        end=temp.getPointByX(collider.rightEndPoint().getX());//finishLine.getIntersection(temp);
                    curSeg=new LineSegment(curStart,end);

                    endOfFollow=true;
                    
                    
                    //System.out.println("hey"+curSeg);
                       }
                    
                    
                
            }
            
        }
            
        }
         
    }//ADD CHECKING IF COLLISION IS RESOLVED SO U CAN GO UP
    
    
    
        
    public double getEndTime(){
        return this.endTime;
    }
    
    public double getBeginTime(){
        return this.beginTime;
    }
    
    public boolean getDirection(){
        return this.direction;
    }
    
    public void printEnterTime(){
        System.out.println("ENTAR SANDMAN: "+entarTime);
    }
    
    public ArrayList<LineSegment> getLS(){
        
        return this.lineSegs;
    }
    public PIO getPio(){
        return myPio;
    }
    public void addLS(LineSegment ls){
        lineSegs.add(new LineSegment(ls));
    }
    public void removeLS(LineSegment ls){
        this.lineSegs.remove(ls);
    }
    public String toString(){
        return ExtraMethods.getLSarrayString(this.lineSegs);
    }
    
    
}