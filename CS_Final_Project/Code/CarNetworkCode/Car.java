import java.util.*;
import java.awt.Color;
public class Car{
	private Grid myGrid;
	private Location loc;
	private ArrayList<Path> myRoute;
	private ArrayList<Boolean> directions;
    private ArrayList<ArrayList<LineSegment>> speedProfile=new ArrayList<ArrayList<LineSegment>>();//speed profile for each 
    private Route theRoute;
	private Path curPath;
	private double speed=80.0;
	private Intersection destination;
    private Intersection start;
	private int inc=0;
	private Intersection mySpot;
	private Path eatenPath;
	private boolean destIsInt=false;
    private boolean startIsInt=false;
    private Path eatenPath2;
    private boolean killMe=false;
    private Path tempPath1;
    private Path tempPath2;
    private Path tempPath3;
    private double trailingDistance;
    public double getTD(){
        return trailingDistance;
    }
    private double startTime;//in seconds with millisecond precision
	Car(Location l){
        this.trailingDistance=0.4064;
        
		this.myRoute=new ArrayList<Path>();
		this.directions=new ArrayList<Boolean>();
		this.myGrid=l.getGrid();
		this.loc=l;
		l.snapToPath();
		this.destination=new Intersection(l);
		myGrid.setup();
		this.curPath=this.loc.snapToPath();
		this.myGrid.addCar(this);
        
	}
	Car(Location l,Location d){
        this.trailingDistance=0.4064;
        
		this.myRoute=new ArrayList<Path>();
		this.directions=new ArrayList<Boolean>();
		this.myGrid=l.getGrid();
		this.myGrid.addCar(this);
		
		/*Path cp=l.snapToPath();
		mySpot=new Intersection(l,true);
		Path a1=new Path(cp.getEnd(),mySpot,cp.getSpeedLim(),true);
		Path a2=new Path(cp.getStart(),mySpot,cp.getSpeedLim(),true);
		myGrid.removePath(cp);
		cp.getEnd().removePath(cp);
		cp.getStart().removePath(cp);*/
		this.setup(l,d);
		
	}
    Car(double td){//FOR TESTING ONLY, DO NOT DELETE
        this.trailingDistance=td;    }
    
    
    public double getSpeed(){
        return this.speed;
    }
	public void setup(Location l,Location d) {
        
		this.loc=l;
		Path pb=d.snapToPath();
        Path pb2=l.snapToPath();
        if (destination!=null)
            destination.specialize();
         if (start!=null)
            start.specialize();
		this.destination=new Intersection(d,true);
		destIsInt=false;
		for (int i=0; i<myGrid.getMyIntersections().size()-1;i++) {
			if(myGrid.getMyIntersections().get(i).tryToEat(destination)) {
				destIsInt=true;
                destination.specialize();
				destination=myGrid.getMyIntersections().get(i);
                destination.specialize();
			}
		}
        myGrid.removeDelayedInt();
        this.start=new Intersection(new Location(l),true);
        startIsInt=false;
        for (int i=0; i<myGrid.getMyIntersections().size()-1;i++) {
			if(myGrid.getMyIntersections().get(i).tryToEat(start)) {
				startIsInt=true;
                //start.specialize();
				start=myGrid.getMyIntersections().get(i);
                //start.specialize();
			}
		}
		myGrid.removeDelayedInt();
		if (!destIsInt) {
		Path b1=new Path(destination,pb.getEnd(),pb.getSpeedLim(),false);
		Path b2=new Path(pb.getStart(),destination,pb.getSpeedLim(),false);
        
		eatenPath=pb;
		pb.getEnd().removePath(pb);
		pb.getStart().removePath(pb);
		}
        if (!startIsInt) {
		Path b1=new Path(start,pb2.getEnd(),pb2.getSpeedLim(),false);
		Path b2=new Path(pb2.getStart(),start,pb2.getSpeedLim(),false);

            
		eatenPath2=pb2;
		pb2.getEnd().removePath(pb2);
		pb2.getStart().removePath(pb2);
        }
        
        //System.out.print(" middle ");
		//System.out.print(myGrid.getMyIntersections().size());
		//myGrid.setup();
		//System.out.print(" end ");
		//System.out.println(myGrid.getMyIntersections().size());
		this.curPath=this.loc.snapToPath();
        
		theRoute=this.getOptimalPath();
        
        
	}
	public void update() {
        
        //theRoute.print(startTime);
        
        
        if(theRoute.advance()){
            //System.out.println("onedown");
				if (!destIsInt) {
				Path p1=destination.getPaths().get(0);
				Path p2=destination.getPaths().get(1);
				p1.getOther(destination).addPath(eatenPath);
				p2.getOther(destination).addPath(eatenPath);
				p1.die();
				p2.die();
				myGrid.removeIntersection(destination);
				}
				setup(this.loc,new Location( myGrid,(Math.random()*800),(Math.random()*800)));
			}
		this.show();
		
	}
	public void show() {
		StdDraw.setPenColor(new Color(0,0,255));
		StdDraw.filledRectangle(this.loc.getPos()[0],this.loc.getPos()[1], 10, 10);
		
	}
    
    /*public void setPathOccupation(int num, Occupation occ){
        //this.speedProfile.set(num,occ);
    }*/
    public void addToSP(int index, ArrayList<LineSegment> ls){
        //System.out.println("\n\n\n\n");
        //System.out.println("x="+startTime);
        //ExtraMethods.printLSarray(ls);
        //System.out.println("\n\n\n\n");
        if(index>=speedProfile.size()){
            //System.out.println("add: "+index+", "+ls.size());
            this.speedProfile.add(ls);
        }
        else{
            //System.out.println("set: "+index+", "+ls.size());
        this.speedProfile.set(index,ls);
        }
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
