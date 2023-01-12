private static final int X = 0;
private static final int Y = 1;
private static final int Z = 2;
private static final int W = 3;

private static double r = 150;
//private static double[][] initPoints = {{r, r, r},{-r, r, r},{r, -r, r},{-r, -r, r},{r, r, -r},{-r, r, -r},{r, -r, -r},{-r, -r, -r}};
private static double[][] initPoints = {{r,r,r,r},{-r,r,r,r},{r,-r,r,r},{-r,-r,r,r},{r,r,-r,r},{-r,r,-r,r},{r,-r,-r,r},{-r,-r,-r,r},{r,r,r,-r},{-r,r,r,-r},{r,-r,r,-r},{-r,-r,r,-r},{r,r,-r,-r},{-r,r,-r,-r},{r,-r,-r,-r},{-r,-r,-r,-r}};
private static ArrayList<Point> points;
private static Point[] v;
//private static int[] pairs = {0,1,2,3,0,2,1,3,4,5,6,7,4,6,5,7,0,4,1,5,2,6,3,7/*,8,9,10,11,8,10,9,11,12,13,14,15,12,14,13,15,8,12,9,13,10,14,11,15,0,8,1,9,2,10,3,11,4,12,5,13,6,14,7,15*/};

void setup() {
  size(900, 900);
  points = new ArrayList<Point>();
  v = new Point[16];
  for (int i = 0; i < initPoints.length; i++) {
    Point another = new Point(initPoints[i]);
    points.add(another);
    v[i] = another;
  }
  int pointNum = 10;
  addEdges(hypercube(3, v), pointNum, points);
  //addPlane(v[0], v[1], v[2], v[3], pointNum, points);
  //addPlane(v[4], v[5], v[6], v[7], pointNum, points);
  //addPlane(v[0], v[1], v[4], v[5], pointNum, points);
  //addPlane(v[2], v[3], v[6], v[7], pointNum, points);
  //addPlane(v[0], v[2], v[4], v[6], pointNum, points);
  //addPlane(v[1], v[3], v[5], v[7], pointNum, points);
  for (int i = 0; i < points.size(); i++) {
    points.set(i, points.get(i).setRadius(300));
  }
}

void draw() {
  background(255);
  translate(width/2, height/2);
  fill(0);
  for (Point point : points) {
    point.rotate(Math.PI/1024, X, Z);
    //point.rotate(Math.PI/1024, Z, Y);
    point.rotate(Math.PI/1024, X, Y);
    //point.rotate(Math.PI/512, Z, W);
    //point.rotate(Math.PI/1024, W, X);
    //point.rotate(Math.PI/2048, Y, W);
    drawCirc(point.isometric(), 5);
  }
}

/*public static void main(String[] args) {
  System.out.println(v);
}*/

public void drawCirc(Point point, int radius) { // for 2D points
  ellipse((float) point.get(X), (float) point.get(Y), radius, radius);
}

public static void addLine(Point a, Point b, int pointNum, ArrayList<Point> fullList) {
  int maxDimension = Math.max(a.dimensions(), b.dimensions());
  double[] deltaCoords = new double[maxDimension];
  double[] currCoords = new double[maxDimension];
  for (int i = 0; i < maxDimension; i++) {
    deltaCoords[i] = b.get(i) - a.get(i);
    currCoords[i] = a.get(i);
  }
  for (int c = 0; c < pointNum; c++) {
    for (int i = 0; i < currCoords.length; i++) {
      currCoords[i] += deltaCoords[i]/(pointNum+1);
    }
    Point another = new Point(currCoords.clone());
    fullList.add(another);
  }
}

public static void addPlane(Point a, Point b, Point c, Point d, int pointNum, ArrayList<Point> fullList) {
  ArrayList<Point> firstLine = new ArrayList<Point>();
  addLine(a, b, pointNum, firstLine);
  ArrayList<Point> secondLine = new ArrayList<Point>();
  addLine(c, d, pointNum, secondLine);
  for (int i = 0; i < pointNum; i++) {
    addLine(firstLine.get(i), secondLine.get(i), pointNum, fullList);
  }
}

public static void addEdges(ArrayList<Edge> edges, int pointNum, ArrayList<Point> fullList) {
  for (Edge edge : edges) {
    addLine(edge.getPoint(1), edge.getPoint(2), pointNum, fullList);
  }
}

public static ArrayList<Edge> hypercube(int dimension, Point[] v) {
  ArrayList<Edge> edges;
  if (dimension <= 1) {
     edges = new ArrayList<Edge>();
     Edge first = new Edge(v, 0, 1);
     edges.add(first);
  } else {
    edges = hypercube(dimension-1, v);
    int size = edges.size();
    int powerOf2 = (int) Math.pow(2, dimension-1);
    for (int i = 0; i < size; i++) {
      Edge duplication = new Edge(v, edges.get(i).end(1)+powerOf2, edges.get(i).end(2)+powerOf2);
      edges.add(duplication);
    }
    for (int i = 0; i < powerOf2; i++) {
      Edge connection = new Edge(v, i, i+powerOf2);
      edges.add(connection);
    }
  }
  return edges;
}

public static class Point {

  private double[] coords;

  public Point(double[] coords) {
    this.coords = coords;
  }

  public double get(int i) {
    if (i >= coords.length) {
      return 0;
    } else {
      return coords[i];
    }
  }
  
  public void set(int i, double val) {
    if (i >= coords.length) {
      double[] newCoords = new double[i+1];
      for (int j = 0; j < coords.length; j++) {
        newCoords[j] = coords[j];
      }
      newCoords[i] = val;
      coords = newCoords;
    } else {
      coords[i] = val;
    }
  }

  public int dimensions() {
    return coords.length;
  }
  
  public double getRadius() {
    double sumSquares = 0;
    for (double coord : coords) {
      sumSquares += Math.pow(coord, 2);
    }
    return Math.sqrt(sumSquares);
  }
  
  public Point setRadius(double newRadius) {
    double[] newCoords = new double[coords.length];
    double radius = getRadius();
    for (int i = 0; i < newCoords.length; i++) {
      newCoords[i] = (coords[i] * newRadius) / radius;
    }
    Point newPoint = new Point(newCoords);
    return newPoint;
  }
  
  /*public Point getShadow(Point observer, double distance) {
    Point shadow = GT.new Point();
    return shadow;
  }*/
  
  public Point isometric() { // Only for 3D points
    double[] xy = new double[2];
    xy[X] = coords[Y] * Math.cos(Math.PI/6) - coords[X] * Math.cos(Math.PI/6);
    xy[Y] = coords[Z] - coords[Y] * Math.sin(Math.PI/6) - coords[X] * Math.sin(Math.PI/6);
    Point result = new Point(xy);
    return result;
  }
  
  public void rotate(double radians, int planeAxis1, int planeAxis2) { // for 3D points (as of now)
    int rX = planeAxis1, rY = planeAxis2;
    double distance = Math.sqrt(Math.pow(get(rX), 2) + Math.pow(get(rY), 2));
    double angle;
    if (get(rX) >= 0 && get(rY) >= 0) {                              // First Quadrant
      angle = Math.atan(get(rY)/get(rX));
      //angle = Math.atan2(get(rX), get(rY));
    } else if (get(rX) < 0 && get(rY) >= 0){                         // Second Quadrant
      angle = Math.atan(-(get(rX)/get(rY))) + Math.PI/2;
    } else if (get(rX) < 0 && get(rY) < 0) {                         // Third Quadrant
      angle = Math.atan(get(rY)/get(rX)) + Math.PI;
    } else {                                                         // Fourth Quadrant
      angle = Math.atan(-(get(rX)/get(rY))) + 3*Math.PI/2;
    }
    angle += radians;
    set(rX, distance * Math.cos(angle));
    set(rY, distance * Math.sin(angle));
  }
  
  public String toString() {
    String to = "(";
    for (int i = 0; i < coords.length; i++) {
      to += coords[i] + ",";
    }
    return to.substring(0, to.length()-1) + ")";
  }
  
}

public static class Edge {
  
  private int end1i, end2i;
  
  private Point[] v;
  
  public Edge (Point[] v, int end1i, int end2i) {
    this.v = v;
    this.end1i = end1i;
    this.end2i = end2i;
  }
  
  public int end(int oneOr2) {
    if (oneOr2 == 1) {
      return end1i;
    } else {
      return end2i;
    }
  }
  
  public Point getPoint(int oneOr2) {
    if (oneOr2 == 1) {
      return v[end1i];
    } else {
      return v[end2i];
    }
  }
  
  public String toString() {
    return end1i + " to " + end2i;
  }
  
}

/*
public Point() {
  coords = new double[0];
}
public class Point3D extends Point {
  public Point3D() { super(); }
  public Point3D(double[] coords) {
    super(coords);
    if (coords.length != 3) {
      System.out.println("Point3D might have incorrect coordinates");
    }
  }
  public Point getShadow(Point observer, double distance) {
    Point2D shadow = GT.new Point2D();
    return shadow;
  } 
}
public class Point2D extends Point {
  public Point2D() { super(); }
  public Point2D(double[] coords) {
    super(coords);
    if (coords.length != 2) {
      System.out.println("Point2D might have incorrect coordinates");
    }
  }
  public Point getShadow(Point observer, double distance) {
    Point1D shadow = GT.new Point1D();
    return shadow;
  } 
}
public class Point1D extends Point {
  public Point1D() { super(); }
  public Point1D(double[] coords) {
    super(coords);
    if (coords.length != 1) {
      System.out.println("Point1D might have incorrect coordinates");
    }
  }
  public Point getShadow(Point observer, double distance) {
    Point1D shadow = GT.new Point1D();
    return shadow;
  } 
}
public double getPolar() {
  double radius = getRadius();
  double polar;
  if (coords[Z] >= 0) {
    polar = Math.acos(coords[Z]/radius); 
  } else {
    polar = Math.acos(coords[Z]/radius) + Math.PI/2;
  }
  return polar;
}
public double getAzimuth() {
  double radius = getRadius();
  double polar = getPolar();
  double azimuth;
  if (coords[X] >= 0 && coords[Y] >= 0) {
    azimuth = Math.acos(coords[X]/(radius*Math.sin(polar))); 
  } else if (coords[X] < 0 && coords[Y] >= 0){
    azimuth = Math.asin(coords[X]/(radius*Math.sin(polar))) + Math.PI/2;
  } else if (coords[X] < 0 && coords[Y] < 0) {
    azimuth = Math.acos(coords[X]/(radius*Math.sin(polar))) + Math.PI;
  } else {
    azimuth = Math.asin(coords[X]/(radius*Math.sin(polar))) + 3*Math.PI/2;
  }
  return azimuth;
}
*/
