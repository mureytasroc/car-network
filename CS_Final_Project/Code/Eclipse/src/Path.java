import java.util.*;

public class Path {
	private Intersection start;
	private Intersection end;
	private double distance;
	private double speedLim;
	private Grid myGrid;
	
	ArrayList<Car> myCars; //Experimental -- should each path have a list of cars?
	
	Path(Intersection s, Intersection e, double SL,double distance){
		this.distance=distance;
		this.start=s;
		this.end=e;
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		this.myGrid.addPath(this);
		s.addPath(this);
		e.addPath(this);
		
	}
	Path(Intersection s, Intersection e, double SL){
		this.distance=s.getLoc().getDistance(e.getLoc());
		this.start=s;
		this.end=e;
		this.speedLim=SL;
		this.myGrid=s.getGrid();
		this.myGrid.addPath(this);
		s.addPath(this);
		e.addPath(this);
		
	}
	public void changeStart(Intersection i){
		this.start=i;
	}
	public void changeEnd(Intersection i){
		this.end=i;
	}
	public double[] getTime(double speed){ //returns {time,maxSpeed}
		double[] returnAr = new double[2];
		
		if(speed>this.speedLim) { //check for speed of slowest car on path? -- this could be factored into dijstra's algo to account for multiple cars in network
			returnAr[1]=speedLim;
		}
		else {
			returnAr[1]=speed;
		}
		returnAr[0] = this.distance/returnAr[1];
		
		return returnAr;
	}
	public void update() {
		show();
	}
	public double getSlope() {
		return( (this.end.getLoc().getPos()[1]-this.start.getLoc().getPos()[1])/(this.end.getLoc().getPos()[0]-this.start.getLoc().getPos()[0]));
	}
	private void show() {
		StdDraw.setPenColor(0,255,255);
		StdDraw.setPenRadius(0.05);
		StdDraw.line(this.start.getLoc().getPos()[0], this.start.getLoc().getPos()[1], this.end.getLoc().getPos()[0], this.end.getLoc().getPos()[1]);
	}
	public Intersection getStart() {
		return this.start;
	}
	public Intersection getEnd() {
		return this.end;
	}
	public double getDistance() {
		return this.distance;
	}
	public double getSpeedLim() {
		return this.speedLim;
	}
	public void intersects(Path p) {
		double x1=this.start.getLoc().getPos()[0];
		double y1=this.start.getLoc().getPos()[1];
		double x2=this.end.getLoc().getPos()[0];
		double y2=this.end.getLoc().getPos()[1];
		double px1=p.getStart().getLoc().getPos()[0];
		double py1=p.getStart().getLoc().getPos()[1];
		double px2=p.getEnd().getLoc().getPos()[0];
		double py2=p.getEnd().getLoc().getPos()[1];
		
		double A1=(y2-y1);
		double B1=(x1-x2);
		double C1=A1*x1+B1*y1;
		
		double A2=(py2-py1);
		double B2=(px1-px2);
		double C2=A2*px1+B2*py1;
		
		double y=(A2*C1-A1*C2)/(A2*B1-A1*B2);
		
		double x=(C2*B1-B2*C1)/(A2*B1-A1*B2);
		if(    ((x1>x&&x>x2)||(x2>x&&x>x1)||(x2==x&&x==x1))  &&  ((px1>x&&x>px2)||(px2>x&&x>px1)||(px2==x&&x==px1))  &&  ((y1>y&&y>y2)||(y2>y&&y>y1)||(y2==y&&y==y1)) &&  ((py1>y&&y>py2)||(py2>y&&y>py1)||(py2==y&&y==py1))  ) {
		Location l=new Location(myGrid,x,y);
		Intersection i=new Intersection(l);
		myGrid.removePath(p);
		myGrid.removePath(this);
		Double sl=Math.min(this.speedLim,p.getSpeedLim());
		myGrid.delayAddPath(new Path(this.start,i,sl));
		myGrid.delayAddPath(new Path(p.getStart(),i,sl));
		myGrid.delayAddPath(new Path(i,this.end,sl));
		myGrid.delayAddPath(new Path(i,p.getEnd(),sl));
		
		}
	}
	
	
}
