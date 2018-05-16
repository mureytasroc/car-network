
public class Intersection {
	private Location loc;
	Intersection(Location l){
		this.loc = l;
	}
	Intersection(Car c){
		this.loc=c.getLocation();
	}
}
