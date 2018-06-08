import java.util.*;
public class RouteModule{
    private ArrayList<LineSegment> speedMap;
    private Path myPath;
    private boolean direction;
    private Car c;
    private Grid myGrid;
    public String toString(){
        return ("a"+myPath.toString());
    }
    public ArrayList<LineSegment> getSpeedMap(){
        return speedMap;
    }
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
    public boolean advance(double offset){
        double newLoc=ExtraMethods.parseLoc(myGrid.getTime()+offset,speedMap);
        double theSpeed=ExtraMethods.parseSpeed(myGrid.getTime()+offset,speedMap);
        if(direction){theSpeed=1;}
        else{theSpeed=-1;}
        //System.out.println(myPath);
        if(Double.isNaN(newLoc)){
            System.out.println("heya");
            //newLoc=5;
        }
        System.out.println(newLoc);
        //if(c.getLocation().teleport(myPath,newLoc)>0){
        
        //c.getLocation().travel(myPath,theSpeed,true);
        if(c.getLocation().travel(myPath,theSpeed,true)>0){
            //System.out.println("yaaaaaaaaaaaaaay");
            return true;
            
        }
        return false;
    }
}