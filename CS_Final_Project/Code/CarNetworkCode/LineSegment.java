import java.lang.*;
import java.util.*;
public class LineSegment implements Comparable{
    
    public static void main(String[] args){//tester code
        /*
        //compareTo test
        LineSegment l1 = new LineSegment(new Point(-1,0),new Point(1,0));
        LineSegment l2 = new LineSegment(new Point(0,-1),new Point(0,1));
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l1.compareTo(l2));*/
        LineSegment l1 = new LineSegment(new Point(4,3),new Point(7,5));
        LineSegment l2 = new LineSegment(new Point(5,0),new Point(10,6));
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l1.getIntersection(l2));
    }
    
    
    
    private Point p1;
    private Point p2;
    private double slope;
    LineSegment(Point inP1, Point inP2){
        this.slope=inP1.slope(inP2);
        this.p1=inP1;
        this.p2=inP2;
    }
    public Point getP1(){
        return this.p1;
    }
    public Point getP2(){
        return this.p2;
    }
    public Line getLine(){
        return new Line(this.p1,this.p2);
    }
    public double getSlope(){
        return p1.slope(p2);
    }
    public Point getIntersection(Line l){
        Point lineInt = l.getIntersection(this.getLine());
        if(lineInt==null){
            return null;
        }
        double y=lineInt.getY();
        double x=lineInt.getX();
        
        if(this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY){
            double higherY;
            double lowerY;
            if(this.p1.getY()>this.p2.getY()){
                higherY=this.p1.getY();
                lowerY=this.p2.getY();
            }
            else{
                higherY=this.p2.getY();
                lowerY=this.p1.getY();
            }
            if(y>=lowerY && y<=higherY){
                return new Point(x,y);
            }
        }
        else{
            double higherX;
            double lowerX;
            if(this.p1.getX()>this.p2.getX()){
                higherX=this.p1.getX();
                lowerX=this.p2.getX();
            }
            else{
                higherX=this.p2.getX();
                lowerX=this.p1.getX();
            }
            if(x>=lowerX && x<=higherX){
                return new Point(x,y);
            }
        }
        return null;
    }
    public Point getIntersection(LineSegment l){
        Line lsLine = l.getLine();
        boolean didInt=true;
        Point intPoint=this.getIntersection(lsLine);
        if(intPoint==null){
            didInt=false;
        }
        if(l.getIntersection(this.getLine())==null){
            didInt=false;
        }
        
        if(didInt){
            return intPoint;
        }
        else{
            return null;
        }
        
    }
    
    public String toString(){
        String ls=this.getLine().toString();
        String add;
        if(this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY){
            double higherY;
            double lowerY;
            if(this.p1.getY()>this.p2.getY()){
                higherY=this.p1.getY();
                lowerY=this.p2.getY();
            }
            else{
                higherY=this.p2.getY();
                lowerY=this.p1.getY();
            }
            add=(" {"+higherY+">=y>="+lowerY+"}");
            
        }
        else{
            double higherX;
            double lowerX;
            if(this.p1.getX()>this.p2.getX()){
                higherX=this.p1.getX();
                lowerX=this.p2.getX();
            }
            else{
                higherX=this.p2.getX();
                lowerX=this.p1.getX();
            }
            add=(" {"+higherX+">=x>="+lowerX+"}");
        }
        ls+=add;
        return ls;
    }
    
    public double getAvgX(){
        return (this.p1.getX()/2.0+this.p2.getX()/2.0);
    }
    public double getAvgY(){
        return (this.p1.getY()/2.0+this.p2.getY()/2.0);
    }
    public LineSegment first(ArrayList<LineSegment> al){
        Point out=null;
        LineSegment output=null;
        for(LineSegment l2: al){
            Point curPoint=null;
            curPoint=this.getIntersection(l2);
            if (curPoint==null){
                
            }
            else
                if(out==null)
                { out=curPoint;output=l2;}
                else if(curPoint.getY()<out.getY())
                { out=curPoint;output=l2;}
        }
        return output;
    }
    public int compareTo(Object ob){
        if ( ! (ob instanceof LineSegment) ){
            return 1;}
        LineSegment ls = (LineSegment)ob;
        double x1=this.getAvgX();
        double y1=this.getAvgY();
        double x2=ls.getAvgX();
        double y2=ls.getAvgY();
        if(x1==x2){
            if(y1==y2){
                return 0;
            }
            else if(y1<y2){
                return 1;
            }
            else{
                return -1;
            }
        }
        else{
            if(x1>x2){
                return 1;
            }
            else{
                return -1;
            }
        }
        
    }
    
    
}