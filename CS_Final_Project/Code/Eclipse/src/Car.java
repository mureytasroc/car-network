import java.util.*;

public class Car{
	private Grid myGrid;
	private Location loc;
	private ArrayList<Path> myRoute;
	
	Car(Grid g, Location l){
		myRoute=new ArrayList<Path>();
		this.myGrid=g;
		this.loc=l;
	}
	
	public void update() {
		
		//CAR UPDATE METHOD
		
	}
	
	public ArrayList<Path> getOptimalPath(Intersection start, Intersection end, ArrayList<Path> paths, ArrayList<Intersection> intersections) {
		ArrayList<Path> path = new ArrayList<Path>();
		//Run dijkstra's algo 
		return path;
	}
	public Location getLocation() {
		return this.loc;
	}
	public Intersection getNextIntersection() {
		return this.myRoute.get(0).getEnd();
	}
	public Intersection getDestination() {
		int lastIndex=this.myRoute.size()-1;
		return this.myRoute.get(lastIndex).getEnd();
	}

}
