class Line {



  private Point point;
  private double slope;
  Line(Point p, double m) {
    this.slope=m;
    this.point=p;
  }
  Line(Point p1, Point p2) {
    this.point=p1;
    this.slope=p1.slope(p2);
  }
  public Point getIntersection(Line l) {
    if ((Math.abs(this.slope-l.getSlope())<0.0001)||((this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY)&&(l.getSlope()==Double.POSITIVE_INFINITY||l.getSlope()==Double.NEGATIVE_INFINITY))) {
      return null;
    } else {//lines are not the same or parallel
      double m1=this.slope;
      double m2=l.getSlope();
      double p1x = this.point.getX();
      double p1y=this.point.getY();
      double p2x=l.getPoint().getX();
      double p2y=l.getPoint().getY();

      double x;
      double y;

      if (this.slope==Double.POSITIVE_INFINITY||this.slope==Double.NEGATIVE_INFINITY) { //if this line is verticle
        x=this.point.getX();
        y=m2*x-m2*p2x+p2y;
      } else if (l.getSlope()==Double.POSITIVE_INFINITY||l.getSlope()==Double.NEGATIVE_INFINITY) { //if that line is verticle
        x=l.getPoint().getX();
        y=m1*x-m1*p1x+p1y;
      } else {
        x = (m2*p2x-p2y-m1*p1x+p1y)/(m2-m1);
        y = m1*x-m1*p1x+p1y;
      }

      return new Point(x, y);
    }
  }



  public Point getPointByX(double x) {
    if (this.getSlope()==Double.POSITIVE_INFINITY||this.getSlope()==Double.NEGATIVE_INFINITY) {
      return null;
    } else {
      return new Point(x, this.getSlope()*(x-this.point.getX())+this.point.getY());
    }
  }
  public Point getPointByY(double y) {
    if (this.getSlope()==0) {
      return null;
    } else {
      return new Point((y-point.getY())/this.getSlope()+point.getX(), y);
    }
  }



  public boolean doesIntersect(Point p, double precision) {
    double m=this.slope;
    double y = m*p.getX()-m*this.point.getX()+this.point.getY();
    if (Math.abs(y-p.getY())<=precision) {
      return true;
    }
    return false;
  }
  public boolean equals(Line l, double precision) {
    if (Math.abs(this.slope-l.getSlope())<precision) {
      if (this.doesIntersect(l.getPoint(), precision)) {
        return true;
      }
    }
    return false;
  }
  public double getSlope() {
    return this.slope;
  }
  public Point getPoint() {
    return this.point;
  }
  public double getYInt() {
    double m1=this.slope;
    double p1x = this.point.getX();
    double p1y=this.point.getY();
    if (m1==Double.POSITIVE_INFINITY || m1==Double.NEGATIVE_INFINITY) {
      return Double.POSITIVE_INFINITY;
    } else {
      return (-m1*p1x+p1y);
    }
  }
  public double getXInt() {
    double m1=this.slope;
    double p1x = this.point.getX();
    double p1y=this.point.getY();
    if (m1==0) {
      return Double.POSITIVE_INFINITY;
    } else {
      if (m1==Double.POSITIVE_INFINITY||m1==Double.NEGATIVE_INFINITY) {
        return this.point.getX();
      } else {
        return ((-p1y+m1*p1x)/m1);
      }
    }
  }


  public String toString() {
    double m1=this.slope;
    double xi=this.getXInt();
    double yi=this.getYInt();
    String s="";
    if (Math.abs(m1)<0.00001) {
      s=("y = "+yi);
    } else if (yi==Double.POSITIVE_INFINITY||yi==Double.NEGATIVE_INFINITY) {
      s=("x = "+xi);
    } else {
      s =("y = ("+this.slope+")x");
      if (yi<0) {
        s+=" - ";
      } else {
        s+=" + ";
      }
      s+=Double.toString(Math.abs(yi));
    }
    return s;
  }
}