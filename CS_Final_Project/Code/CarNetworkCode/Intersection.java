import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;
public class Intersection implements Comparable {
	private Location loc;
	private Grid myGrid;
	private ArrayList<Path> myPaths;
	private double nodeValue=Double.MAX_VALUE;
	private Color col;
	private boolean edible=true;
	
	
	Intersection(Location l){
		this.loc = l;
		myGrid=l.getGrid();
		this.myGrid.addIntersection(this);
		this.myPaths = new ArrayList<Path>();
		this.col=new Color(0,155,0);
	}
	Intersection(Location l,boolean special){
		this.loc = l;
		myGrid=l.getGrid();
		this.myGrid.addIntersection(this);
		this.myPaths = new ArrayList<Path>();
		this.col=new Color(0,155,0);
		if (special) {
		edible=false;
		}
		
	}
    public void specialize(){
        edible=!edible;
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
    public void nodify(double source, Car c) {
		if (source<nodeValue) {
			nodeValue=source;
			for (int i=0; i<myPaths.size();i++) {
				Path p=myPaths.get(i);
				if (this==p.getStart()) {
					p.getEnd().nodify((nodeValue+p.getDistance(c)),c);
				}
				else if (this==p.getEnd()){
					p.getStart().nodify((nodeValue+p.getDistance(c)),c);
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
	public void setup() {
		this.nodeValue=Double.MAX_VALUE;
	}
	public void update() {
		
		this.show();
		//this.nodeValue=Double.MAX_VALUE;
	}
	public boolean tryToEat(Intersection i) {
		boolean out=false;
		if (i.getLoc().getPos()[0]==this.getLoc().getPos()[0]&&i.getLoc().getPos()[1]==this.getLoc().getPos()[1]&&i.isEdible()&&this.isEdible()) {
			ArrayList<Path> iPaths=i.getPaths();
			for(int t=0; t<i.getPaths().size();t++) {
				Path p=iPaths.get(t);
				this.myPaths.add(p);
				if (i==p.getStart()) {
					p.changeStart(this);
				}
				else if (i==p.getEnd()){
					p.changeEnd(this);
				}
			}
			myGrid.delayRemoveIntersection(i);
			out=true;
		}
		return out;
	}
	public boolean isEdible(){
		//return edible;
		return true;
	}
	public void show() {
		StdDraw.setPenColor(col);
		StdDraw.filledRectangle(this.loc.getPos()[0], this.loc.getPos()[1], 13, 13);
		if(!this.edible) {StdDraw.setPenColor(255,255,0); StdDraw.filledRectangle(this.loc.getPos()[0], this.loc.getPos()[1], 15, 15);}
		StdDraw.setPenColor(0,0,0);
        StdDraw.setPenRadius(0.03);
		StdDraw.text(this.loc.getPos()[0], this.loc.getPos()[1], (new Integer((int)(nodeValue))).toString() );
		/*System.out.println("hey");
		System.out.println(nodeValue);
		for(int i=0; i<this.myPaths.size();i++) {
			System.out.println("intr");
		System.out.println(this.myPaths.get(i).getDistance());
		System.out.println(this.myPaths.get(i).getEnd().nodeValue());
		System.out.println(this.myPaths.get(i).getStart().nodeValue());
		}*/
	}
	public int compareTo(Object o) {
		int out=1;
		if (o instanceof Intersection) {
			Intersection i=((Intersection)o);
		if (i.getLoc().getPos()[0]>this.getLoc().getPos()[0]) {
			out=-1;
		}
		else if (i.getLoc().getPos()[0]==this.getLoc().getPos()[0]&&i.getLoc().getPos()[1]>this.getLoc().getPos()[1]) {
			out=-1;
		}
		}
		return out;
	}
}
