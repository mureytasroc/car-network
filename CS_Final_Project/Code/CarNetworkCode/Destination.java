public class Destination{
    private Path myPath;
    private double distance;
    private Location spot;
    Destination(Path p, double d){
        myPath=p;
        double distance=d;
        spot=new Location((Point)myPath.getStart().getX+distance*myPath.getSlope())
        
    }
    Destination(Location l){
        myPath=l.snapToPath();
        spot=l;
        
    }
}