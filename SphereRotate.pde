public float arctan = 0.3427394; //30053766989313195369868965863502083817488410633295;
public int radius = 300;
//public int wait = 0;
public HashMap<String, Point> points;
public String[] hexNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
public String[] octNames = {"i", "j", "k", "l", "m", "n"};
public String[] dodNames = {"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

/*public enum thePoints {
  
}*/

void setup () {
  size (900, 900);
  //fill (0);
  //textSize (30);
  points = new HashMap<String, Point>();
  /* OCTAHEDRON (ab ad ae af bc be bf cd ce cf de df) DIAG (ac bd ef) */
  points.put("i", new Point(0, PI/2, 2*radius/sqrt(3)));
  points.put("j", new Point(PI/2, PI/2, 2*radius/sqrt(3)));
  points.put("k", new Point(PI, PI/2, 2*radius/sqrt(3)));
  points.put("l", new Point(3*PI/2, PI/2, 2*radius/sqrt(3)));
  points.put("m", new Point(0, 0, 2*radius/sqrt(3)));
  points.put("n", new Point(0, PI, 2*radius/sqrt(3)));
  /* HEXAHEDRON (ab ac ag bd bh cd ce df ef eg fh gh) TETRAHEDRA (ad ae ah dh ed eh) (bc bf bg cf cg fg) DIAG (af be ch dg) */
  points.put("a", new Point(PI/4, acos(1/sqrt(3)), radius));
  points.put("b", new Point(PI/4, PI/2 + asin(1/sqrt(3)), radius));
  points.put("c", new Point(3*PI/4, acos(1/sqrt(3)), radius));
  points.put("d", new Point(3*PI/4, PI/2 + asin(1/sqrt(3)), radius));
  points.put("e", new Point(5*PI/4, acos(1/sqrt(3)), radius));
  points.put("f", new Point(5*PI/4, PI/2 + asin(1/sqrt(3)), radius));
  points.put("g", new Point(7*PI/4, acos(1/sqrt(3)), radius));
  points.put("h", new Point(7*PI/4, PI/2 + asin(1/sqrt(3)), radius));
  /* DODECAHEDRON */
  points.put("o", new Point(0, arctan, radius));
  points.put("p", new Point(PI, arctan, radius));
  points.put("q", new Point(0, PI - arctan, radius));
  points.put("r", new Point(PI, PI - arctan, radius));
  points.put("s", new Point(PI/2, PI/2 + arctan, radius));
  points.put("t", new Point(PI/2, PI/2 - arctan, radius));
  points.put("u", new Point(3*PI/2, PI/2 + arctan, radius));
  points.put("v", new Point(3*PI/2, PI/2 - arctan, radius));
  points.put("w", new Point(arctan, PI/2,  radius));
  points.put("x", new Point(2*PI - arctan, PI/2, radius));
  points.put("y", new Point(PI + arctan, PI/2, radius));
  points.put("z", new Point(PI - arctan, PI/2, radius));
  /*int r = 300;
  float[][] someCoords = {{r,r,r},{-r,r,r},{-r,-r,r},{-r,-r,-r},{r,r,-r},{r,-r,r},{r,-r,-r},{-r,r,-r}};
  for (int i = 0; i < someCoords.length; i++) {
    points.add(new Point(someCoords[i]));
  }
  for (int i = 0; i < points.size(); i++) {
    float[] iso = points.get(i).getIso();
    ellipse (iso[X], iso[Y], 10, 10);
  }*/
  
}

void draw () {
  background(255);
  translate (width/2, height/2);
  
  //isoShape ("ijiliminjkjmjnklkmknlmln");
  //isoShape ("abacagbdbhcdcedfefegfhgh");
  
  isoShape ("ogoaoppcpeqbqhrqrdrfvgvevuufuhzczdzyyefytatctssbsdwawbwxgxhx");
  //isoShape ("ayauarbpbybvcxcucqdodvdxeweseqfoftfwgzgrgshphzhttvusproqyxzw");          
  
  /*isoEdge ("ah");
  isoEdge ("ad");
  isoEdge ("ae");
  isoEdge ("eh");
  isoEdge ("ed");
  isoEdge ("dh");
  
  isoEdge ("gb");
  isoEdge ("cb");
  isoEdge ("gc");
  isoEdge ("gf");
  isoEdge ("fc");
  isoEdge ("fb");
  
  isoEdge ("gd");
  isoEdge ("ch");
  isoEdge ("eb");
  isoEdge ("fa");*/
  
  for (String name : hexNames) {
    points.get(name).rotatePoint(PI/512, 0);
  }
  
  for (String name : dodNames) {
    points.get(name).rotatePoint(PI/512, 0);
  }
  
  for (String name : octNames) {
    points.get(name).rotatePoint(PI/512, 0);
  }
   
  //text (points.get("o").getAZI(), 0, 0);
  
}

public void isoShape(String edges) {
  while (edges.length() > 1) {
    isoEdge (edges.substring(0, 2));
    edges = edges.substring(2);
  }
}

public void myShape(String edges) {
  while (edges.length() > 1) {
    edge (edges.substring(0, 2));
    edges = edges.substring(2);
  }
}

public void edge(String twoName) {
  String aName = twoName.substring(0,1);
  String bName = twoName.substring(1);
  line (points.get(aName).getX(), points.get(aName).getY(), points.get(bName).getX(), points.get(bName).getY());
}

public void isoEdge(String twoName) {
  String aName = twoName.substring(0,1);
  String bName = twoName.substring(1);
  line (points.get(aName).getIso()[X], points.get(aName).getIso()[Y], points.get(bName).getIso()[X], points.get(bName).getIso()[Y]);
}

/*public static void addLine(Point a, Point b, int pointNum, ArrayList<Point> fullList) {
  int maxDimension = Math.max(a.dimensions(), b.dimensions());
  float[] deltaCoords = new float[maxDimension];
  float[] currCoords = new float[maxDimension];
  for (int i = 0; i < maxDimension; i++) {
    deltaCoords[i] = b.getC(i) - a.getC(i);
    currCoords[i] = a.getC(i);
  }
  for (int c = 0; c < pointNum; c++) {
    for (int i = 0; i < currCoords.length; i++) {
      currCoords[i] += deltaCoords[i]/(pointNum+1);
    }
    //Point another = new Point(currCoords);
    //fullList.add(another);
  }
}*/

public enum Co {
    X, Y, Z, // Cartesian Coordinates
    AZI,  //Azimuth Angle, angle from x-axis to point's (x,y) coordinate
    POL,  //Polar Angle, angle down from z-axis to point
    RAD  //Radius, Distance of point from origin
}

public final int X = Co.X.ordinal();
public final int Y = Co.Y.ordinal();
public final int Z = Co.Z.ordinal();
public final int AZI = Co.AZI.ordinal();
public final int POL = Co.POL.ordinal();
public final int RAD = Co.RAD.ordinal();

public class Point {
  
  private float[] coords;
  
  /*public Point(float[] coords) {
    this.coords = coords;
  }

  public float getC(int i) {
    if (i >= coords.length) {
      return 0;
    } else {
      return coords[i];
    }
  }

  public int dimensions() {
    return coords.length;
  }*/
  
  public Point (float azi, float pol, float rad) {
    makePoint(azi, pol, rad);
  }
  
  private void makePoint(float azi, float pol, float rad) {
    coords = new float[6];
    coords[AZI] = azi;
    coords[POL] = pol;
    coords[RAD] = rad;
    coords[X] = rad * sin(pol) * cos(azi);
    coords[Y] = rad * sin(pol) * sin(azi);
    coords[Z] = rad * cos(pol);
  }
  
  public float getX() {
    return coords[X];
  }
  
  public float getY() {
    return coords[Y];
  }
  
  public float getAZI() {
    return coords[AZI];
  }
  
  public float[] getIso() {
    float[] result = new float[2];
    // result[0] = coords[Y]*cos(PI/6) - coords[X]*cos(PI/6);
    result[0] = (coords[Y] - coords[X]) * (sqrt(3)/2);
    // result[1] = coords[Z] - coords[Y]*sin(PI/6) - coords[X]*sin(PI/6);
    result[1] = coords[Z] - (coords[Y] + coords[X])/2;
    return result;
  }
  
  public void rotatePoint(float aziRotate, float polRotate) {
    coords[AZI] += aziRotate;
    if (coords[AZI] >= 2*PI) {
      coords[AZI] -= 2*PI;
    }
    if (coords[AZI] <= -2*PI) {
      coords[AZI] += 2*PI;
    }
    coords[POL] += polRotate;
    if (coords[POL] >= 2*PI) {
      coords[POL] -= 2*PI;
    }
    coords[X] = coords[RAD] * sin(coords[POL]) * cos(coords[AZI]);
    coords[Y] = coords[RAD] * sin(coords[POL]) * sin(coords[AZI]);
    coords[Z] = coords[RAD] * cos(coords[POL]);
  }
  
  /*public void setRadius(float newRadius) {
    float sumSquares = 0;
    for (float coord : coords) {
      sumSquares += pow(coord, 2);
    }
    float radius = sqrt(sumSquares);
    float[] newCoords = new float[coords.length];
    for (int i = 0; i < newCoords.length; i++) {
      newCoords[i] = (coords[i] * newRadius) / radius;
    }
    coords = newCoords;
  }*/
  
}

/*
  line (points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY());
  line (points[0].getX(), points[0].getY(), points[3].getX(), points[3].getY());
  line (points[0].getX(), points[0].getY(), points[4].getX(), points[4].getY());
  line (points[0].getX(), points[0].getY(), points[5].getX(), points[5].getY());
  
  line (points[1].getX(), points[1].getY(), points[4].getX(), points[4].getY());
  line (points[1].getX(), points[1].getY(), points[5].getX(), points[5].getY());
  
  line (points[2].getX(), points[2].getY(), points[1].getX(), points[1].getY());
  line (points[2].getX(), points[2].getY(), points[3].getX(), points[3].getY());
  line (points[2].getX(), points[2].getY(), points[4].getX(), points[4].getY());
  line (points[2].getX(), points[2].getY(), points[5].getX(), points[5].getY());
  
  line (points[3].getX(), points[3].getY(), points[4].getX(), points[4].getY());
  line (points[3].getX(), points[3].getY(), points[5].getX(), points[5].getY());
  
  public class Orthant {
  
  // is this class useful

  private int ortht;
  
  private boolean[] signs;

  public Orthant(int ortht) {
    String bitStr = Integer.toBinaryString(ortht);
    signs = new boolean[bitStr.length()];
    int i = signs.length - 1;
    for (int j = 0; j < signs.length; j++) {
      if (bitStr.charAt(j) == '0') {
        signs[i] = true;
      } else { // ..rAt(j) == '1'
        signs[i] = false;
      }
      i--;
    }
  }

  public Orthant(Orthant a) {
    ortht = a.ortht;
    signs = a.signs;
  }

  public boolean hasPos(int i) {
    if (i < signs.length) {
      return signs[i];
    } else {
      return true;
    }
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < signs.length || i < 3; i++) { // 3 is default
      if (hasPos(i)) {
        sb.append(", +");
      } else {
        sb.append(", -");
      }
    }
    return "Orthant " + ortht + ":\n(" + sb.substring(2) + ")\n x  y  z";
  }

}
  
*/
