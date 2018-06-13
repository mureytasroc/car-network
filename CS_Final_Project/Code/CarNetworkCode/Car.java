import java.util.*;
import java.awt.Color;
public class Car implements Comparable<Car> {
	private Grid myGrid;
	private Location loc;
	private ArrayList<Path> myRoute;
	private ArrayList<Boolean> directions;
    private ArrayList<ArrayList<LineSegment>> speedProfile=new ArrayList<ArrayList<LineSegment>>();//speed profile for each
    private ArrayList<ArrayList<LineSegment>> usedOccupations=new ArrayList<ArrayList<LineSegment>>();
    private ArrayList<Path> usedOccupationsP=new ArrayList<Path>();
    private Route theRoute=new Route(this);
	private Path curPath;
	private double speed=50.0;
	private Destination destination;
    private Intersection start;
	private int inc=0;
	private Intersection mySpot;
	private Path eatenPath;
	private boolean destIsInt=false;
    private boolean startIsInt=false;
    private Path eatenPath2;
    private boolean killMe=false;
    /*private Path tempPath1;
    private Path tempPath2;
    private Path tempPath3;*/
    private double trailingDistance;
    private Boolean endOrientation=true;
    
    private double intersectionBufferTime;
    private double intersectionOccupationTime;
    
    
    public ArrayList<Double> getBufferData(){
        ArrayList<Double> d = new ArrayList<Double>();
        d.add(this.trailingDistance);
        d.add(intersectionBufferTime);
            d.add(intersectionOccupationTime);
            return d;
    }
    private double startTime;//in seconds with millisecond precision
	Car(Location l){
        this.trailingDistance=0.4064;
        intersectionBufferTime=trailingDistance/this.speed-0.1;
        if(intersectionBufferTime<=0){
            intersectionBufferTime=0;
        }
        intersectionOccupationTime=3;
        
		this.myRoute=new ArrayList<Path>();
		this.directions=new ArrayList<Boolean>();
		this.myGrid=l.getGrid();
		this.loc=l;
		l.snapToPath();
		this.destination=new Destination(l);
		myGrid.setup();
		this.curPath=this.loc.snapToPath();
		this.myGrid.addCar(this);
        
	}
	Car(Location l,Location d){
        this.trailingDistance=0.4064;
        intersectionBufferTime=trailingDistance/this.speed-0.1;
        if(intersectionBufferTime<=0){
            intersectionBufferTime=0;
        }
        intersectionOccupationTime=3;
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
    Car(double td,double sp){//FOR TESTING ONLY, DO NOT DELETE
        this.speed=sp;
        this.trailingDistance=td;    
    intersectionBufferTime=trailingDistance/this.speed-0.1;
        if(intersectionBufferTime<=0){
            intersectionBufferTime=0;
        }
        intersectionOccupationTime=3;}
    
    
    public double getSpeed(){
        return this.speed;
    }
	public void setup(Location l,Location d) {
        this.destination=new Destination(d);
		this.loc=l;
		Path pb=d.snapToPath();
        Path pb2=l.snapToPath();
        /*if (destination!=null)
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
        */
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
				/*if (!destIsInt) {
				Path p1=destination.getPaths().get(0);
				Path p2=destination.getPaths().get(1);
				p1.getOther(destination).addPath(eatenPath);
				p2.getOther(destination).addPath(eatenPath);
				p1.die();
				p2.die();
				myGrid.removeIntersection(destination);
				}*/
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
    public Route getOptimalPath() {
        System.out.println("CHAR");
        
        this.clearOccupations();
        this.resetCarUsedOcc();
        
        //System.out.println("\n\n\n\n");
        //System.out.println("cccdebug");
        for(int i=0;i<myGrid.getMyPaths().size();i++){
            System.out.println("HERERER");
            myGrid.getMyPaths().get(i).removeOcc();
            myGrid.getMyPaths().get(i).printOcc();
            System.out.println("ender");
            
        }
        //System.out.println("\n\n\n\n");
        
        
        
        this.startTime=this.myGrid.getTime();//set start time to be 3 seconds from query time -- we can play with this delay as we test our system
     //System.out.println(this.myGrid.getTime());
for (Intersection i: myGrid.getMyIntersections()) {
i.setup();
}       
        for (Path p: myGrid.getMyPaths()) {
p.cleanup();
}

        myGrid.intersectionUpdate();
        
        //System.out.println("!@#$%^&");
        
        
        

        ArrayList<Boolean> p = new ArrayList<Boolean>(theRoute.getDirections());
        if(! (p.size()==0)){
        endOrientation=p.get(p.size()-1);}
        else{
            //endOrientation=null;
        }
        if(endOrientation){this.start=curPath.getEnd();}
        else{this.start=curPath.getStart();}
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        this.start.nodify(startTime,this,null,startTime);
        //System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //System.out.println("error is not in nodify");
        for (Intersection i: myGrid.getMyIntersections()) {
            i.update();
        }
        StdDraw.show();
        Intersection d1=destination.getInts().get(0);
        Intersection d2=destination.getInts().get(1);
        //this.directions = new ArrayList<Boolean>();
        
        if(d1.nodeValue()==Double.POSITIVE_INFINITY){
            System.out.println("UNSOLVABLE");//CCC
        }
        //ArrayList<Path> path = destination.collectRoute(this.start,directions,this,startTime);
        //System.out.println("destination: "+(Point)d1);
        ArrayList<Double> ald=new ArrayList<Double>();
        theRoute=d1.prepareRoute(this.start,this,this.startTime,0,ald);
        
        
        /*if (!startIsInt) {
Path p1=start.getPaths().get(0);
Path p2=start.getPaths().get(1);
                this.curPath=eatenPath2;
                /*if (p1.getOther(start)==destination){
                        Path a;
                        if (start.compareTo(destination)<0){
                        a=new Path(start,destination,p1.getSpeedLim());
                        }
                        else{
                        a=new Path(destination,start, p1.getSpeedLim());
                        }
                        this.curPath=a;
                        tempPath1=a;
                    killMe=true;
                    //System.out.println("yes");
                }
            p1.die();
            p2.getOther(start).addPath(eatenPath2);
            p2.die();
            p1.getOther(start).addPath(eatenPath2);
            myGrid.removeIntersection(start);

                
        }*/
       /* if(this.directions.get(this.inc)) {
speed=Math.abs(speed);
            int t=this.loc.travel(curPath,speed,true);
}
        else{
            speed=-Math.abs(speed);
            int t=this.loc.travel(curPath,speed,true);
        }
        inc++;*/
        //this.destination.nodify(0,this,null);//bug checking
        
return theRoute;
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
    public int compareTo(Car c) {
		int out=1;
        if (this.speed>getSpeed()){
            return 1;
        }
        else if(c.getSpeed()>this.speed){
            return -1;
        }
        else{
            return 0;
        }
		
	}
    
    public void addCarUsedOcc(Occupation occ, PIO pio, Path p){
        ArrayList<LineSegment> addor = new ArrayList<LineSegment>();
        ArrayList<LineSegment> ls1 = occ.getLS();
        LineSegment ls2 = pio.getLS();
        for(int i=0;i<ls1.size();i++){
            addor.add(ls1.get(i));
        }
        addor.add(ls2);
        
        usedOccupations.add(addor);
        usedOccupationsP.add(p);
    }
        
        public void resetCarUsedOcc(){
            this.usedOccupations=new ArrayList<ArrayList<LineSegment>>();
            usedOccupationsP=new ArrayList<Path>();
        }
    
    public void clearOccupations(){
        for(int i=0;i<usedOccupations.size();i++){
            usedOccupationsP.get(i).removeLSOcc(usedOccupations.get(i));
        }
    }
    
    public void addDouble(Double d){
        System.out.println("DAUDU"+d);
        //ald.add(d);
    }
    

}
