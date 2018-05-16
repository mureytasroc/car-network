import java.util.*;
import java.awt.Color;
public class Car{
	private Grid myGrid;
	private Location loc;
	private ArrayList<Intersection> myRoute;
	private Path curPath;
	private double speed=-0.5;
	private Intersection destination;
	
	Car(Location l){
		this.myRoute=new ArrayList<Intersection>();
		this.myGrid=l.getGrid();
		this.loc=l;
		l.snapToPath();
		this.destination=new Intersection(l);
		this.curPath=this.loc.snapToPath();
		this.myGrid.addCar(this);
	}
	Car(Location l,Location d){
		this.myRoute=new ArrayList<Intersection>();
		this.myGrid=l.getGrid();
		this.loc=l;
		d.snapToPath();
		this.destination=new Intersection(d);
		this.curPath=this.loc.snapToPath();
		this.myGrid.addCar(this);
	}
	public void update() {
		curPath=this.loc.snapToPath();
		int t=this.loc.travel(curPath,speed);
		if(t>0) {
			System.out.println("do something");
			speed=.5;
			this.loc.travel(curPath,speed);
		}
		if(t==2) {
			System.out.println("do something");
			speed=-.5;
			this.loc.travel(curPath,speed);
		}
		this.show();
		//CAR UPDATE METHOD
		
	}
	public void show() {
		StdDraw.setPenColor(new Color(0,0,255));
		StdDraw.filledRectangle(this.loc.getPos()[0],this.loc.getPos()[1], 10, 10);
		
	}
	public ArrayList<Path> getOptimalPath(Intersection start, Intersection end, ArrayList<Path> paths, ArrayList<Intersection> intersections) {
		ArrayList<Path> path = new ArrayList<Path>();
		//Run dijkstra's algo 
		return path;
	}
	public Location getLocation() {
		return this.loc;
	}
	/*public Intersection getNextIntersection() {
		return this.myRoute.get(0).getEnd();
	}
	public Intersection getDestination() {
		int lastIndex=this.myRoute.size()-1;
		return this.myRoute.get(lastIndex).getEnd();
	}*/
	public Grid getGrid() {
		return myGrid;
	}

}
