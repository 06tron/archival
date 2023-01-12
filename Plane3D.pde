int[] dems = {200, 300, 300}; // {x(red), y(blue), z(green)}
float xHorz = horz(dems[0]);
float xVert = vert(dems[0]);
float yVert = dems[1];
float zHorz = horz(dems[2]);
float zVert = vert(dems[2]);
float rWidth = xHorz + zHorz;
float rHeight = yVert + Math.max(xVert, zVert);
float[][] points = {{100,0,100}, {0,100,100}, {100,100,0}, {100,100,100}};

public float horz(float dem) {
  return dem * (float) Math.cos(Math.PI/6);
}

public float vert(float dem) {
  return dem * (float) Math.cos(Math.PI/3);
}

void setup () {
  size (500,500);
  noSmooth();
  background (0);
  fill (255);
  noStroke();
  rect(0, 0, rWidth, rHeight);
  stroke (255,0,0);
  line (zHorz, yVert, rWidth, yVert+xVert); // x-axis
  stroke (0,0,255);
  line (zHorz, yVert, zHorz, 0);
  stroke (0,255,0);
  line (zHorz, yVert, 0, yVert+zVert);
  stroke(0);
  for (float[] point : points) {
    float[] xyPoint = convert(point);
    point (xyPoint[0], xyPoint[1]);
  }
}

public float[] convert(float[] point) {
  float[] xyPoint= new float[2];
  xyPoint[0] = zHorz + (horz(point[0]) - horz(point[2]));
  xyPoint[1] = yVert - (point[1] - Math.max(vert(point[0]), vert(point[2])));
  return xyPoint;
}
