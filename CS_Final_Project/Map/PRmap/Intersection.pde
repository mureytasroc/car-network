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
     double d=Math.abs(dist);
     LineSegment thisLS = myPaths.get(i).getLS();
     
   
     
     Point c=point.getClosest(thisLS.getP1(),thisLS.getP2());
     Point f=point.getFarthest(thisLS.getP1(),thisLS.getP2());
     double x1=c.getX();
     double y1=c.getY();
     double x2=f.getX();
     double y2=f.getY();
     if(Math.abs(x1-x2)<0.001){
      if(y2<y1){
        d*=-1;
      }
     }
     else{
       if(x2<x1){
        d*=-1; 
       }
     }
            //System.out.println(thisLS.getSlope()+",  "+d);
            //System.out.println(myPaths.size());
     LineSegment temp = new LineSegment(c,thisLS.getSlope(),d/2.0);
     Point p = temp.getP2();
     myMarkers.add(new Marker(this.map,p,d,myPaths.get(i)));
     myMarkers.get(myMarkers.size()-1).drawMarker();
   }
   
 }
 

  
}