
public class CarNetwork {
	
	private static Grid myGrid;
	public static void main(String args[]) {
		myGrid = new Grid();
		
		Path s=new Path(new Intersection(new Location(myGrid,100,50)),new Intersection(new Location(myGrid,100,750)),20);
		Path t=new Path(new Intersection(new Location(myGrid,700,50)),new Intersection(new Location(myGrid,700,750)),20);
		Path q=new Path(new Intersection(new Location(myGrid,40,700)),new Intersection(new Location(myGrid,700,100)),20);
		Path u=new Path(new Intersection(new Location(myGrid,40,40)),new Intersection(new Location(myGrid,760,760)),20);
		Path p=new Path(new Intersection(new Location(myGrid,250,700)),new Intersection(new Location(myGrid,750,700)),20);
		Path r=new Path(new Intersection(new Location(myGrid,50,100)),new Intersection(new Location(myGrid,750,100)),20);
		myGrid.setup();
		Location l=new Location(myGrid,500,600);
		Location d=new Location(myGrid,0,800);
		
		//Car c=new Car(l,d);
		
		while(true){
		myGrid.update();
		}
	}
	
}
