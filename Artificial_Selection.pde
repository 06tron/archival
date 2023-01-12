int lth;
int wth;
int deathRate;

void setup () {
  size (800,800);
  lth = 100;
  wth = 100;
}

void draw () {
  wth += (int) random (-5,5);
  lth += (int) random (-5,5);
  ellipse (width/2, height/2, wth, lth);
  if (lth > 20) {
    deathRate += 1;
  }
  if (deathRate > 10) lth -= 1;
}
