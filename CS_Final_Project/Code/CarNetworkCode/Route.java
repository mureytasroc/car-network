import java.util.*;
public class Route{
    private Car c;
    private ArrayList<RouteModule> myRoute;
    private int dC;
    public Route(Car c,ArrayList<RouteModule> a){
        this.c=c;
        myRoute=new ArrayList<RouteModule>(a);
    }
    public Route(Car c){
        this.c=c;
        myRoute=new ArrayList<RouteModule>();
    }
    public Route(Route r){
        this.c=r.getCar();
        this.myRoute=r.getRoute();
    }
    public Car getCar(){
        return c;
    }
    
    public ArrayList<RouteModule> getRoute(){
        return myRoute;
    }
    public void addModule(RouteModule r){
        myRoute.add(r);
    }
    public boolean getDirection
    public void clear(){
        for(int i=myRoute.size(); i>=0;i--){
            myRoute.remove(i);
        }
    }
}