import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;

class Point { //implements Comparable{
  private double x;
  private double y;
  public Point(double xIn, double yIn) {
    this.x=xIn;
    this.y=yIn;
  }
  public Point (Point p) {
    this.x=p.getX();
    this.y=p.getY();
  }
  public double slope(Point p) {
    if (p==null)
      return 1.0/0;
    double dY=p.getY()-this.y;
    double dX=p.getX()-this.x;
    if (dX==0) {
      if (dY==0) {
        return Double.POSITIVE_INFINITY;
      }
      if (dY>0) {
        return Double.POSITIVE_INFINITY;
      }
      if (dY<0) {
        return Double.NEGATIVE_INFINITY;
      }
    }
    return dY/dX;
  }
  public double getX() {
    return this.x;
  }
  public double getY() {
    return this.y;
  }
  public String toString() {
    String xs=Double.toString((this.x));
    String ys=Double.toString((this.y));
    return ("("+xs+", "+ys+")");
  }
  /*public int compareTo(Object o) {
   int out=1;
   if (o instanceof Point) {
   Point i=((Point)o);
   if (i.getY()>this.getY()) {
   out=-1;
   }
   else if (i.getY()==this.getY()&&i.getX()<this.getX()) {
   out=-1;
   }
   }
   System.out.println("POINT 48: COMPARED OBJECT IS NOT A POINT");
   return out;
   }*/

  public Point getClosest(Point a, Point b) {
    double distA = this.getDist(a);
    double distB = this.getDist(b);
    if (distA==distB) {
      return null;
    }
    if (distA>distB) {
      return b;
    } else {
      return a;
    }
  }
  public Point getFarthest(Point a, Point b) {
    double distA = this.getDist(a);
    double distB = this.getDist(b);
    if (distA==distB) {
      return null;
    }
    if (distA<distB) {
      return b;
    } else {
      return a;
    }
  }
  public double getDist(Point p) {
    return (Math.sqrt(Math.pow(p.getX()-this.getX(), 2)+Math.pow(p.getY()-this.getY(), 2)));
  }
  public boolean appxEquals(Point p){
    return this.getDist(p)<0.1;
  }
}

//m=double.MAX_VALUE
//m=y/x
//x=y/m