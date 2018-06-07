  
  double w;
double h;

void setup(){
  size(600,600);
background(255);
w=width;
h=-height;
        Map m = new Map((int)w,(int)h);
        m.addPath(new Path(m,new Point(40,40),new Point(100,100),20));
        m.update();
    
    
    
}
void draw(){
  ;
}