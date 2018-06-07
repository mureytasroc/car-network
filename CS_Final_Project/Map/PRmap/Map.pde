import java.awt.*;
import java.util.*;

class Map{
    ArrayList<Path> myPaths;
    ArrayList<Intersection> myIntersections;
    private double lineWidth;
    
    
    public Map(double lw){
      this.lineWidth=lw;
        myPaths=new ArrayList<Path>();
        myIntersections=new ArrayList<Intersection>();
    }
    
    public void drawGrid(int horRoads, int vertRoads){
      
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
        
        for(int i=0;i<=horRoads+1;i++){
          double y = topy+inHeight*i/((double)(horRoads+1));
          for(int s=0;s<=vertRoads;s++){
            double x1=leftx+inWidth*s/((double)(vertRoads+1));
            double x2=leftx+inWidth*(s+1)/((double)(vertRoads+1));
            
            myIntersections.add(new Intersection(this,new Point(x1,y),new ArrayList<Path>()));
            
            x1+= lineWidth/2.0;
            x2-= lineWidth/2.0;
            
            this.addPath(new Path(this,new Point(x1,y),new Point(x2,y),lineWidth));
            myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-1));
            if(s>0){
              myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-2));
            }
          }
          myIntersections.add(new Intersection(this,new Point(rightx,y),new ArrayList<Path>()));
          myIntersections.get(myIntersections.size()-1).addPath(myPaths.get(myPaths.size()-1));
          
          
        }
        
        
        for(int i=0;i<=vertRoads+1;i++){
          double x = leftx+inWidth*i/((double)(vertRoads+1));
          for(int s=0;s<=horRoads;s++){
            double y1=topy+inHeight*s/((double)(horRoads+1));
            double y2=topy+inHeight*(s+1)/((double)(horRoads+1));
            y1+= lineWidth/2.0;
            y2-= lineWidth/2.0;
            
            int interIndex=(horRoads+2)*i+s;
            
            this.addPath(new Path(this,new Point(x,y1),new Point(x,y2),lineWidth));
            myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-1));
            if(s>0){
              myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-2));
            }
            
            
          }
          
          int interIndex=(horRoads+2)*i+horRoads+1;
          myIntersections.get(interIndex).addPath(myPaths.get(myPaths.size()-1));
          
          //this.addBlock(x,lineWidth*3/2);
          //this.addBlock(x,height-lineWidth*3/2);
          
        }
        
      
      
    }
    
    public ArrayList<Path> getPaths(){
        return this.myPaths;
    }

    public void addPath(Path p){
        myPaths.add(p);
    }
    public double getLineWidth(){
      return this.lineWidth; 
    }
    public void update(int horMarkers, int vertMarkers, double markerDist){

        for(int i=0;i<myPaths.size();i++){
            myPaths.get(i).show();
        }
        
        for(int i=0;i<myIntersections.size();i++){
         myIntersections.get(i).setMarkers(markerDist); 
        }

    }
}