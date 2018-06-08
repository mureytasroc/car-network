  
double lineWidth;

void setup(){
  size(800,800);
background(0);
lineWidth=(double)width*27.0/600.0;//=8 inches in real life
        Map m = new Map(lineWidth);
        
        double markerWidth=(double)width*10.0/600.0;
        
        m.drawGrid(3,2,2,2,markerWidth);
        
        m.update(markerWidth);
    
    
    
}
void draw(){
  ;
}