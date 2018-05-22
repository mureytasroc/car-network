import java.util.*;
import java.awt.Color;
public class Car{
	private Grid myGrid;
	private Location loc;
	private ArrayList<Path> myRoute;
	private ArrayList<Boolean> directions;
	private Path curPath;
	private double speed=3;
	private Intersection destination;
    private Intersection start;
	private int inc=0;
	private Intersection mySpot;
	private Path eatenPath;
	private boolean destIsInt=false;
    private boolean startIsInt=false;
    private Path eatenPath2;
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
    public double getSpeed(){
        return this.speed;
    }
	public void setup(Location l,Location d) {
        //System.out.print("start ");
        //System.out.print(myGrid.getMyIntersections().size());
		this.loc=l;
		
		Path pb=d.snapToPath();
        Path pb2=l.snapToPath();
        if (destination!=null)
            destination.specialize();
		this.destination=new Intersection(d,true);
        
		destIsInt=false;
        
		/*myGrid.update2();
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis()<start+1000) {}*/
		
		for (int i=0; i<myGrid.getMyIntersections().size()-1;i++) {
			if(myGrid.getMyIntersections().get(i).tryToEat(destination)) {
				destIsInt=true;
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
				start=myGrid.getMyIntersections().get(i);
			}
		}
		myGrid.removeDelayedInt();
		
		if (!destIsInt) {
		Path b1=new Path(destination,pb.getEnd(),pb.getSpeedLim(),true);
		Path b2=new Path(pb.getStart(),destination,pb.getSpeedLim(),true);
        
		eatenPath=pb;
		pb.getEnd().removePath(pb);
		pb.getStart().removePath(pb);
		}
        if (!startIsInt) {
		Path b1=new Path(start,pb2.getEnd(),pb2.getSpeedLim(),true);
		Path b2=new Path(pb2.getStart(),start,pb2.getSpeedLim(),true);

            
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
		
		myRoute=this.getOptimalPath();
	}
	public void update() {
        
		
		int t=this.loc.travel(curPath,speed,true);
		if(t>0) {
			if (this.inc==myRoute.size()) {
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
					
				}
                for(int i=directions.size()-1;i>=0;i--) {
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
		inc++;
			}
		}
		this.show();
		//CAR UPDATE METHOD
		
	}
	public void show() {
		StdDraw.setPenColor(new Color(0,0,255));
		StdDraw.filledRectangle(this.loc.getPos()[0],this.loc.getPos()[1], 10, 10);
		
	}
	public ArrayList<Path> getOptimalPath() {
        
		for (Intersection i: myGrid.getMyIntersections()) {
			i.setup();
		}
		//this.destination.nodify(0);
        myGrid.intersectionUpdate();
        this.destination.nodify(0,this);
		
        //myGrid.update2();
		ArrayList<Path> path = new ArrayList<Path>();
		Intersection currentIntersection=this.start;
        double projected=this.start.nodeValue();
        
		/*if (this.curPath.getEnd().nodeValue()<this.curPath.getStart().nodeValue()) {
			currentIntersection=this.curPath.getEnd();
			speed=Math.abs(speed);
		}
		else {
			currentIntersection=this.curPath.getStart();
			speed=-Math.abs(speed);
		}*/
		double min=Double.MAX_VALUE;
		//System.out.println("most");
        int timeRequest=0;
        double prevmin=-3.14;
        
		while (min>0) {
			min=Double.MAX_VALUE;
			int temp=0;
			ArrayList<Path> options=currentIntersection.getPaths();
            //System.out.println(options.size());
			for (int i=0; i<options.size();i++) {
                
				/*if (options.get(i).getOther(currentIntersection).nodeValue()==0) {
					 temp=i;
					 min=0;
					 
				 }*/
                //System.out.println("begin list: "+(options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance(this)));
				 if ((options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance(this))<min) {
                //if (options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance()<min) {
					 min=options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance(this);
                     
                    //min=options.get(i).getOther(currentIntersection).nodeValue()+options.get(i).getDistance();
					 temp=i;
				 }
			}
            //System.out.println("min");
                //System.out.println(min);
            min=min-(options.get(temp).getDistance(this));
            /*if (prevmin!=min)
                System.out.println("adjusted min");
                System.out.println(min);
            prevmin=min;*/
            //StdDraw.setPenColor(255,255,255);
            //StdDraw.filledRectangle(currentIntersection.getLoc().getPos()[0],currentIntersection.getLoc().getPos()[1],5,5);
            //StdDraw.show();
			path.add(options.get(temp));
            double startTime=(projected-currentIntersection.nodeValue());
			if (options.get(temp).getStart()==currentIntersection) {
				this.directions.add(true);
                options.get(temp).addOccupation(new Occupation(options.get(temp),startTime, true,this));
			}
			else {
				this.directions.add(false);
                options.get(temp).addOccupation(new Occupation(options.get(temp),startTime, false,this));
			}
			currentIntersection=options.get(temp).getOther(currentIntersection);
			
			
		}
        
		//System.out.println("all");
        if (!startIsInt) {
					
				Path p1=start.getPaths().get(0);
				Path p2=start.getPaths().get(1);
				p1.getOther(start).addPath(eatenPath2);
				p2.getOther(start).addPath(eatenPath2);
				p1.die();
				p2.die();
				myGrid.removeIntersection(start);
                startIsInt=true;
                this.curPath=eatenPath2;
				}
        if(this.directions.get(this.inc)) {
			
			speed=Math.abs(speed);
		}
        else{
            speed=-Math.abs(speed);
        }
        inc++;
		return path;
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
