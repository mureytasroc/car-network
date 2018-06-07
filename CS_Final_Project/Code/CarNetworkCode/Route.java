import java.util.*;
public class Route{
    private Car c;
    private ArrayList<RouteModule> myRoute;
    private int inc=0;
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
    public boolean advance(){
        if(myRoute.get(inc).advance()){
            inc++;
        }
        if (inc==myRoute.size()){
            inc=0;
            this.clear();
            return true;
        }
        return false;
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
    public void clear(){
        for(int i=myRoute.size()-1; i>=0;i--){
            myRoute.remove(i);
        }
    }
}