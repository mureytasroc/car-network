import java.awt.*;
import java.util.*;


public class Marker{
  private Point point;
  private double distance;
  private Path path;
  private Map map;
 Marker(Map m,Point p, double dist, Path pa){
   this.map=m;
   this.path=pa;
   this.point=p;
   this.distance=dist;
 }
 public void drawMarker(){
   double wid=this.map.getLineWidth();
   LineSegment pathLS=this.path.getLS();
   
   LineSegment firstSeg=new LineSegment(point,pathLS.getSlope(),distance/2.0);
   LineSegment secSeg=new LineSegment(point,pathLS.getSlope(),-distance/2.0);
   
    drawSeg(wid,firstSeg,path.getLMC(),path.getRMC());
    drawSeg(wid,secSeg,path.getRMC(),path.getLMC());
    
    
 }
 
 private void drawSeg(double wid,LineSegment lineSeg, Color leftColor, Color rightColor){
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
 }
  
}