int xLoc;
int xSpd;
int xClr;

void setup () {
  size (200,300);
  xLoc = 20;
  xSpd = 1;//forwards
}

void draw () {
  background (0);
  noStroke ();
  xLoc = xLoc+xSpd;//motion
  xClr = (int)((xLoc-20)*255/ (width-40));/* xLoc(20-180) = xClr(0-255)
  (why is space neccessary) */
  fill (255, 255, 255);
  text ("Color:"+xClr, width-80, 30);
  if (xLoc == width-20) {
    xSpd = -1;//backwards
  }
  if (xLoc == 20) {
    xSpd=1;//forwards
  }
  fill (xClr, 0, 255-xClr);//color
  ellipse (xLoc, height/3, 40, 40);//dynamic
  if (xLoc < width/2) {
    fill (0, 0, 225);//blue
  } else fill (255, 0, 0);//red
  ellipse (xLoc, 2*height/3, 40, 40);//static
}
