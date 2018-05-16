import java.util.*;

public class Path {
	private Intersection start;
	private Intersection end;
	private float distance;
	private float speedLim;
	
	ArrayList<Car> myCars; //Experimental -- should each path have a list of cars?
	
	Path(Intersection s, Intersection e, float dist, float SL){
		this.start=s;
		this.end=e;
		this.distance=dist;
		this.speedLim=SL;
	}
	
	public float[] getTime(float speed){ //returns {time,maxSpeed}
		float[] returnAr = new float[2];
		
		if(speed>this.speedLim) { //check for speed of slowest car on path? -- this could be factored into dijstra's algo to account for multiple cars in network
			returnAr[1]=speedLim;
		}
		else {
			returnAr[1]=speed;
		}
		returnAr[0] = this.distance/returnAr[1];
		
		return returnAr;
	}
	
	public Intersection getStart() {
		return this.start;
	}
	public Intersection getEnd() {
		return this.end;
	}
	public float getDistance() {
		return this.distance;
	}
	public float getSpeedLim() {
		return this.speedLim;
	}
	
}
