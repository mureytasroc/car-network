public class Point{
    private double x;
    private double y;
    Point(double xIn, double yIn){
        this.x=xIn;
        this.y=yIn;
    }
    public double slope(Point p){
        double m=0;
        double dY=p.getY()-this.y;
        double dX=p.getX()-this.x;
        if(Math.abs(dX)<Math.abs(dY/(double.MAX_VALUE))){
            return null;
        }
        else{
            return dY/dX;
        }
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
}

//m=double.MAX_VALUE
//m=y/x
//x=y/m