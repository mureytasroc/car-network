public class RouteModule {
  Car myCar;
  Path path;
  ArrayList<LineSegment> lineSegs;
  double endTime;
  boolean direction;
  RouteModule(Car c, Path p, ArrayList<LineSegment> ls, double et, boolean dir) {
    myCar=c;
    endTime=et;
    lineSegs=ls;
    path=p;
    myCar=c;
  }
  public String toString(){
    ArrayList<LineSegment> ls =lineSegs;
    String str="";
        for(int i=0;i<ls.size();i++){
               if(i==ls.size()-1){
                   str+=(ls.get(i));
               }
               else{
            str+=(ls.get(i)+", ");}
        }
        return str;
  }
  
  public String toString(boolean b){
     return path.getStart().toString();
  }
  
  
  public ArrayList<LineSegment> getLS(){
   return this.lineSegs; 
  }
  public LineSegment getLSbyTime(double time){
    LineSegment result=null;
    for(int i=0;i<lineSegs.size();i++){
      if(lineSegs.get(i).isInRange(time)){
        result=lineSegs.get(i);
      }
    }
    return result;
  }
  public Path getPath(){
    return this.path;
  }
  public boolean getDirection(){
    return direction;
  }
  public double getEndTime(){
    return endTime;
  }

}