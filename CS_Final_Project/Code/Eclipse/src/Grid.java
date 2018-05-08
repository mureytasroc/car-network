import java.util.*;
import java.awt.Color;
public class Grid {
	
	private ArrayList<Car> myCars;
	private ArrayList<Intersection> myIntersections;
	private ArrayList<Path> myPaths;
	private ArrayList<Path> removePaths;
	private ArrayList<Intersection> removeIntersections;
	private double clock; // this will reset after a new set of instructions are generated and will be used for all cars to reference
	
	private ArrayList<Car> deleteQueue;
	private ArrayList<Path> delayQueue;

	Grid(){
		StdDraw.setCanvasSize(800,800);
		StdDraw.setXscale(0,800);
        StdDraw.setYscale(0,800);
		StdDraw.enableDoubleBuffering();
		this.myCars = new ArrayList<Car>();
		this.deleteQueue = new ArrayList<Car>();
		this.myIntersections = new ArrayList<Intersection>();
		this.myPaths = new ArrayList<Path>();
		this.delayQueue=new ArrayList<Path>();
		this.removePaths = new ArrayList<Path>();
		this.removeIntersections=new ArrayList<Intersection>();
	}
	
	public void setup() {
	for(int i=myPaths.size()-1;i>=0;i--) {
		for (int j=0;j<i;j++) {
			myPaths.get(i).intersects(myPaths.get(j));
		}
		 //This is the update loop like in FishTank
	}
	
	/*for(int i=removePaths.size()-1;i>=0;i--) {
			Path r=removePaths.get(i);
			myPaths.remove(r);
			r.getEnd().removePath(r);
			r.getStart().removePath(r);
			removePaths.remove(r);
		
		 //This is the update loop like in FishTank
	}*/
	for(int i=delayQueue.size()-1;i>=0;i--) {
		myPaths.add(delayQueue.get(i));
		delayQueue.remove(delayQueue.get(i));
}
	for(int i=myIntersections.size()-1;i>=0;i--) {
		for (int j=0;j<i;j++) {
			myIntersections.get(i).tryToEat(myIntersections.get(j));
		}
	}
	for(int i=removeIntersections.size()-1;i>=0;i--) {
		myIntersections.remove(removeIntersections.get(i));
	}
	
}
	public void update() {
		this.show();
		/*for(int i=myPaths.size()-1;i>=0;i--) {
			for (int j=0;j<i;j++) {
				myPaths.get(i).intersects(myPaths.get(j));
			}
			 //This is the update loop like in FishTank
		}
		for(int i=removePaths.size()-1;i>=0;i--) {
				Path r=removePaths.get(i);
				myPaths.remove(r);
				r.getEnd().removePath(r);
				r.getStart().removePath(r);
				removePaths.remove(r);
			
			 //This is the update loop like in FishTank
		}
		for(int i=myIntersections.size()-1;i>=0;i--) {
			for (int j=0;j<i;j++) {
				myIntersections.get(i).tryToEat(myIntersections.get(j));
			}
		}
		for(int i=removeIntersections.size()-1;i>=0;i--) {
			myIntersections.remove(removeIntersections.get(i));
		}*/
		for(int i=myPaths.size()-1;i>=0;i--) {
		myPaths.get(i).update();
		}
		myIntersections.get(3).nodify(0);
		for(int i=myIntersections.size()-1;i>=0;i--) {
			myIntersections.get(i).update();
			//System.out.println(myIntersections.get(i).nodeValue());
			
			 //This is the update loop like in FishTank
		}
		StdDraw.setPenRadius();
		for(int i=myCars.size()-1;i>=0;i--) {
			myCars.get(i).update();
			 //This is the update loop like in FishTank
		}
		
		StdDraw.show();
	}
	
	public void show() {
		StdDraw.setPenColor(new Color(255,0,0));
		StdDraw.filledRectangle(500,500,500,500);
	}
	
	
	public void addCar(Car c) {
		this.myCars.add(c);
	}
	public void addPath(Path p) {
		this.myPaths.add(p);
	}
	public void delayAddPath(Path p) {
		this.delayQueue.add(p);
	}
	public void addIntersection(Intersection i) {
		this.myIntersections.add(i);
	}
	
	public void removeCar(Car c) {
		this.myCars.remove(c);
	}
	public void removePath(Path p) {
		this.removePaths.add(p);
		/*myPaths.remove(p);
		p.getEnd().removePath(p);
		p.getStart().removePath(p);
		removePaths.remove(p);*/
	}
	public void removeIntersection(Intersection i) {
		this.removeIntersections.add(i);
	}
	
	
	public void requestRoute(Car c, Intersection start, Intersection end) {
		ArrayList<Path> cRoute = c.getOptimalPath(start,end,myPaths,myIntersections);
		//check for problems with this path and modify this and other paths as necessary
	
		this.clock=0;
	}
	
	public void collisionCheck() {
		
	}
	

	
	public ArrayList<Path> getMyPaths() {
		return this.myPaths;
	}
	public ArrayList<Car> getMyCars(){
		return this.myCars;
	}
	public ArrayList<Intersection> getMyIntersections(){
		return this.myIntersections;
	}
	
}
