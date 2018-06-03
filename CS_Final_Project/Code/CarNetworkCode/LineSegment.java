public class LineSegment{
    private Point p1;
    private Point p2;
    private double slope;
    LineSegment(Point inP1, Point inP2){
        this.slope=inP1.slope(inP2);
        this.p1=inP1;
        this.p2=inP2;
    }
    
}