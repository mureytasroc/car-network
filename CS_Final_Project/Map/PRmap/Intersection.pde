public class Intersection{
  Point point;
  ArrayList<Path> myPaths;
  ArrayList<Marker> myMarkers;
  Map map;
 Intersection(Map m, Point p, ArrayList<Path> myp){
   this.map=m;
   myMarkers = new ArrayList<Marker>();
   this.point = p;
   this.myPaths=myp;
 }
 public void addPath(Path p){
   myPaths.add(p);
 }
 public void setMarkers(double dist){
   double d=dist;
   for(int i=0;i<myPaths.size();i++){
     LineSegment thisLS = myPaths.get(i).getLS();
     
     double slope=thisLS.getSlope();
     if(slope==Double.NEGATIVE_INFINITY){
      d*=-1;
     }
     Point p=point.getClosest(thisLS.getP1(),thisLS.getP2());
     myMarkers.add(new Marker(this.map,p,d,myPaths.get(i)));
   }
   
 }
 

  
}