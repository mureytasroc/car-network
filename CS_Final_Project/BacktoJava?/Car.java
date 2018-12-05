import java.util.*;
public class Car {
  Map map;
  Point location;
  boolean direction;
  boolean firstRoute=true;
  double maxSpeed=20;
  double trailingDistance;
  double intersectionBufferTime;
  double intersectionOccupationTime;
  double startTime;
  double nextStartTime;
  ArrayList<RouteModule> theRoute;
  ArrayList<RouteModule> nextRoute;
  ArrayList<RouteModule> nextNextRoute;
  Intersection start;
  Intersection destination;
  Intersection nextStart;
  Intersection nextDestination;
  Intersection nextNextStart;
  Intersection nextNextDestination;

  boolean newRequestArmed=true;

  //getoptimalpathparam
  Intersection gopStart;
  Intersection gopDestination;
  double gopST;
  boolean gopPreemptive;
  boolean gopReady;


  Car(double td, double sp) {
    this.maxSpeed=sp;
    this.trailingDistance=td;

    intersectionBufferTime=trailingDistance/this.maxSpeed-0.1;
    if (intersectionBufferTime<=0) {
      intersectionBufferTime=0;
    }
    intersectionOccupationTime=0.1;
  }

  Car(Intersection initialInt, Path initialPath, Map m, double ms) {


    this.trailingDistance=20;
    intersectionBufferTime=trailingDistance/this.maxSpeed-0.1;
    if (intersectionBufferTime<=0) {
      intersectionBufferTime=0;
    }
    intersectionOccupationTime=0.1;
    map=m;
    maxSpeed=ms;

    startTime=this.map.getTime();



    start=initialInt;
    destination=start;
    while (destination==start||map.isDestinationBad(nextDestination)) {
      Random r = new Random();
      int ind= r.nextInt(m.getIntersections().size());
      destination=m.getIntersections().get(ind);
    }


    gopStart=start;
    gopDestination=destination;
    gopST=startTime;
    gopPreemptive=false;
    gopReady=false;
    this.getOptimalPath();
    //print(theRoute);
  }

  public ArrayList<Double> getBufferData() {
    ArrayList<Double> d = new ArrayList<Double>();
    d.add(this.trailingDistance);
    d.add(intersectionBufferTime);
    d.add(intersectionOccupationTime);
    return d;
  }


  public void show() {
    if (location!=null) {
        System.out.println("car 89");
      StdDraw.setPenRadius(6); 
      StdDraw.setPenColor(255, 255, 255);
      StdDraw.setPenColor(0, 0, 255);
      StdDraw.filledEllipse((float)location.getX(), (float)location.getY(), 25, 25);
      //noStroke();
    }
  }
  public void update() {
    double time=map.getTime();
    if (this.theRoute!=null) {
      RouteModule rm=this.getRouteModule(time);
      if (rm!=null) {
        LineSegment appLS=rm.getLSbyTime(time);
        Point intensity=appLS.getPointByX(time);
        Path appPath=rm.getPath();
        LineSegment pathLS=appPath.getLS();
        this.location=pathLS.vector(appPath.getStart().getPoint(), intensity.getY(), true);

        if (theRoute.indexOf(rm)==theRoute.size()-1&&newRequestArmed) {
          newRequestArmed=false;

          nextStart=destination;
          nextDestination=nextStart;
          while (nextDestination==nextStart||nextDestination==theRoute.get(theRoute.size()-1).getPath().getOther(nextStart)||map.isDestinationBad(nextDestination)) {
            Random r = new Random();
            int ind= r.nextInt(map.getIntersections().size());
            nextDestination=map.getIntersections().get(ind);
          }
          nextStartTime=theRoute.get(theRoute.size()-1).getEndTime()+0.00001;

          gopStart=nextStart;
          gopDestination=nextDestination;
          gopST=nextStartTime;
          gopPreemptive=true;
          gopReady=false;
          this.getOptimalPath();
        }

        if (theRoute.indexOf(rm)==theRoute.size()-1&&this.location.appxEquals(destination.getPoint())) {

          while (!gopReady) {
            System.out.print("gop not ready");
          }

          newRequestArmed=true;
          this.start=nextStart;
          this.destination=nextDestination;
          this.theRoute=nextRoute;
          nextRoute=null;
          startTime=nextStartTime;
        }
      } else {
        //this.location=null;
      }
      //this.location;
    }
  }
  public double getMaxSpeed() {
    return this.maxSpeed;
  }
  public void getOptimalPath() {

    Intersection start=gopStart;
    Intersection destination=gopDestination;
    boolean preemptive=gopPreemptive;
    double st=gopST;

    if (firstRoute) {
      nextRoute=null;
    }
    for (int i=0; i<map.getPaths().size(); i++) {
      map.getPaths().get(i).Setup();
    }
    for (int i=0; i<map.getIntersections().size(); i++) {
      map.getIntersections().get(i).Setup();
    }
    if (!preemptive) {
      start.nodify(st, this, null, null);
    } else {
      start.nodify(st, this, null, theRoute.get(theRoute.size()-1).getPath());
    }
    if (destination.getNV()>=Double.MAX_VALUE) {
      System.out.println("ERROR CAR 69");
      //do another path
    } else {
      //println(destination.getNV());
      if (!preemptive) {
        theRoute=destination.prepareRoute(start, this);
      } else {
        nextRoute=destination.prepareRoute(start, this);
      }
    }


    if (firstRoute) {
      firstRoute=false;
    }
    gopReady=true;
  }
  public RouteModule getRouteModule(double time) {
    RouteModule result=null;
    for (int i=0; i<theRoute.size(); i++) {
      if (theRoute.get(i).getLSbyTime(time)!=null) {
        result=theRoute.get(i);
      }
    }
    return result;
  }

  public Intersection getDestination() {
    return this.destination;
  }
}