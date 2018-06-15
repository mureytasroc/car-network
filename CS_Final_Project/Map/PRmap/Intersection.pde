public class Intersection{
  Point point;
  ArrayList<Path> myPaths;
  ArrayList<Marker> myMarkers;
  Map map;
  Path leadingPath;
  double nodeValue=Double.MAX_VALUE;
 Intersection(Map m, Point p, ArrayList<Path> myp){
   this.map=m;
   myMarkers = new ArrayList<Marker>();
   this.point = p;
   this.myPaths=myp;
 }
 public void addPath(Path p){
   myPaths.add(p);
 }
 public Point getPoint(){
   return point;
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
 
 /*public void nodify(double source, Car c, Path inPath){
    if (source<this.nodeValue) {
            this.leadingPath=inPath;
      nodeValue=source;
      for (int i=0; i<this.myPaths.size();i++) {
        Path p=myPaths.get(i);
 
                Boolean direction=Boolean.valueOf(false);
            if(p.getStart()==this){
              direction=Boolean.valueOf(true);
            }
                p.getOther(this).nodify((nodeValue+p.getTime(c,direction,source)),c,p);
      }
    }
        else{
        } 
 }*/
 public void setupPaths(){
   for(int i=0;i<myPaths.size();i++){
     myPaths.get(i).setIntersections(this);
   }
 }
 
 public String toString(){
   return this.point.toString();
 }
 

  
}