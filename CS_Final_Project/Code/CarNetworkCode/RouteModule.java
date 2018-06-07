import java.util.*;
public class RouteModule{
    ArrayList<LineSegment> speedMap;
    Path myPath;
    boolean direction;
    Car c;
    Grid myGrid;
    public RouteModule(ArrayList<LineSegment>a, Path p,boolean d, Car c){
        speedMap=new ArrayList<LineSegment>(a);
        myPath=p;
        direction=d;
        this.c=c;
        myGrid=c.getGrid();
    }
    public boolean getDirection(){
        return direction;
    }
    public Path getPath(){
        return myPath;
    }
    public boolean advance(){
        double newLoc=ExtraMethods.parseLoc(myGrid.getTime(),speedMap);
        if(Double.isNaN(newLoc)){
            System.out.println("heya");
            //newLoc=5;
        }
        if(c.getLocation().teleport(myPath,newLoc)>0){
            return true;
        }
        return false;
    }
}