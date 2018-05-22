public class Occupation{
    private double speed;
    private boolean direction;
    private int beginTime;
    private int endTime;
    private Path myPath;
    public Occupation(Path myPath,double time, boolean direction,Car c){
        this.speed=c.getSpeed();
        this.direction=direction;
        this.beginTime=(int)time;
        this.endTime=beginTime+(int)(myPath.getDistance(c)/this.speed)+1;
        this.myPath=myPath;
    }
    public void update(){
        this.endTime-=1;
        this.beginTime-=1;
        if (this.endTime<0){
            myPath.removeOccupation(this);
        }
    }
    
}