import java.util.ArrayList;

public class Intersection {
	private Location loc;
	private Grid myGrid;
	private ArrayList<Path> myPaths;
	private double nodeValue=Double.MAX_VALUE;
	
	
	Intersection(Location l){
		this.loc = l;
		myGrid=l.getGrid();
		this.myGrid.addIntersection(this);
		this.myPaths = new ArrayList<Path>();
	}
	public void nodify(double source) {
		if (source<nodeValue) {
			nodeValue=source;
			for (int i=0; i<myPaths.size();i++) {
				Path p=myPaths.get(i);
				if (this==p.getStart()) {
					p.getEnd().nodify((nodeValue+p.getDistance()));
				}
				else {
					p.getStart().nodify((nodeValue+p.getDistance()));
				}
			}
		}
	}
	public double nodeValue() {
		return nodeValue;
	}
	Intersection(Car c){
		this.loc=c.getLocation();
		myGrid=c.getGrid();
		this.myGrid.addIntersection(this);
		this.myPaths = new ArrayList<Path>();	
	}
	public ArrayList<Path> getPaths(){
		return myPaths;
	}
	public void addPath(Path p) {
		myPaths.add(p);
	}
	public void removePath(Path p) {
		myPaths.remove(p);
	}
	public Location getLoc() {
		return this.loc;
	}
	public Grid getGrid() {
		return myGrid;
	}
	public void update() {
		
		this.show();
		this.nodeValue=Double.MAX_VALUE;
	}
	public boolean tryToEat(Intersection i) {
		boolean out=false;
		if (i.getLoc().getPos()[0]==this.getLoc().getPos()[0]&&i.getLoc().getPos()[1]==this.getLoc().getPos()[1]) {
			ArrayList<Path> iPaths=i.getPaths();
			for(int t=0; t<i.getPaths().size();t++) {
				Path p=iPaths.get(t);
				this.myPaths.add(p);
				if (i==p.getStart()) {
					p.changeStart(this);
				}
				else {
					p.changeEnd(this);
				}
			}
			myGrid.removeIntersection(i);
			out=true;
		}
		return out;
	}
	public void show() {
		StdDraw.setPenColor(0,155,0);
		StdDraw.filledRectangle(this.loc.getPos()[0], this.loc.getPos()[1], 15, 15);
		StdDraw.setPenColor(255,255,255);
		StdDraw.text(this.loc.getPos()[0], this.loc.getPos()[1], (new Integer((int)(nodeValue))).toString() );
		System.out.println(nodeValue);
	}
}
