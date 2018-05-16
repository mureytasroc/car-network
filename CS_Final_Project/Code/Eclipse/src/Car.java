import java.util.*;

public class Car{
	private Grid myGrid;
	private Location loc;
	private ArrayList<Path> myRoute;
<<<<<<< HEAD
	private ArrayList<Boolean> directions;
	private Path curPath;
	private double speed=3.5;
	private Intersection destination;
	private int inc=0;
	private Intersection mySpot;
	private Path eatenPath;
	private boolean destIsInt=false;
	Car(Location l){
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
	public void setup(Location l,Location d) {
		this.loc=l;
		l.snapToPath();
		Path pb=d.snapToPath();
		this.destination=new Intersection(d,true);
		destIsInt=false;
		/*myGrid.update2();
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis()<start+1000) {}*/
		
		for (int i=0; i<myGrid.getMyIntersections().size()-1;i++) {
			if(myGrid.getMyIntersections().get(i).tryToEat(destination)) {
				destIsInt=true;
				destination=myGrid.getMyIntersections().get(i);
			}
		}
		myGrid.removeDelayedInt();
		
		if (!destIsInt) {
		Path b1=new Path(pb.getEnd(),destination,pb.getSpeedLim(),true);
		Path b2=new Path(pb.getStart(),destination,pb.getSpeedLim(),true);
		eatenPath=pb;
		pb.getEnd().removePath(pb);
		pb.getStart().removePath(pb);
		}
		
		myGrid.setup();
		
		
		this.curPath=this.loc.snapToPath();
		
		myRoute=this.getOptimalPath();
=======
	
	Car(Grid g, Location l){
		myRoute=new ArrayList<Path>();
		this.myGrid=g;
		this.loc=l;
>>>>>>> fc4fcc5eae45caab5d7b95a8fd0fbc5f3cb8a5ca
	}
	
	public void update() {
<<<<<<< HEAD
		
		int t=this.loc.travel(curPath,speed);
		
		if(t>0) {
			if (this.inc>=myRoute.size()) {
				if (!destIsInt) {
					
				Path p1=destination.getPaths().get(0);
				Path p2=destination.getPaths().get(1);
				p1.getOther(destination).addPath(eatenPath);
				p2.getOther(destination).addPath(eatenPath);
				p1.die();
				p2.die();
				
				myGrid.removeIntersection(destination);
				}
				for(int i=myRoute.size()-1;i>=0;i--) {
					myRoute.remove(i);
					directions.remove(i);
				}
				this.inc=0;
				setup(this.loc,new Location( myGrid,(Math.random()*800),(Math.random()*800)));
			}
			else {
				
			curPath=this.loc.snapToPath(myRoute.get(this.inc));
			
		if(this.directions.get(this.inc)) {
			
			speed=Math.abs(speed);
		}
		else {speed=-Math.abs(speed);
		}
		t=this.loc.travel(curPath,speed);
		t=this.loc.travel(curPath,speed);
		inc++;
			}
		}
		this.show();
		//CAR UPDATE METHOD
=======
>>>>>>> fc4fcc5eae45caab5d7b95a8fd0fbc5f3cb8a5ca
		
		//CAR UPDATE METHOD
		
	}
<<<<<<< HEAD
	public ArrayList<Path> getOptimalPath() {
		for (Intersection i: myGrid.getMyIntersections()) {
			i.setup();
		}
		this.destination.nodify(0);
		this.myGrid.update2();
		
=======
	
	public ArrayList<Path> getOptimalPath(Intersection start, Intersection end, ArrayList<Path> paths, ArrayList<Intersection> intersections) {
>>>>>>> fc4fcc5eae45caab5d7b95a8fd0fbc5f3cb8a5ca
		ArrayList<Path> path = new ArrayList<Path>();
		Intersection currentIntersection;
		if (this.curPath.getEnd().nodeValue()<this.curPath.getStart().nodeValue()) {
			currentIntersection=this.curPath.getEnd();
			speed=Math.abs(speed);
		}
		else {
			currentIntersection=this.curPath.getStart();
			speed=-Math.abs(speed);
		}
		double min=Double.MAX_VALUE;
		System.out.println("most");
		while (min!=0) {
			min=Double.MAX_VALUE;
			int temp=0;
			ArrayList<Path> options=currentIntersection.getPaths();
			for (int i=0; i<options.size();i++) {
				//System.out.println(options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance());
				if (options.get(i).getOther(currentIntersection).nodeValue()==0) {
					 temp=i;
					 min=0;
					 
				 }
				 else if (options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance()<min) {
					 min=options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance();
					 temp=i;
				 }
			}
			System.out.print("min: ");
			System.out.println(min);
			path.add(options.get(temp));
			if (options.get(temp).getStart()==currentIntersection) {
				this.directions.add(true);
			}
			else {
				this.directions.add(false);
			}
			currentIntersection=options.get(temp).getOther(currentIntersection);
			
			
		}
		System.out.println("all");
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
