double pixelLength;
int rows;
int columns;
Pixel[][] pixelGrid;
ArrayList<Pixel> usedPixels;
double margin;
int movementType;
double xCoord;
double yCoord;
double speed;

void setup() {
  size(1200, 800);
  fill(0);
  pixelLength = height/50;
  rows = (int) (height/pixelLength);
  columns = (int) (width/pixelLength);
  pixelGrid = new Pixel[rows][columns];
  for (int r = 0; r < rows; r++) {
    for (int c = 0; c < columns; c++) {
      pixelGrid[r][c] = new Pixel(r, c);
    }
  }
  usedPixels = new ArrayList<Pixel>();
  margin = pixelLength*1.5;
  movementType = 1;
  xCoord = margin;
  yCoord = height-margin;
  speed = pixelLength/10;
}

void draw() {
  background(255);
  usedPixels.clear();
  if (movementType == 1 && xCoord >= width-margin) {
    movementType = 2;
  } else if (movementType == 2 && yCoord <= margin) {
    movementType = 3;
  } else if (movementType == 3 && yCoord >= height-margin) {
    movementType = 4;
  } else if (movementType == 4 && xCoord <= margin) {
    movementType = 1;
  }
  addLine(margin, margin, xCoord, yCoord);
  switch (movementType) {
    case 1:
      xCoord += speed;
      break;
    case 2:
      yCoord -= speed;
      break;
    case 3:
      yCoord += speed;
      break;
    case 4:
      xCoord -= speed;
      break;
  }
  noStroke();
  drawPixels();
  stroke(255,0,0);
  line((float) margin, (float) margin, (float) xCoord, (float) yCoord);
}

void addLine(double startX, double startY, double endX, double endY) {
  double changeInY = endY-startY;
  double changeInX = endX-startX;
  double slope = changeInY/changeInX;
  if (Math.abs(changeInY) > Math.abs(changeInX)) {
    if (startY > endY) {
      double temp = startY;
      startY = endY;
      endY = temp;
    }
    for (int r = index(startY); r <= index(endY); r++) {
      double yDistTraveled = (pixelLength*(r+0.5))-startY;
      double xCoord = (yDistTraveled/slope)+startX;
      usedPixels.add(pixelGrid[r][index(xCoord)]);
    }
  } else {
    if (startX > endX) {
      double temp = startX;
      startX = endX;
      endX = temp;
    }
    for (int c = index(startX); c <= index(endX); c++) {
      double xDistTraveled = (pixelLength*(c+0.5))-startX;
      double yCoord = (xDistTraveled*slope)+startY;
      usedPixels.add(pixelGrid[index(yCoord)][c]);
    }
  }
}

void drawPixels() {
  for (Pixel pixel : usedPixels) {
    pixel.drawPixel();
  }
}

// If coord is an x-value, then index() returns the column the x-value is in
// If coord is a y-value, then index() returns the row the y-value is in
int index(double coord) {
  return (int) (coord/pixelLength);
}

class Pixel {
  
  int row, column;
  
  Pixel(int row, int column) {
    this.row = row;
    this.column = column;
  }
  
  void drawPixel() {
    float topLeftX = (float) (column*pixelLength);
    float topLeftY = (float) (row*pixelLength);
    float extent = (float) pixelLength;
    rect(topLeftX, topLeftY, extent, extent);
  }
  
}
