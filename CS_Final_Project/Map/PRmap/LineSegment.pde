import java.lang.*;
import java.util.*;
class LineSegment { //implements Comparable{



  private Point p1;
  private Point p2;
  private double slope;
  LineSegment(Point inP1, Point inP2) {
    this.slope=inP1.slope(inP2);
    this.p1=inP1;
    this.p2=inP2;
  }
  LineSegment(Point inP1, double slope, double length) {
    this.p1=inP1;
    if (slope==Double.POSITIVE_INFINITY||slope==Double.NEGATIVE_INFINITY) {
      this.p2=new Point(p1.getX(), p1.getY()+length);
    } else {
      double dX;
      double dY;
      dX=length/(Math.sqrt(slope*slope+1));
      dY=slope*dX;
      this.p2=new Point(p1.getX()+dX, p1.getY()+dY);
    }
  }
  public Point getP1() {
    return this.p1;
  }
  public Point getP2() {
    return this.p2;
  }
  public Line getLine() {
    return new Line(this.p1, this.p2);
  }
  public double getSlope() {
    return p1.slope(p2);
  }
  public Point getIntersection(Line l) {
    Point lineInt = l.getIntersection(this.getLine());
    if (lineInt==null) {
      return null;
    }
    double y=lineInt.getY();
    double x=lineInt.getX();

    if (this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY) {
      double higherY;
      double lowerY;
      if (this.p1.getY()>this.p2.getY()) {
        higherY=this.p1.getY();
        lowerY=this.p2.getY();
      } else {
        higherY=this.p2.getY();
        lowerY=this.p1.getY();
      }
      if (y>=lowerY && y<=higherY) {
        return new Point(x, y);
      }
    } else {
      double higherX;
      double lowerX;
      if (this.p1.getX()>this.p2.getX()) {
        higherX=this.p1.getX();
        lowerX=this.p2.getX();
      } else {
        higherX=this.p2.getX();
        lowerX=this.p1.getX();
      }
      if (x>=lowerX && x<=higherX) {
        return new Point(x, y);
      }
    }
    return null;
  }
  public boolean hasCollision(LineSegment l) {//test
    if (l==null) {
      return false;
    }
    Point lineInt = l.getLine().getIntersection(this.getLine());
    if (lineInt==null) {
      if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
        if (Math.abs(l.getLine().getPointByY(this.p1.getY()).getX()-this.p1.getX())<0.0001) {
          double hy1=this.higherEndPoint().getY();
          double ly1=this.lowerEndPoint().getY();
          double hy2=l.higherEndPoint().getY();
          double ly2=l.lowerEndPoint().getY();
          if (hy1>=ly2&&hy1<hy2||hy2>=ly1&&hy2<hy1) {
            return true;
          } else {
            return false;
          }
        } else {
          return false;
        }
      } else {
        if (Math.abs(l.getLine().getPointByX(this.p1.getX()).getY()-this.p1.getY())<0.0001) {
          double rx1=this.rightEndPoint().getX();
          double lx1=this.leftEndPoint().getX();
          double rx2=l.rightEndPoint().getX();
          double lx2=l.leftEndPoint().getX();
          if (rx1>=lx2&&rx1<rx2||rx2>=lx1&&rx2<rx1) {
            return true;
          } else {
            return false;
          }
        } else {
          return false;
        }
      }
    } else {
      Point inte = this.getIntersection(l);
      if (inte!=null) {
        return true;
      } else {
        return false;
      }
    }
  }
  public boolean isInRange(double x) {
    if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
      if (Math.abs(this.p1.getX()-x)<0.0001) {
        return true;
      } else {
        return false;
      }
    } else {
      if (x>this.leftEndPoint().getX()-0.0001&&x<this.rightEndPoint().getX()+0.0001) {
        return true;
      } else {
        return false;
      }
    }
  }
  public boolean isInDomain(double y) {
    if (this.getSlope()==0) {
      if (Math.abs(this.p1.getY()-y)<0.0001) {
        return true;
      } else {
        return false;
      }
    } else {
      if (y>this.lowerEndPoint().getY()-0.0001&&y<this.higherEndPoint().getY()+0.0001) {
        return true;
      } else {
        return false;
      }
    }
  }
  public Point rightEndPoint() {
    if (this.p1.getX()>this.p2.getX()) {
      return p1;
    }
    if (this.p1.getX()<this.p2.getX()) {
      return p2;
    }
    System.out.println("RIGHT_END_POINT_NULL");
    return null;
  }
  public Point leftEndPoint() {
    if (this.p1.getX()>this.p2.getX()) {
      return p2;
    }
    if (this.p1.getX()<this.p2.getX()) {
      return p1;
    }
    System.out.println("LEFT_END_POINT_NULL");
    return null;
  }
  public Point lowerEndPoint() {
    if (this.p1.getY()>this.p2.getY()) {
      return p2;
    }
    if (this.p1.getY()<this.p2.getY()) {
      return p1;
    }
    System.out.println("LOWER_END_POINT_NULL");
    return null;
  }
  public Point higherEndPoint() {
    if (this.p1.getY()>this.p2.getY()) {
      return p1;
    }
    if (this.p1.getY()<this.p2.getY()) {
      return p2;
    }
    System.out.println("HIGHER_END_POINT_NULL");
    return null;
  }
  public Point getIntersection(LineSegment l) {
    Line lsLine = l.getLine();
    boolean didInt=true;
    Point intPoint=this.getIntersection(lsLine);
    if (intPoint==null) {
      didInt=false;
    }
    if (l.getIntersection(this.getLine())==null) {
      didInt=false;
    }

    if (didInt) {
      return intPoint;
    } else {
      return null;
    }
  }

  /*public String toString(boolean old){
   String ls=this.getLine().toString();
   String add;
   if(this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY){
   double higherY;
   double lowerY;
   System.out.println(p1+"  "+p2);
   if(this.p1.getY()>this.p2.getY()){
   higherY=this.p1.getY();
   lowerY=this.p2.getY();
   }
   else{
   higherY=this.p2.getY();
   lowerY=this.p1.getY();
   }
   add=(" {"+higherY+">=y>="+lowerY+"}");
   
   }
   else{
   double higherX;
   double lowerX;
   if(this.p1.getX()>this.p2.getX()){
   higherX=this.p1.getX();
   lowerX=this.p2.getX();
   }
   else{
   higherX=this.p2.getX();
   lowerX=this.p1.getX();
   }
   add=(" {"+higherX+">=x>="+lowerX+"}");
   }
   ls+=add;
   return ls;
   }*/

  public String toString() {
    String ls="((1-t)*(";
    ls+=this.p1.getX();
    ls+=")+t*(";
    ls+=this.p2.getX();
    ls+="),(1-t)*(";
    ls+=this.p1.getY();
    ls+=")+t*(";
    ls+=this.p2.getY();
    ls+="))";

    return ls;
  }

  public double getAvgX() {
    return (this.p1.getX()/2.0+this.p2.getX()/2.0);
  }
  public double getAvgY() {
    return (this.p1.getY()/2.0+this.p2.getY()/2.0);
  }
  public LineSegment firstIntersection(ArrayList<LineSegment> al) {
    Point out=null;
    LineSegment output=null;
    for (int i=0; i<al.size(); i++) {
      Point curPoint=this.getIntersection(al.get(i));
      if (curPoint!=null) {
        if (out==null)
        { 
          out=new Point(curPoint);
          output=al.get(i);
        } else if (curPoint.getX()<out.getX())
        { 
          out=new Point(curPoint);
          output=al.get(i);
        }
      }
    }
    return output;
  }

  public Point getPointByX(double x) {

    double higherX;
    double lowerX;
    if (this.p1.getX()>this.p2.getX()) {
      higherX=this.p1.getX();
      lowerX=this.p2.getX();
    } else {
      higherX=this.p2.getX();
      lowerX=this.p1.getX();
    }

    if (x>higherX+0.0001||x<lowerX-0.0001) {
      return null;
    } else if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
      return null;
    } else {
      return new Point(x, this.getSlope()*(x-p1.getX())+p1.getY());
    }
  }

  public Point getPointByY(double y) {
    double higherY;
    double lowerY;
    if (this.p1.getY()>this.p2.getY()) {
      higherY=this.p1.getY();
      lowerY=this.p2.getY();
    } else {
      higherY=this.p2.getY();
      lowerY=this.p1.getY();
    }
    if (y>higherY||y<lowerY) {
      return null;
    } else if (this.getSlope()==0) {
      return null;
    } else {
      return new Point((y-p1.getY())/this.getSlope()+p1.getX(), y);
    }
  }

  public Point endPointMinus(double TD, LineSegment collider) {

    if (this.isOppositeSlope(collider)) {//THIS METHOD CAN ONLY BE USED IF THE SLOPE OF THIS LS IS SAME SIGN AS COLLIDER
      return null;
    }

    Point collision = this.getIntersection(collider);
    if (collision==null) {//THERE MUST BE AN INTERSECTION
      return null;
    }

    if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
      return null;
    }
    if (collider.getSlope()==Double.POSITIVE_INFINITY||collider.getSlope()==Double.NEGATIVE_INFINITY) {
      return null;
    }

    double awaySlope=this.getSlope()-collider.getSlope();


    double dX=-TD/Math.abs(awaySlope);//-0.3125
    double x=collision.getX()+dX;
    return this.getPointByX(x);
  }

  /*public int compareTo(Object ob){
   if ( ! (ob instanceof LineSegment) ){
   return 1;}
   LineSegment ls = (LineSegment)ob;
   double x1=this.getAvgX();
   double y1=this.getAvgY();
   double x2=ls.getAvgX();
   double y2=ls.getAvgY();
   if(x1==x2){
   if(y1==y2){
   return 0;
   }
   else if(y1<y2){
   return 1;
   }
   else{
   return -1;
   }
   }
   else{
   if(x1>x2){
   return 1;
   }
   else{
   return -1;
   }
   }
   
   }*/

  public boolean isOppositeSlope(LineSegment l) {
    if (this.getSlope()==0&&l.getSlope()==0) {
      return true;
    }
    if ((this.getSlope()>0&&l.getSlope()<0)||(this.getSlope()<0&&l.getSlope()>0)) {
      return true;
    }
    return false;
  }

  public Point vector(Point p, double d, boolean dir) {
    double distance = Math.abs(d);
    if (!dir) {
      distance*=-1;
    }
    Point out;
    if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
      out=new Point(p.getX(), p.getY()+distance);
    } else {
      double dX=distance/Math.sqrt(1+this.getSlope()*this.getSlope());
      double dY=dX*this.getSlope();
      out=new Point(p.getX()+dX, p.getY()+dY);
    }
    return out;
  }

  public Point getLatestEPCollision(ArrayList<LineSegment> ls) {//test
    double farthestRight=Double.NEGATIVE_INFINITY;
    LineSegment fr=null;
    for (int i=0; i<ls.size(); i++) {
      if (this.hasCollision(ls.get(i))) {
        if (ls.get(i).rightEndPoint().getX()>farthestRight) {
          farthestRight=ls.get(i).rightEndPoint().getX();
          fr=ls.get(i);
        }
      }
    }
    if (fr==null) {
      return null;
    } else {
      return new Point(fr.rightEndPoint());
    }
  }
  public Point getMidpoint(){
    return new Point((this.p1.getX()+this.p2.getX())/2.0,(this.p1.getY()+this.p2.getY())/2.0);
  }
}