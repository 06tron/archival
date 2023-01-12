int w, h;
TestVector a, b, c;

void setup() {
  size(1536, 768);
  w = 10000;
  h = 10000;
  a = new TestVector(51, 16);
  b = new TestVector(4, 22);
  c = new TestVector(28, 9);
}

void draw() {
  background(191);
  // translate(width/4, height/4);
  noStroke();
  rect(0, 0, w, h);
  stroke(0);
  strokeWeight(5);
  //int rise = a.y + b.y;
  //int run = a.x + b.x;
  //float sa = (a.x * h + a.y * w) / (a.x * b.y + a.y * b.x);
  //float sb = (b.x * h + b.y * w) / (b.x * a.y + b.y * a.x);
  //int cap = (int) (max(sa, sb) / 2) + 1;
  for (int i = 0; i < 20; i++) {
    for (int j = 0; j < 20; j++) {
      for (int k = 0; k < 20; k++) {
         TestVector.add(a.scale(i), b.scale(j), c.scale(k)).drawTo(this, w, h);
      }
    }
  }
}

void keyPressed() {
  if (keyCode == UP) {
    h += 10;
  }
  if (keyCode == DOWN) {
    h -= 10;
  }
  if (keyCode == LEFT) {
    w -= 10;
  }
  if (keyCode == RIGHT) {
    w += 10;
  }
  if (key == 'w') {
    a.y += 10;
  }
  if (key == 's') {
    a.y -= 10;
  }
  if (key == 'a') {
    a.x -= 10;
  }
  if (key == 'd') {
    a.x += 10;
  }
}

static class TestVector {
  
  int x, y;
  
  TestVector(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  TestVector scale(int s) {
    return new TestVector(x * s, y * s);
  }
  
  static TestVector add(TestVector a, TestVector b, TestVector c) {
    return new TestVector(a.x + b.x + c.x, a.y + b.y + c.y);
  }
  
  void drawTo(PApplet p, int w, int h) {
    p.point(x % w, y % h);
  }
  
}
