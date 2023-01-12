int rise, run; 
int density; 

void setup() {
  size(1400, 700);
  translate(width/4, height/4);
  rise = 30;
  run = 30;
  density = 10;
  noStroke();
  int w = run * density;
  int h = rise * density;
  rect(0, 0, w, h);
  stroke(0);
  strokeWeight(5);
  shearX(0.3);
  for (int i = 0; i <= density; i++) {
    for (int j = 0; j <= density; j++) {
      point((run * i) % w, (rise * j) % h);
    }
  }
}
