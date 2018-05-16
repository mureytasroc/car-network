
public class Location {
	
	private float[] coords;
	
	Location(float xPos, float yPos){
		this.coords=snapToPath(xPos,yPos);
	}
	Location(Location l){
		this.coords = l.getPos();
	}
<<<<<<< HEAD
	public Grid getGrid() {
		return myGrid;
	}
	public int travel(Path p,double mag) {
		int out=0;
		double slope=p.getSlope();
		if (slope<Double.MAX_VALUE&&slope>-Double.MAX_VALUE) {
		double inc=mag/Math.pow((1+slope*slope),0.5);
		coords[0]+=inc;
		coords[1]+=slope*inc;
		}
		else {coords[1]+=mag;}
		if(p.getStart().getLoc().getPos()[0]>p.getEnd().getLoc().getPos()[0]) {
			if (coords[0]>p.getStart().getLoc().getPos()[0]) {
				out=1; coords[0]=(p.getStart().getLoc().getPos()[0]);
				coords[1]=(p.getStart().getLoc().getPos()[1]);
				}
			else if(coords[0]<p.getEnd().getLoc().getPos()[0]) {
				out=2; coords[0]=(p.getEnd().getLoc().getPos()[0]);
				coords[1]=(p.getEnd().getLoc().getPos()[1]);
			}
		}
		else if (p.getStart().getLoc().getPos()[0]<p.getEnd().getLoc().getPos()[0]){
			if(coords[0]<p.getStart().getLoc().getPos()[0]) {
				out=1; coords[0]=(p.getStart().getLoc().getPos()[0]);
				coords[1]=(p.getStart().getLoc().getPos()[1]);
			}
			else if(coords[0]>p.getEnd().getLoc().getPos()[0]) {
				out=2; coords[0]=(p.getEnd().getLoc().getPos()[0]);
				coords[1]=(p.getEnd().getLoc().getPos()[1]);
			}
		}
		else if(p.getStart().getLoc().getPos()[1]>p.getEnd().getLoc().getPos()[1]) {
			if (coords[1]>p.getStart().getLoc().getPos()[1]) {
				out=1; coords[0]=(p.getStart().getLoc().getPos()[0]);
				coords[1]=(p.getStart().getLoc().getPos()[1]);
				}
			else if(coords[1]<p.getEnd().getLoc().getPos()[1]) {
				out=2; coords[0]=(p.getEnd().getLoc().getPos()[0]);
				coords[1]=(p.getEnd().getLoc().getPos()[1]);
			}
		}
		else {
			if(coords[1]<p.getStart().getLoc().getPos()[1]) {
				out=1; coords[0]=(p.getStart().getLoc().getPos()[0]);
				coords[1]=(p.getStart().getLoc().getPos()[1]);
			}
			else if(coords[1]>p.getEnd().getLoc().getPos()[1]) {
				out=2; coords[0]=(p.getEnd().getLoc().getPos()[0]);
				coords[1]=(p.getEnd().getLoc().getPos()[1]);
			}
		}
		
		return out;
	}
	public Path snapToPath() {
		ArrayList<Path> paths=myGrid.getMyPaths();
		if (paths.size()>0) {
		double x=coords[0];
		double y=coords[1];
		double bestPathDist=Double.MAX_VALUE;
		int bestPathIndex=0;
		for(int i=0; i<paths.size();i++) {
			Path p=paths.get(i);
			double x1=((p.getStart().getLoc().getPos())[0]);
			double y1=((p.getStart().getLoc().getPos())[1]);
			double x2=((p.getEnd().getLoc().getPos())[0]);
			double y2=((p.getEnd().getLoc().getPos())[1]);
			double d20=Math.pow((x2-x),2)+Math.pow((y2-y),2);
	        double d21=Math.pow((x2-x1),2)+Math.pow((y2-y1),2);
	        double d10=Math.pow((x1-x),2)+Math.pow((y1-y),2);
	        double dist2=Double.MAX_VALUE;
			if (d20>d21+d10) {
			dist2=d10;
			}
			else if (d10>d21+d20){
				dist2=d20;
			}
			else {
				double A=((-d20+d21+d10)/(2*( d21)));
				double dx=A*(x2-x1);
				double dy=A*(y2-y1);
				dist2=(x1+dx-x)*(x1+dx-x)+(y1+dy-y)*(y1+dy-y);
				
			}
			if (dist2<=bestPathDist) {bestPathDist=dist2; bestPathIndex=i;}
		}
		Path p=paths.get(bestPathIndex);
		double x1=((p.getStart().getLoc().getPos())[0]);
		double y1=((p.getStart().getLoc().getPos())[1]);
		double x2=((p.getEnd().getLoc().getPos())[0]);
		double y2=((p.getEnd().getLoc().getPos())[1]);
		double d20=Math.pow((x2-x),2)+Math.pow((y2-y),2);
        double d21=Math.pow((x2-x1),2)+Math.pow((y2-y1),2);
        double d10=Math.pow((x1-x),2)+Math.pow((y1-y),2);
		if (d20>d21+d10) {
			coords[0]=x1;
			coords[1]=y1;
		}
		else if (d10>d21+d20){
			coords[0]=x2;
			coords[1]=y2;
		}
		else {
			double A=((-d20+d21+d10)/(2*(d21)));
			double dx=A*(x2-x1);
			double dy=A*(y2-y1);
			coords[0]=x1+dx;
			coords[1]=y1+dy;
			
		}
		return p;
	}
		else {
			return null;
		}
=======
	
	public float[] snapToPath(float x, float y) {
		float[] point = new float[2];
>>>>>>> fc4fcc5eae45caab5d7b95a8fd0fbc5f3cb8a5ca
		//Snap x and y to the closest path
		return point;
	}
<<<<<<< HEAD
	public Path snapToPath(Path p) {
		double x=coords[0];
		double y=coords[1];
		ArrayList<Path> paths=myGrid.getMyPaths();
		double x1=((p.getStart().getLoc().getPos())[0]);
		double y1=((p.getStart().getLoc().getPos())[1]);
		double x2=((p.getEnd().getLoc().getPos())[0]);
		double y2=((p.getEnd().getLoc().getPos())[1]);
		double d20=Math.pow((x2-x),2)+Math.pow((y2-y),2);
        double d21=Math.pow((x2-x1),2)+Math.pow((y2-y1),2);
        double d10=Math.pow((x1-x),2)+Math.pow((y1-y),2);
		if (d20>d21+d10) {
			coords[0]=x1;
			coords[1]=y1;
		}
		else if (d10>d21+d20){
			coords[0]=x2;
			coords[1]=y2;
		}
		else {
			double A=((-d20+d21+d10)/(2*(d21)));
			double dx=A*(x2-x1);
			double dy=A*(y2-y1);
			coords[0]=x1+dx;
			coords[1]=y1+dy;
			
		}
		return p;
		//Snap x and y to specific path
		
	}
	public double[] getPos() {
=======
	
	public float[] getPos() {
>>>>>>> fc4fcc5eae45caab5d7b95a8fd0fbc5f3cb8a5ca
		return this.coords;
	}
	
	public boolean equals(Location l) {
		boolean eq=false;
		//check if this is the same location as l, watch out for floating point precision
		return eq;
	}
	
}
