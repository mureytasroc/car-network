import java.awt.*;
import java.util.*;

class Path{

    
    private Color leftColor, rightColor, lmColor, rmColor;
    private LineSegment lineSeg;
    private double wid;
    private Map map;
    private ArrayList<Marker> myMarks;
    
    Path(Map m,Point p1, Point p2,double w){
        this.myMarks=new ArrayList<Marker>();
        this.map=m;
        this.wid=w;
        this.lineSeg=new LineSegment(p1,p2);
        this.leftColor = new Color(255,0,0);
        this.rightColor = new Color(0,255,0);
        this.lmColor = new Color(255,0,255);
        this.rmColor = new Color(0,255,255);
    }
    public void show(){
      
      
        strokeWeight((float)(wid*1.0/2.0));
        strokeCap(SQUARE);
            
        double perpSlope;
        if(this.lineSeg.getSlope()==Double.POSITIVE_INFINITY||this.lineSeg.getSlope()==Double.NEGATIVE_INFINITY){
            perpSlope=0;
        }
        else if(this.lineSeg.getSlope()==0){
            perpSlope=Double.POSITIVE_INFINITY;
        }
        else{
            perpSlope=-1.0/this.lineSeg.getSlope();
        }
        LineSegment p1PerpLSL = new LineSegment(lineSeg.getP1(),perpSlope,this.wid/4.0);
        Point leftStart = p1PerpLSL.getP2();
        LineSegment p1PerpLSR = new LineSegment(lineSeg.getP1(),perpSlope,-this.wid/4.0);
        Point rightStart = p1PerpLSR.getP2();
        
        LineSegment p2PerpLSL = new LineSegment(lineSeg.getP2(),perpSlope,this.wid/4.0);
        Point leftEnd = p2PerpLSL.getP2();
        LineSegment p2PerpLSR = new LineSegment(lineSeg.getP2(),perpSlope,-this.wid/4.0);
        Point rightEnd = p2PerpLSR.getP2();
        
        stroke(rightColor.getRed(),rightColor.getGreen(),rightColor.getBlue());
        line((float)leftStart.getX(),(float)leftStart.getY(),(float)leftEnd.getX(),(float)leftEnd.getY());
        
        stroke(leftColor.getRed(),leftColor.getGreen(),leftColor.getBlue());
        line((float)rightStart.getX(),(float)rightStart.getY(),(float)rightEnd.getX(),(float)rightEnd.getY());
        
        /*StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(lineSeg.getP1().getX(),lineSeg.getP1().getY(),lineSeg.getP2().getX(),lineSeg.getP2().getY());*/
        
    }
    public Color getLMC(){
     return this.lmColor; 
    }
    public Color getRMC(){
     return this.rmColor; 
    }
    public LineSegment getLS(){
      return this.lineSeg;}
      
    public Point getIntersection(Path p){
      return this.lineSeg.getIntersection(p.getLS());
    }
    public void addMarker(Point p, double dist){
      this.myMarks.add(new Marker(map,p,dist,this));
    }
    public void drawMarkers(){
     for(int i=0;i<myMarks.size();i++){
      myMarks.get(i).drawMarker(); 
     }
    }
    
    
    
    
    
    
    
    
}