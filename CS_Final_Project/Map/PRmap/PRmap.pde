
double lineWidth;
double markerWidth;
//new ArrayListdouble markerWidth;
Map m;
void setup() {
   
    
    
    
  size(600, 600);//for 11 by 17 595x605 for 8.5x11 //for 10x16 
  background(0);
  lineWidth=40*(double)height/600.0;//=8 inches in real life
  System.out.println((8*width/lineWidth/12.0));
  System.out.println((8*height/lineWidth/12.0));
  m= new Map(lineWidth);

  markerWidth=(double)(width*10.0/600.0);

  m.drawGrid(2, 1, 0, 0, markerWidth*2);

  m.Setup();

//println(m.getIntersections().get(1));

  m.addCar(new Car(m.getIntersections().get(1), m.getIntersections().get(2), m, 50));
  m.addCar(new Car(m.getIntersections().get(4), m.getIntersections().get(3), m, 50));
  //m.addCar(new Car(m.getIntersections().get(7), m.getIntersections().get(8), m, 50));

  //save("11x17(11,7).jpg");
}
//void setup(){
  //testOcc();
//}
void draw() {
  try{
  m.update(markerWidth*2.5);}
  catch(Exception e){;}
  //save("CARSnew.jpg");
  //exit();
  
}
//(12,8) for 10x16
//wopa (14,11) for 8.5x11
//void split(11,17,11,7){
// for(int i=0;i<
//}



void testOcc() {
  ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
  ls.add(new LineSegment(new Point(-1, 0), new Point(3, 5)));
  ls.add(new LineSegment(new Point(3, 5), new Point(8, 7)));
  ls.add(new LineSegment(new Point(8, 7), new Point(20, 10)));
  ls.add(new LineSegment(new Point(19.5, 10), new Point(23, 10)));

  print(ls);
  System.out.println("\n\n\n");
  /*ls.add(new LineSegment(new Point(-1,10), new Point(3,5)));
   ls.add(new LineSegment(new Point(3,5), new Point(8,3)));
   ls.add(new LineSegment(new Point(8,3), new Point(20,0)));*/


  Occupation occ = new Occupation(ls);

  Occupation next = new Occupation(new Path(10), occ, 3, true, 2, new Car(0.5, 2));
  println(next.getLS());
  System.out.println(next.getEndTime());
  System.out.println(next.getPio().getLS());
}