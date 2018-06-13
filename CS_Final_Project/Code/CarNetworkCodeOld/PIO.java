public class PIO{
    private Path path;
    private Intersection intersection;
    private LineSegment lineSeg;
    PIO(Path p,Intersection inte,LineSegment l){
        path=p;
        intersection = inte;
        lineSeg=l;
        //lineSeg=this;
    }
    public LineSegment getLS(){
        return lineSeg;
    }
    public Path getPath(){
        return path;
    }
    public Intersection getIntersection(){
        return intersection;
    }

}