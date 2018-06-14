  
double lineWidth;
double markerWidth;
//new ArrayListdouble markerWidth;
Map m;
void setup(){
  size(600,640);//for 11 by 17 595x605 for 8.5x11 //for 10x16 
background(0);
lineWidth=40*(double)height/595.0;//=8 inches in real life
System.out.println((8*width/lineWidth/12.0));
System.out.println((8*height/lineWidth/12.0));
        m= new Map(lineWidth);
        
       markerWidth=(double)(width*10.0/600.0);
        
        m.drawGrid(2,1,2,2,markerWidth);
        
        m.addCar(new Car(new Point(50,50)));
    
    save("11x17(11,7).jpg");
    System.out.println("dun");
    
    
}
void draw(){
  m.update(markerWidth);
}
//(12,8) for 10x16
//wopa (14,11) for 8.5x11
//void split(11,17,11,7){
 // for(int i=0;i<
//}