
public class Location {
	
	private float[] coords;
	
	Location(float xPos, float yPos){
		this.coords=snapToPath(xPos,yPos);
	}
	Location(Location l){
		this.coords = l.getPos();
	}
	
	public float[] snapToPath(float x, float y) {
		float[] point = new float[2];
		//Snap x and y to the closest path
		return point;
	}
	
	public float[] getPos() {
		return this.coords;
	}
	
	public boolean equals(Location l) {
		boolean eq=false;
		//check if this is the same location as l, watch out for floating point precision
		return eq;
	}
	
}
