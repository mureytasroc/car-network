import java.awt.*;
import java.util.*;

class Path {


  private Color leftColor, rightColor, lmColor, rmColor;
  private LineSegment lineSeg;
  private double wid;
  private Map map;
  private ArrayList<Marker> myMarks;
  private Intersection start;
  private Intersection end;
  private double distance;
  private Occupation occupation=new Occupation();
  private ArrayList<Occupation> possibleOccupation=new ArrayList<Occupation>();
  double speedLimit;

  Path(double dist) {//for testing only
    this.distance=dist;
  }

  Path(Map m, Point p1, Point p2, double w, double sl) {
    this.myMarks=new ArrayList<Marker>();
    this.map=m;
    this.wid=w;
    this.lineSeg=new LineSegment(p1, p2);
    this.start=null;
    this.end=null;
    this.leftColor = new Color(255, 0, 0);
    this.rightColor = new Color(0, 255, 0);
    this.lmColor = new Color(255, 0, 255);
    this.rmColor = new Color(0, 255, 255);
    speedLimit=sl;
  }
  /*public double getTime(Car c, boolean direction, double source) {
   return this.lineSeg.getP1().getDist(this.lineSeg.getP2())/c.getMaxSpeed();
   }*/

  public double getTime(Car c, boolean direction, double source, Path restrictedPath) {
    if (this==restrictedPath) {
      return Double.POSITIVE_INFINITY;
    } else {

      double s=Math.abs(c.getMaxSpeed());
      if (s>speedLimit) {
        s=this.speedLimit;
      }
      double projected=Math.abs((this.getDistance()/s));

      this.possibleOccupation.add(new Occupation(this, this.occupation, source, direction, s, c));

      double et=this.possibleOccupation.get(possibleOccupation.size()-1).getEndTime();
      if (et>=Double.MAX_VALUE) {
        //System.out.println("INFINITE TIME PATH 55");
        projected=Double.POSITIVE_INFINITY;
      } else {
        projected=this.possibleOccupation.get(possibleOccupation.size()-1).getEndTime()-source;
      }

      return projected;
    }
  }


  public Intersection getStart() {
    return this.start;
  }

  public Intersection getEnd() {
    return this.end;
  }

  public void show() {


    strokeWeight((float)(wid*1.0/2.0));
    strokeCap(SQUARE);

    double perpSlope;
    if (this.lineSeg.getSlope()==Double.POSITIVE_INFINITY||this.lineSeg.getSlope()==Double.NEGATIVE_INFINITY) {
      perpSlope=0;
    } else if (this.lineSeg.getSlope()==0) {
      perpSlope=Double.POSITIVE_INFINITY;
    } else {
      perpSlope=-1.0/this.lineSeg.getSlope();
    }
    LineSegment p1PerpLSL = new LineSegment(lineSeg.getP1(), perpSlope, this.wid/4.0);
    Point leftStart = p1PerpLSL.getP2();
    LineSegment p1PerpLSR = new LineSegment(lineSeg.getP1(), perpSlope, -this.wid/4.0);
    Point rightStart = p1PerpLSR.getP2();

    LineSegment p2PerpLSL = new LineSegment(lineSeg.getP2(), perpSlope, this.wid/4.0);
    Point leftEnd = p2PerpLSL.getP2();
    LineSegment p2PerpLSR = new LineSegment(lineSeg.getP2(), perpSlope, -this.wid/4.0);
    Point rightEnd = p2PerpLSR.getP2();

    stroke(rightColor.getRed(), rightColor.getGreen(), rightColor.getBlue());
    line((float)leftStart.getX(), (float)leftStart.getY(), (float)leftEnd.getX(), (float)leftEnd.getY());

    stroke(leftColor.getRed(), leftColor.getGreen(), leftColor.getBlue());
    line((float)rightStart.getX(), (float)rightStart.getY(), (float)rightEnd.getX(), (float)rightEnd.getY());

    /*StdDraw.setPenColor(StdDraw.BLACK);
     StdDraw.setPenRadius(0.005);
     StdDraw.line(lineSeg.getP1().getX(),lineSeg.getP1().getY(),lineSeg.getP2().getX(),lineSeg.getP2().getY());*/
  
    /*fill(255);
    textSize(32);
    text(String.valueOf(map.getPathInd(this)), (float)this.lineSeg.getMidpoint().getX(), (float)this.lineSeg.getMidpoint().getY());*/

}
  public Color getLMC() {
    return this.lmColor;
  }
  public Color getRMC() {
    return this.rmColor;
  }
  public LineSegment getLS() {
    return this.lineSeg;
  }

  public Point getIntersection(Path p) {
    return this.lineSeg.getIntersection(p.getLS());
  }
  public void addMarker(Point p, double dist) {
    this.myMarks.add(new Marker(map, p, dist, this));
  }
  public void drawMarkers() {
    for (int i=0; i<myMarks.size(); i++) {
      myMarks.get(i).drawMarker();
    }
  }
  public Intersection getOther(Intersection i) {
    if (i==this.start) {
      return this.end;
    } else {
      return this.start;
    }
  }
  public void setIntersections(Intersection i) {
    if (this.start==null) {
      this.start=i;
    } else {
      if (Math.abs(start.getPoint().getX()-i.getPoint().getX())<0.001) {
        if (start.getPoint().getY()<i.getPoint().getY()) {
          end=i;
        } else {
          end=start;
          start=i;
        }
      } else {
        if (start.getPoint().getX()<i.getPoint().getX()) {
          end=i;
        } else {
          end=start;
          start=i;
        }
      }

      this.distance=Math.sqrt(Math.abs(Math.pow(start.getPoint().getX()-end.getPoint().getX(), 2)+Math.pow(start.getPoint().getY()-end.getPoint().getY(), 2)));
    }
  }
  public void printIntersections() {//for debugging
    System.out.println(start+", "+end);
  }
  public double getDistance() {
    return this.distance;
  }


  public void Setup() {
    possibleOccupation=new ArrayList<Occupation>();
  }

  public RouteModule confirm(Car c, boolean direction, double endTime) {
    ArrayList<Occupation> dirRightPaths = new ArrayList<Occupation>();
    for (int i=0; i<possibleOccupation.size(); i++) {
      if (possibleOccupation.get(i).getDirection()==direction) {
        dirRightPaths.add(possibleOccupation.get(i));
      }
    }
    double closestStartTimeDif=Double.POSITIVE_INFINITY;
    Occupation closestOcc=null;
    for (int i=0; i<dirRightPaths.size(); i++) {
      if (Math.abs(dirRightPaths.get(i).getEndTime()-endTime)<closestStartTimeDif) {
        closestStartTimeDif=Math.abs(dirRightPaths.get(i).getEndTime()-endTime);
        closestOcc=dirRightPaths.get(i);
      }
    }
    if (closestOcc==null) {
      System.out.println("ERROR PATH 181");
    } else {
      this.occupation.add(closestOcc);
      ArrayList<LineSegment> alls = new ArrayList<LineSegment>(closestOcc.getLS());
      double et=closestOcc.getEndTime();
      boolean dir=closestOcc.getDirection();
      PIO pio = closestOcc.getPio();
      pio.getIntersection().confirmPIO(pio);
      //c.addCarUsedOcc(closestOcc, pio, this);
      //alls.add(pio.getLS());
      return new RouteModule(c, this, alls, et, dir);
    }
    return null;
  }


  public void confirmPIO(PIO pio) {
    LineSegment ls = pio.getLS();
    Point p1 = ls.getP1();
    Point p2 = ls.getP2();
    if (pio.getIntersection()==this.getStart()) {
      this.occupation.addLS(new LineSegment(new Point(p1.getX(), 0), new Point(p2.getX(), 0)));
    } else {
      this.occupation.addLS(new LineSegment(new Point(p1.getX(), this.getDistance()), new Point(p2.getX(), this.getDistance())));
    }
  }

  public String toString() {
    return this.lineSeg.toString();
  }
}