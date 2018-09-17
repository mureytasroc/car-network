import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Map {
  ArrayList<Path> myPaths;
  ArrayList<Intersection> myIntersections;
  ArrayList<Car>myCars;
  private double lineWidth;
  private long janTime;
  private boolean stopAll=false;
double speedLim=100;
public ServerEcho se;
public String outgoing="";

  public Map(double lw) {
    janTime=System.currentTimeMillis();
    this.lineWidth=lw;
    myCars=new ArrayList<Car>();
    myPaths=new ArrayList<Path>();
    myIntersections=new ArrayList<Intersection>();
    try{se = new ServerEcho(3000);}catch (Exception e){;}
    
  }

  public void drawGrid(int horRoads, int vertRoads, int horMarkers, int vertMarkers, double markerDist) {

    myIntersections.clear();


    double leftx=lineWidth*3.0/2.0;
    double rightx=width-lineWidth*3.0/2.0;
    double inWidth=rightx-leftx;

    double topy=lineWidth*3.0/2.0;
    double boty=height-lineWidth*3.0/2.0;
    double inHeight=boty-topy;


    //this.addPath(new Path(this,new Point(lineWidth*3/2,lineWidth*2),new Point(lineWidth*3/2,height-lineWidth*2),lineWidth));
    //this.addPath(new Path(this,new Point(lineWidth*2,lineWidth*3/2),new Point(width-lineWidth*2,lineWidth*3/2),lineWidth));
    //this.addPath(new Path(this,new Point(width-lineWidth*3/2,lineWidth*2),new Point(width-lineWidth*3/2,height-lineWidth*2),lineWidth));
    //this.addPath(new Path(this,new Point(lineWidth*2,height-lineWidth*3/2),new Point(width-lineWidth*2,height-lineWidth*3/2),lineWidth)); 

    for (int i=0; i<=horRoads+1; i++) {
      double y = topy+inHeight*i/((double)(horRoads+1));
      for (int s=0; s<=vertRoads; s++) {
        double x1=leftx+inWidth*s/((double)(vertRoads+1));
        double x2=leftx+inWidth*(s+1)/((double)(vertRoads+1));
        //System.out.println("("+x1+", "+y+")");
        myIntersections.add(new Intersection(this, new Point(x1, y), new ArrayList<Path>()));

        x1+= lineWidth/2.0;
        x2-= lineWidth/2.0;

        this.addPath(new Path(this, new Point(x1, y), new Point(x2, y), lineWidth, speedLim));

        for (int b=1; b<=vertMarkers; b++) {
          double pathWidth=x2-x1;
          double xm=x1+pathWidth*b/((double)(vertMarkers+1));
          this.myPaths.get(myPaths.size()-1).addMarker(new Point(xm, y), markerDist);
        }

        myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-1));
        if (s>0) {
          myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-2));
        }
      }
      myIntersections.add(new Intersection(this, new Point(rightx, y), new ArrayList<Path>()));
      myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-1));
    }


    for (int i=0; i<=vertRoads+1; i++) {
      double x = leftx+inWidth*i/((double)(vertRoads+1));
      for (int s=0; s<=horRoads; s++) {
        double y1=topy+inHeight*s/((double)(horRoads+1));
        double y2=topy+inHeight*(s+1)/((double)(horRoads+1));
        y1+= lineWidth/2.0;
        y2-= lineWidth/2.0;

        int interIndex=(vertRoads+2)*s+i;

        this.addPath(new Path(this, new Point(x, y1), new Point(x, y2), lineWidth, speedLim));

        for (int b=1; b<=horMarkers; b++) {
          double pathWidth=y2-y1;
          double ym=y1+pathWidth*b/((double)(horMarkers+1));
          this.myPaths.get(myPaths.size()-1).addMarker(new Point(x, ym), markerDist);
        }

        myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-1));
        if (s>0) {
          myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-2));
        }
      }

      int interIndex=(vertRoads+2)*(horRoads+1)+i;
      myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-1));

      //this.addBlock(x,lineWidth*3/2);
      //this.addBlock(x,height-lineWidth*3/2);
    }
  }

  public void Setup() {
    for (int i=0; i<myIntersections.size(); i++) {
      myIntersections.get(i).setupPaths();
    }
  }

  public ArrayList<Path> getPaths() {
    return this.myPaths;
  }
  public ArrayList<Intersection> getIntersections() {
    return this.myIntersections;
  }

  public void addPath(Path p) {
    myPaths.add(p);
  }
  public double getLineWidth() {
    return this.lineWidth;
  }
  public void update(double markerDist) throws UnknownHostException, IOException {
    if(!stopAll){
    background(0);
    for (int i=0; i<myPaths.size(); i++) {
      myPaths.get(i).show();
      myPaths.get(i).drawMarkers();
    }
    //System.out.println("\n\n\n\n");
    for (int i=0; i<myIntersections.size(); i++) {
      myIntersections.get(i).setMarkers(markerDist);
    }
    for (int i=0; i<myCars.size(); i++) {
      myCars.get(i).update();
      myCars.get(i).show();
    }}
    else{
      System.out.println("STOPPED");
    }
    
    if(se.available()){
        System.out.print(se.getIncoming());
            se.sendOutput("deez");}
    
  }
  public double getTime() {
    return (double)(System.currentTimeMillis()-janTime)/1000.0;
  }
  public void addCar(Car c) {
    this.myCars.add(c);
  }
  public boolean isDestinationBad(Intersection in){
    for(int i=0;i<myCars.size();i++){
      if(in==myCars.get(i).getDestination()){
        return true;
      }
    }
    return false;
  }
  
  public void stopAll(){
    stopAll=true;
  }
  public int getIntInd(Intersection i){
    return myIntersections.indexOf(i);
  }
  public int getPathInd(Path p){
    return myPaths.indexOf(p);
  }
}