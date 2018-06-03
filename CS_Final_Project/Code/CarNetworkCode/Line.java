public class Line{
    private Point point;
    private double slope;
    Line(Point p, double m){
        this.slope=m;
        this.point=p;
    }
    Line(Point p1, Point p2){
        this.point=p;
        this.slope=p1.slope(p2);
    }
    public Point getIntersection(Line l){
        
    }
    public boolean doesIntersect(Point p){
        if(m)
    }
    public boolean equals(Line l){
        if(this.slope==l.getSlope()){
            if(this.)
        }
        else{
            return false;
        }
    }
    public double getSlope(){
        
    }
    
    

}