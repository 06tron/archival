int deg = 1;
int order = 1;
int len = 1;
int count = 0;

void setup () {
  size (765, 765);
}

void draw () {
  if (order <= 19*2) {
    translate (7*width/30, 5*height/6);
    rotate (radians (90));
    String dragon = dragon(order/2);
    for (int i = 0; i < dragon.length(); i++) {
      if (dragon.charAt(i) == 'F') {
        line (0, 0, 0, len);
        translate (0, len);
      } else if (dragon.charAt(i) == 'L') {
        rotate (radians (90));
      } else if (dragon.charAt(i) == 'R') {
        rotate (radians (-90));
      }
    }
  }
  order++;
  /*
  if (deg > 1440) deg = 1;
  else deg += 1;
  translate (width/2, height/2);
  rotate (radians (deg/4));
  background (255);
  fract (200, deg/4, 255);
  
  if (count < 1000) {
    translate (mouseX, mouseY);
    card (300);
  }
  count++;
  */
}

void fract (int len, int deg, int clr) {
  if (len > 30) {
    line (0, len, 0, -len);
    fill (clr, 0, 0);
    ellipse (0, 0, (4/3.0)*len, (4/3.0)*len);
    pushMatrix ();
    translate (0, len);
    rotate (radians (deg));
    fract (len/2, deg, clr-20);
    popMatrix ();
    pushMatrix ();
    translate (0, -len);
    rotate (radians (deg));
    fract (len/2, deg, clr-20);
    popMatrix ();
  }
}

String dragon (int order) {
  String dragon = "F";
  String nogard = "F";
  for (int i = 0; i < order; i++) {
    String d = dragon;
    dragon = d + "L" + nogard;
    nogard = d + "R" + nogard;
  }
  return dragon;
}

void card (int wth) {
  /*if (wth < 20) {
    stroke (0);
  } else {
    noStroke ();
  }*/
  if (wth > 1) {
    fill (mouseX/3, 200, mouseY/3);
    ellipse (0, 0, wth, wth);
    pushMatrix ();
    rotate (radians (random (0, 360)));
    translate (0, wth/2);
    card (2*wth/3);
    popMatrix ();
    /*pushMatrix ();
    rotate (radians (random (-25, -85)));
    translate (0, wth/2);
    card (2*wth/3);
    popMatrix ();*/
  }
}
