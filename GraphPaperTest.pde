boolean a, b, c;

void setup() {
  size(192, 192);
  noStroke();
  a = b = c = true;
}

void draw() {
  background(0);
  fill(255);
  if (c) rect(0, 0, width/3, height);
  if (b) rect(width/3, 0, width/3, height);
  if (a) rect(2*width/3, 0, width/3, height);
}

void keyPressed() {
  if (key == ' ' && (a = !a) && (b = !b) && (c = !c));
}
