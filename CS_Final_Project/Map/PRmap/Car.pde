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
  ArrayList<RouteModule> theRoute;

  Car(double td, double sp) {
    this.maxSpeed=sp;
    this.trailingDistance=td;

    intersectionBufferTime=trailingDistance/this.maxSpeed-0.1;
    if (intersectionBufferTime<=0) {
      intersectionBufferTime=0;
    }
    intersectionOccupationTime=3;
  }

  Car(Intersection initialInt, Path initialPath, Map m, double ms) {

    this.trailingDistance=5;
    intersectionBufferTime=trailingDistance/this.maxSpeed-0.1;
    if (intersectionBufferTime<=0) {
      intersectionBufferTime=0;
    }
    intersectionOccupationTime=3;
    map=m;
    maxSpeed=ms;
    
    println(m.getIntersections().get(5));
    this.getOptimalPath(initialInt,m.getIntersections().get(5));
    print(theRoute);
    
    
  }

  public ArrayList<Double> getBufferData() {
    ArrayList<Double> d = new ArrayList<Double>();
    d.add(this.trailingDistance);
    d.add(intersectionBufferTime);
    d.add(intersectionOccupationTime);
    return d;
  }


  public void show() {
    if(location!=null){
    strokeWeight(6); 
    stroke(255, 255, 255);
    fill(0, 0, 255);
    ellipse((float)location.getX(), (float)location.getY(), 25, 25);
    noStroke();}
  }
  public void update() {
    double time=m.getTime();
    if(this.theRoute!=null){
      RouteModule rm=this.getRouteModule(time);
      if(rm!=null){
        LineSegment appLS=rm.getLSbyTime(time);
        Point intensity=appLS.getPointByX(time);
        Path appPath=rm.getPath();
        LineSegment pathLS=appPath.getLS();
        this.location=pathLS.vector(appPath.getStart().getPoint(),intensity.getY(),true);
      }
      else{
        this.location=null;
      }
      //this.location;
      
    }
  }
  public double getMaxSpeed() {
    return this.maxSpeed;
  }
  public void getOptimalPath(Intersection start, Intersection destination) {
    for (int i=0; i<map.getPaths().size(); i++) {
      map.getPaths().get(i).Setup();
    }
    for (int i=0; i<map.getIntersections().size(); i++) {
      map.getIntersections().get(i).Setup();
    }
    this.startTime=this.map.getTime();
    start.nodify(startTime, this, null);
    if (destination.getNV()>=Double.MAX_VALUE) {
      System.out.println("ERROR CAR 69");
      //do another path
    }
    else{
      //println(destination.getNV());
      theRoute=destination.prepareRoute(start,this);
      
    }


    if (firstRoute) {
      firstRoute=false;
    }
  }
  public RouteModule getRouteModule(double time){
    RouteModule result=null;
    for(int i=0;i<theRoute.size();i++){
      if(theRoute.get(i).getLSbyTime(time)!=null){
        result=theRoute.get(i);
      }
    }
    return result;
  }

}