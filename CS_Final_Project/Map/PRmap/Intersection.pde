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
   for(int i=0;i<myPaths.size();i++){
     double slope=myPaths.get(i).getLS().getSlope();
     myMarkers.add(new Marker(this.map,p,dist,myPaths.get(i)));
   }
   
 }
 

  
}