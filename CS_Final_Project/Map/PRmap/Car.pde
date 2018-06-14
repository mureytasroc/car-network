public class Car{
  Point location;
  boolean direction;
  double speed=20;
  Car(Point p){
    location=p;
  }
 public void show(){
   strokeWeight(6); 
   stroke(255,255,255);
   fill(0,0,255);
    ellipse((float)location.getX(),(float)location.getY(),25,25);
    noStroke();
  }
  public double getSpeed(){
    return this.speed;
  }
  public void getOptimalPath(){
    
  }
}