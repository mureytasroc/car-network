import java.util.ArrayList;
import java.util.Collections;
import java.awt.*;


public class Intersection implements Comparable<Intersection> {
	private Location loc;
	private Grid myGrid;
	private ArrayList<Path> myPaths;
	private double nodeValue=Double.MAX_VALUE;
	private Color col;
	private boolean edible=true;
	private Intersection prevIntersection;
  private Path leadingPath;
	
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
	/*public void nodify(double source) {//redacted
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
    public void nodify(double source, Car c) {//recursive
		if (source<this.nodeValue) {
			nodeValue=source;
			for (int i=0; i<this.myPaths.size();i++) {
				Path p=myPaths.get(i);
					p.getOther(this).nodify((nodeValue+p.getDistance(c)),c,p);
			}
		}
	}*/
    // In later implementations, source will be passed as a parameter to the getDistance method
    public void nodify(double source, Car c, Path inPath, double startTime) {//recursive
        
		if (source<this.nodeValue) {
            this.leadingPath=inPath;
			nodeValue=source;
			for (int i=0; i<this.myPaths.size();i++) {
				Path p=myPaths.get(i);
                
				//p.getOther(this).nodify((nodeValue+p.getDistance(c)),c,p);
                Boolean direction=Boolean.valueOf(false);
          	if(p.getStart()==this){
              direction=Boolean.valueOf(true);
            }
                
                p.getOther(this).nodify((nodeValue+p.getTime(c,this,direction,startTime+source)),c,p,startTime);
                
                //getDistance(c,this,source) //where this defines directionality
			}
		}
	}

     public Route prepareRoute(Intersection start, Car c, double startTime){
        //System.out.println(startTime);
        if(this == start){
            Route myRoute = new Route(c);
            return myRoute;
        }
        else{
            //System.out.println("this far");
            Route myRoute = new Route(this.leadingPath.getOther(this).prepareRoute(start,c,startTime));
            boolean direction=false;
          	if(leadingPath.getEnd()==this){
              direction=true;
            }
            myRoute.addModule(new RouteModule(this.leadingPath.confirm(),this.leadingPath,direction,c));
            //System.out.println("hasdfk;hk"+this.leadingPath);
            return myRoute;
        }
    }
    public ArrayList<Path> collectRoute(Intersection start, ArrayList<Boolean> dirs, Car c, double startTime){
        //System.out.println(startTime);
        if(this == start){
            ArrayList<Path> myList = new ArrayList<Path>();
            return myList;
        }
        else{
            //System.out.println("this far");
            ArrayList<Path> myList = new ArrayList<Path>(this.leadingPath.getOther(this).collectRoute(start,dirs,c,startTime));
            myList.add(this.leadingPath);
            
            
          	Boolean direction=Boolean.valueOf(false);
          	if(leadingPath.getEnd()==this){
              direction=Boolean.valueOf(true);
            }
            this.leadingPath.confirmPossibleOccupation(c,myList.size()-1,direction);
            /*new Occupation(this.leadingPath,this.leadingPath.getOther(this).nodeValue(),direction,c);*/
          dirs.add(direction);
            return myList;
        }
    }
    
    public void addPotentialIntersection(Path pdouble,double reachTime){
        
        //add a potential horizontal occupation at appropriate end of each applicable path's occupation graph
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
    public Point getPoint(){
        return this.loc.getPoint();
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
		StdDraw.text(this.loc.getPos()[0], this.loc.getPos()[1], (Integer.valueOf((int)(nodeValue))).toString() );
        //System.out.println(nodeValue);
		/*System.out.println("hey");
		
		for(int i=0; i<this.myPaths.size();i++) {
			System.out.println("intr");
		System.out.println(this.myPaths.get(i).getDistance());
		System.out.println(this.myPaths.get(i).getEnd().nodeValue());
		System.out.println(this.myPaths.get(i).getStart().nodeValue());
		}*/
	}
	public int compareTo(Intersection i) {
		int out=1;
			//Intersection i=((Intersection)o);
		if (i.getLoc().getPos()[0]>this.getLoc().getPos()[0]) {
			out=-1;
		}
		else if (i.getLoc().getPos()[0]==this.getLoc().getPos()[0]&&i.getLoc().getPos()[1]>this.getLoc().getPos()[1]) {
			out=-1;
		}
		return out;
	}
}
