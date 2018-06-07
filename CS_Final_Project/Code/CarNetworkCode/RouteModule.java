import java.util.*;
public class RouteModule{
    ArrayList<LineSegment> speedMap;
    Path myPath;
    boolean direction;
    public RouteModule(ArrayList<LineSegment>a, Path p,boolean d){
        speedMap=new ArrayList<LineSegment>(a);
        myPath=p;
        direction=d;
    }
    public boolean getDirection(){
        return direction;
    }
    public boolean getPath(){
        return myPath;
    }
}