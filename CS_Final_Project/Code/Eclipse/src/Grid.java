import java.util.*;
public class Grid {
	
	private ArrayList<Car> myCars;
	private ArrayList<Intersection> myIntersections;
	private ArrayList<Path> myPaths;
	private double clock; // this will reset after a new set of instructions are generated and will be used for all cars to reference
	
	private ArrayList<Car> deleteQueue;

	Grid(){
		this.myCars = new ArrayList<Car>();
		this.deleteQueue = new ArrayList<Car>();
		this.myIntersections = new ArrayList<Intersection>();
		this.myPaths = new ArrayList<Path>();
	}
	
	
	public void update() {
		for(int i=0;i<myCars.size();i++) {
			myCars.get(i).update();
			 //This is the update loop like in FishTank
		}
		
		for(int i=deleteQueue.size()-1;i>=0;i--) { //This loop removes all Cars that are queued for delete after the update loop is finished so there is no index confusion inside the update loop
			myCars.remove(deleteQueue.get(i));
			deleteQueue.remove(i);
		}
		
		show();
		
	}
	
	public void show() {
	
		
		
		
		
		
	}
	
	
	public void addCar(Car c) {
		this.myCars.add(c);
	}
	public void removeCar(Car c) {
		this.deleteQueue.add(c);
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
