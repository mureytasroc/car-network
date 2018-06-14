import java.util.*;
import java.util.Collections;

class RouteModule{
  private Path path;
  private double endTime;
  private ArrayList<LineSegment> profile;
  
  RouteModule(Path p, double ET, ArrayList<LineSegment> prof){
    path=p;
    endTime=ET;
    profile=prof;
  }
  public Path getPath(){
    return path;
  }
  public double getEndTime(){
    return endTime;
  }
  public ArrayList<LineSegment> getProfile(){
    return profile;
  }
  
}