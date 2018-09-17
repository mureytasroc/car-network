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
  Intersection avoidIntersection;
  


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
      intersectionBufferTime=0.01;
    }
    intersectionOccupationTime=0;
  }

  Car(Intersection initialInt, Intersection initialOtherInt, Map m, double ms) {


    this.trailingDistance=20;
    intersectionBufferTime=trailingDistance/this.maxSpeed-0.1;
    if (intersectionBufferTime<=0) {
      intersectionBufferTime=0.01;
    }
    intersectionOccupationTime=0;
    map=m;
    maxSpeed=ms;

    startTime=this.map.getTime();
    avoidIntersection=initialOtherInt;



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
    print(this.theRoute.get(0).getLS());
    //print(this.theRoute.get(1).getLS());
    
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
      strokeWeight(6); 
      stroke(255, 255, 255);
      fill(0, 0, 255);
      ellipse((float)location.getX(), (float)location.getY(), 25, 25);
      noStroke();
    }
  }
  public void update() {
    double time=m.getTime();
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
          avoidIntersection=theRoute.get(theRoute.size()-1).getPath().getOther(nextStart);
          
          while (nextDestination==nextStart||map.isDestinationBad(nextDestination)){
            Random r = new Random();
            int ind= r.nextInt(m.getIntersections().size());
            nextDestination=m.getIntersections().get(ind);
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
            print("gop not ready");
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
    



          ArrayList<Intersection> checked = new ArrayList<Intersection>();
          checked.add(destination);
          checked.add(start);
    if (destination.getNV()>=Double.MAX_VALUE) {
      //System.out.println("INFINITE TIME CAR 69");
      for(int ind=0;ind<m.getIntersections().size();ind++){
          if(!(map.isDestinationBad(destination)||checked.contains(destination))){
      destination=m.getIntersections().get(ind);
      gopDestination=destination;
    }
    if(destination.getNV()<Double.MAX_VALUE){
      
      break;
    }
    else{
      checked.add(m.getIntersections().get(ind));
    }
      }
      //do another path
    }
        if(destination.getNV()>=Double.MAX_VALUE){
      map.stopAll();
    }
    
    
    
    
    
    
      //println(destination.getNV());
      if (!preemptive) {
        theRoute=destination.prepareRoute(start, this);
      } else {
        nextRoute=destination.prepareRoute(start, this);
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
  public Intersection avoid(){
    return this.avoidIntersection;
  }
  
 
}