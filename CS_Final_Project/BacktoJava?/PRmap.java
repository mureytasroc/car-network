import java.util.*;
import java.awt.*;
public class PRmap{
    public static double lineWidth;
public static double markerWidth;
//new ArrayListdouble markerWidth;
public static Map m;
public static double width=600;
public static double height=640;
public static void main(String[] args) {
    StdDraw.setCanvasSize(600,640);
		StdDraw.setXscale(0,600);
        StdDraw.setYscale(0,640);
		StdDraw.enableDoubleBuffering();
  /*size(600, 640);//for 11 by 17 595x605 for 8.5x11 //for 10x16 
  background(0);*/
  lineWidth=40*(double)height/595000.0;//=8 inches in real life
  //System.out.println((8*width/lineWidth/12.0));
  //System.out.println((8*height/lineWidth/12.0));
  m= new Map(lineWidth);

  markerWidth=(double)(width*10.0/600.0);

  m.drawGrid(2, 1, 2, 2, markerWidth);

  m.Setup();

System.out.println(m.getIntersections().get(1));
  m.addCar(new Car(m.getIntersections().get(1), m.getPaths().get(1), m, 50));
  m.addCar(new Car(m.getIntersections().get(4), m.getPaths().get(1), m, 50));
  m.addCar(new Car(m.getIntersections().get(2), m.getPaths().get(1), m, 50));
  //m.addCar(new Car(m.getIntersections().get(7), m.getPaths().get(1), m, 50));

  //save("11x17(11,7).jpg");
    while(true){
        draw();
    }
}
//void setup(){
  //testOcc();
//}
public static void draw() {
  m.update(markerWidth);
    StdDraw.setPenColor(new Color(0,0,255));
    StdDraw.filledRectangle(0,0,10,10);
    StdDraw.show();
}
//(12,8) for 10x16
//wopa (14,11) for 8.5x11
//void split(11,17,11,7){
// for(int i=0;i<
//}



public static void testOcc() {
  ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
  ls.add(new LineSegment(new Point(-1, 0), new Point(3, 5)));
  ls.add(new LineSegment(new Point(3, 5), new Point(8, 7)));
  ls.add(new LineSegment(new Point(8, 7), new Point(20, 10)));
  ls.add(new LineSegment(new Point(19.5, 10), new Point(23, 10)));

  System.out.print(ls);
  System.out.println("\n\n\n");
  /*ls.add(new LineSegment(new Point(-1,10), new Point(3,5)));
   ls.add(new LineSegment(new Point(3,5), new Point(8,3)));
   ls.add(new LineSegment(new Point(8,3), new Point(20,0)));*/


  Occupation occ = new Occupation(ls);

  Occupation next = new Occupation(new Path(10), occ, 3, true, 2, new Car(0.5, 2));
  System.out.println(next.getLS());
  System.out.println(next.getEndTime());
  System.out.println(next.getPio().getLS());
}
}