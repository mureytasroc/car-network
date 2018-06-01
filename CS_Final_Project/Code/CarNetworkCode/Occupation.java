public class Occupation{
    private double speed;
    private boolean direction;
    private double beginTime;
    private double endTime;
    private Path myPath;
    public Occupation(Path myPath,double time, boolean direction,Car c){
        this.speed=c.getSpeed();
        this.direction=direction;
        this.beginTime=time;
        this.endTime=beginTime+(myPath.getDistance(c)/this.speed);
        this.myPath=myPath;
        this.myPath.addOccupation(this);
    }
    public void update(){
        this.endTime-=1;
        this.beginTime-=1;
        if (this.endTime<0){
            myPath.removeOccupation(this);
        }
    }
    public boolean hasCrash(double startTime,double velocity){
        
        if (startTime>endTime){
            return false;
        }
        double distance=myPath.getDistance();
        finishTime=startTime+distance/velocity;
        if(finishTime<beginTime){
            return false;
        }
        if startTime
    }
    
}