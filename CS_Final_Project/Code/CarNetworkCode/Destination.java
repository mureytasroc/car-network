public class Destination{
    private Path myPath;
    private double distance;
    private Location spot;
    Destination(Path p, double d){
        myPath=p;
        
    }
    Destination(Location l){
        myPath=l.snapToPath();
        spot=l;
    }
}