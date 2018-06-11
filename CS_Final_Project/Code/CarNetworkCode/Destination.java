import java.util.*;
public class Destination{
    private Path myPath;
    private double distance;
    private Location spot;
    public Destination(Path p, double d){
        myPath=p;
        double distance=d;
        double slope=myPath.getSlope();
        double xinc=distance/Math.pow((1+slope*slope),0.5);
        spot=new Location(p.getGrid(),((Point)myPath.getStart()).getX()+xinc,((Point)myPath.getStart()).getY()+xinc*slope);
            
            
        
    }
    public Destination(Location l){
        myPath=l.snapToPath();
        spot=l;
        double slope=myPath.getSlope();
        double distance=Math.pow(Math.pow(((Point)myPath.getStart()).getX()-((Point)spot).getX(),2)
                                 +Math.pow(((Point)myPath.getStart()).getY()-((Point)spot).getY(),2),0.5);
        
        
    }
    public ArrayList<Intersection> getInts(){
        ArrayList<Intersection> output=new ArrayList<Intersection>();
        output.add(myPath.getStart());
        output.add(myPath.getEnd());
        return output;
    }
}