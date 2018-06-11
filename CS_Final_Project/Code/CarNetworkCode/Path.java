import java.util.*;
import java.util.Collections;
public class Path extends LineSegment{
	private Intersection start;
	private Intersection end;
	private double distance;
	private double speedLim;
	private Grid myGrid;
    private Occupation occupation=new Occupation();
    private ArrayList<Occupation> possibleOccupation = new ArrayList<Occupation>();
    private Occupation confirmedOccupation;
    private double startT=-1;
    ArrayList<String> savedD=new ArrayList<String>();
    ArrayList<PIO> pioList=new ArrayList<PIO>();
	
	//ArrayList<Car> myCars; //Experimental -- should each path have a list of cars? prob no
	
	Path(Intersection s, Intersection e, double SL,double distance){
        super((Location)s.maxInt(e),(Location)s.minInt(e));
		this.distance=distance;
        if(e.compareTo(s)>0){     
		this.start=s;
		this.end=e;
        }
        else{
            this.start=e;
		this.end=s;
        }
        
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		this.myGrid.addPath(this);
		s.addPath(this);
		e.addPath(this);
        //System.out.println(this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
		
	}
	Path(Intersection s, Intersection e, double SL, double distance, boolean add){
        super((Location)s.maxInt(e),(Location)s.minInt(e));
		this.distance=distance;
		if(e.compareTo(s)>0){
		this.start=s;
		this.end=e;
        }
        else{
            this.start=e;
		this.end=s;
        }
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		if (add) {
		this.myGrid.addPath(this);
		}
		s.addPath(this);
		e.addPath(this);
        //System.out.println(this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
		
	}
	Path(Intersection s, Intersection e, double SL){
        super((Location)s.maxInt(e),(Location)s.minInt(e));
		this.distance=s.getLoc().getDistance(e.getLoc());
		if(e.compareTo(s)>0){
		this.start=s;
		this.end=e;
        }
        else{
            this.start=e;
		this.end=s;
        }
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		this.myGrid.addPath(this);
		s.addPath(this);
		e.addPath(this);
        //System.out.println(this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
		
	}
  Path(Intersection s, Intersection e, double SL, boolean add){
      super((Location)s.maxInt(e),(Location)s.minInt(e));
		this.distance=s.getLoc().getDistance(e.getLoc());
		if(e.compareTo(s)>0){
		this.start=s;
		this.end=e;
        }
        else{
            this.start=e;
		this.end=s;
        }
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		if (add) {
		this.myGrid.addPath(this);
		}
		s.addPath(this);
		e.addPath(this);
      //System.out.println(this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
		
	}
    Path(double dist){//used for testing only, never actually used in algo //don't delete
        super(new Point(1,1),new Point(1,1));
        this.distance=dist;
    }
	public void die() {
		this.getStart().removePath(this);
		this.getEnd().removePath(this);
		this.myGrid.removePath(this);
	}
	public void changeStart(Intersection i){
		this.start=i;
		this.distance=start.getLoc().getDistance(end.getLoc());
	}
	public void changeEnd(Intersection i){
		this.end=i;
		this.distance=start.getLoc().getDistance(end.getLoc());
	}

	/*public double[] getTime(double speed){ //returns {time,maxSpeed}
		double[] returnAr = new double[2];
		
		if(speed>this.speedLim) { //check for speed of slowest car on path? -- this could be factored into dijstra's algo to account for multiple cars in network
			returnAr[1]=speedLim;
		}
		else {
			returnAr[1]=speed;
		}
		returnAr[0] = this.distance/returnAr[1];
		
		return returnAr;
	}*/
	public void update() {
        if (this.end.getLoc().getPos()[0]==this.start.getLoc().getPos()[0]&&this.end.getLoc().getPos()[1]==this.start.getLoc().getPos()[1]){
            this.die();
            if (this.start!=this.end){
                this.start.tryToEat(this.end);
            }
        }
		show();
	}
    public void update(boolean b) {
        if (this.end.getLoc().getPos()[0]==this.start.getLoc().getPos()[0]&&this.end.getLoc().getPos()[1]==this.start.getLoc().getPos()[1]){
            this.die();
            if (this.start!=this.end){
                this.start.tryToEat(this.end);
            }
        }
        if (b){
		show();
        }
	}
    
    public Occupation getPO(){
        return this.confirmedOccupation;
    }
    
    public double getTime(Car c,Intersection origin,Boolean direction, double enterTime){
        this.startT=enterTime;
        //System.out.println("here"+this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
        
        double s=Math.abs(c.getSpeed());
        if (s>this.getSpeedLim()){
            s=this.getSpeedLim();
        }
		double projected=Math.abs((this.distance/s));
        
        this.possibleOccupation.add(new Occupation(this,this.occupation,enterTime,direction,Math.abs(c.getSpeed()),c));
        
        
        projected=this.possibleOccupation.get(possibleOccupation.size()-1).getEndTime()-enterTime;
        //this.getOther(origin).addPotentialOccupation(this,projected);
        
        return projected;
    }
    
    /*public void confirmPossibleOccupation(Car c, int pathNum, boolean direction){
        
        //System.out.println(startT);
        
        //c.setPathOccupation(pathNum,this.possibleOccupation);
        this.occupation.add(possibleOccupation);
        c.addToSP(pathNum,this.occupation.getLS());
        //System.out.println(this.occupation.getLS());
        //System.out.println("aiiiii");
        //add possibleOccupation to occupations, delete all redundant occupations (ones that have a point with an x value less than the possibleOccupation's end x value )
        
    }*/
    
    public ArrayList<LineSegment> confirm(boolean direction, double startTime){//add pio
        //c.setPathOccupation(pathNum,this.possibleOccupation);
        ArrayList<Occupation> dirRightPaths = new ArrayList<Occupation>();
        for(int i=0;i<possibleOccupation.size();i++){
            if(possibleOccupation.get(i).getDirection()==direction){
                dirRightPaths.add(possibleOccupation.get(i));
            }
            else{
                //System.out.println("else");
            }
        }
        double closestStartTimeDif=Double.POSITIVE_INFINITY;
        Occupation closestOcc=null;
        int COindex;
        System.out.println(dirRightPaths.size());
        for(int i=0;i<dirRightPaths.size();i++){
         if(Math.abs(dirRightPaths.get(i).getBeginTime()-startTime)<closestStartTimeDif){
             closestStartTimeDif=Math.abs(dirRightPaths.get(i).getBeginTime()-startTime);
             closestOcc=dirRightPaths.get(i);
         }
        }
        this.occupation.add(closestOcc);//WTF RETURNED THIS.OCCUPATION
        ArrayList<LineSegment> alls = new ArrayList<LineSegment>(closestOcc.getLS());
        PIO pio = closestOcc.getPio();
        pio.getIntersection().confirmPIO(pio);
        this.confirmedOccupation=this.occupation;//confirmedOcc is only for debugging
        return alls;///WTF why return lineSeg
        
    }
	public double getSlope() {
		return( (this.end.getLoc().getPos()[1]-this.start.getLoc().getPos()[1])/(this.end.getLoc().getPos()[0]-this.start.getLoc().getPos()[0]));
	}
	private void show() {
		//StdDraw.setPenColor((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200));
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(0,(int)(255*this.speedLim/5.0),(int)(255*this.speedLim/5.0));
		StdDraw.line(this.start.getLoc().getPos()[0], this.start.getLoc().getPos()[1], this.end.getLoc().getPos()[0], this.end.getLoc().getPos()[1]);
		StdDraw.setPenColor(0,255,255);
		double[] x = { this.start.getLoc().getPos()[0],  (this.start.getLoc().getPos()[0]+this.end.getLoc().getPos()[0])/2-40,this.end.getLoc().getPos()[0], (this.start.getLoc().getPos()[0]+this.end.getLoc().getPos()[0])/2 };
        double[] y = { this.start.getLoc().getPos()[1],(this.start.getLoc().getPos()[1]+this.end.getLoc().getPos()[1])/2+4, this.end.getLoc().getPos()[1], (this.start.getLoc().getPos()[1]+this.end.getLoc().getPos()[1])/2};
        //StdDraw.filledPolygon(x, y);
	}
	public Intersection getStart() {
		return this.start;
	}
	public Intersection getEnd() {
		return this.end;
	}
	public Intersection getOther(Intersection i) {
		if(i==this.start) {
			return this.end;
		}
		else {
			return this.start;
		}
	}
	public double getDistance() {
		return this.distance;
	}
    public double XD() {
		return (this.end.getLoc().getPos()[0]-this.start.getLoc().getPos()[0]);
	}
    public double YD() {
		return (this.end.getLoc().getPos()[1]-this.start.getLoc().getPos()[1]);
	}
    public double getDistance(Car c) {
        double s=Math.abs(c.getSpeed());
        if (s>this.getSpeedLim()){
            s=this.getSpeedLim();
        }
		return Math.abs((this.distance/s));
	}
    public double getTime(Car c, long a){
        return 1.0;
    }
	public double getSpeedLim() {
		return this.speedLim;
	}
    public double maxX(){
        //System.out.println(this.start.getLoc().getPos()[0]+" or "+this.end.getLoc().getPos()[0]);
        return Math.max(this.start.getLoc().getPos()[0],this.end.getLoc().getPos()[0]);
        
        
    }
    public double maxY(){
        return Math.max(this.start.getLoc().getPos()[1],this.end.getLoc().getPos()[1]);
    }
     public double minX(){
        return Math.min(this.start.getLoc().getPos()[0],this.end.getLoc().getPos()[0]);
    }
    public double minY(){
        return Math.min(this.start.getLoc().getPos()[1],this.end.getLoc().getPos()[1]);
    }
	public void intersects() {
		ArrayList<Intersection> crosses=new ArrayList<Intersection>();
		crosses.add(this.start);
		crosses.add(this.end);
		double x1=this.start.getLoc().getPos()[0];
		double y1=this.start.getLoc().getPos()[1];
		double x2=this.end.getLoc().getPos()[0];
		double y2=this.end.getLoc().getPos()[1];
		double A1=(y2-y1);
		double B1=(x1-x2);
		double C1=A1*x1+B1*y1;
		for(int inc=0; inc<myGrid.getMyPaths().size();inc++) {
			
		Path p=myGrid.getMyPaths().get(inc);
		if (p!=this) {
		
		double px1=p.getStart().getLoc().getPos()[0];
		double py1=p.getStart().getLoc().getPos()[1];
		double px2=p.getEnd().getLoc().getPos()[0];
		double py2=p.getEnd().getLoc().getPos()[1];
		
		
		
		double A2=(py2-py1);
		double B2=(px1-px2);
		double C2=A2*px1+B2*py1;
		
		double y=(A2*C1-A1*C2)/(A2*B1-A1*B2);
		
		double x=(C2*B1-B2*C1)/(A2*B1-A1*B2);
		if(x1==x2) {
			x=x1;
			y=py1+(x1-px1)*-A2/B2;
		}
		else if(px1==px2) {
			x=px1;
			y=y1+(px1-x1)*-A1/B1;
		}
		else if(y1==y2) {
			y=y1;
			x=px1+(y1-py1)*-B2/A2;
		}
		else if(py1==py2) {
			y=py1;
			x=x1+(py1-y1)*-B1/A1;
		}
		//else if (B2==0&&A2==0) {   x=px1;y=y1; System.out.println("catch"); }
		/*if(A2*B1-A1*B2==0) {System.out.println("catch");System.out.println(A2+" "+A1+" "+ B1+" "+B2);
		System.out.println("("+x1+","+y1+") to ("+x2+","+y2+")");
		System.out.println("("+px1+","+py1+") to ("+px2+","+py2+")");
		}*/
		if(    ((x1>x&&x>x2)||(x2>x&&x>x1)||(x2==x&&x==x1))  &&  ((px1>x&&x>px2)||(px2>x&&x>px1)||(px2==x&&x==px1))  &&  ((y1>y&&y>y2)||(y2>y&&y>y1)||(y2==y&&y==y1)) &&  ((py1>y&&y>py2)||(py2>y&&y>py1)||(py2==y&&y==py1))  ) {
		Location l=new Location(myGrid,x,y);
		Intersection i=new Intersection(l);
		crosses.add(i);
		/*myGrid.removePath(p);
		myGrid.removePath(this);
		Double sl=Math.min(this.speedLim,p.getSpeedLim());
		myGrid.delayAddPath(new Path(this.start,i,sl));
		myGrid.delayAddPath(new Path(p.getStart(),i,sl));
		myGrid.delayAddPath(new Path(i,this.end,sl));
		myGrid.delayAddPath(new Path(i,p.getEnd(),sl));*/
		}
		}
		}
		Collections.sort(crosses);
		Double sl=this.speedLim;
		myGrid.delayRemovePath(this);
			for (int i=0; i<crosses.size()-1;i++) {
				myGrid.delayAddPath(new Path(crosses.get(i),crosses.get(i+1),sl,false));
			}
		
	}
    
    public void saveData(String s){
        this.savedD.add(s);
    }
    
    public void printData(){
        System.out.println(savedD);
    }
    public void cleanup(){
        pioList=new ArrayList<PIO>();
        confirmedOccupation=null;
        /*for(int i=possibleOccupation.size()-1; i>=0;i--){
            possibleOccupation.remove(i);
        }*/
        possibleOccupation=new ArrayList<Occupation>();
    }
    public void confirmPIO(PIO pio){
        LineSegment ls = pio.getLS();
        Point p1 = ls.getP1();
        Point p2 = ls.getP2();
        if(pio.getIntersection()==this.getStart()){
            this.occupation.addLS(new LineSegment(new Point(p1.getX(),0),new Point(p2.getX(),0)));
        }
        else{
            this.occupation.addLS(new LineSegment(new Point(p1.getX(),this.getDistance()),new Point(p2.getX(),this.getDistance())));
        }
    }

    public Grid getGrid(){return this.myGrid;}
    
    
}
