int order = 1;

void setup () {
  size(600, 600);
}

void draw () {
  translate (100, height-100);
  /*if (order <= 14*2) {
    drawLine(nogard(order/2), 1);
  }
  order++;*/
  hilbert (9, 5);
}

void drawLine(String instr, int len) {
  for (int i = 0; i < instr.length(); i++) {
    if (instr.charAt(i) == 'F') {
      line (0, 0, 0, len);
      translate (0, len);
    } else if (instr.charAt(i) == 'R') {
      rotate (radians (90));
    } else if (instr.charAt(i) == 'L') {
      rotate (radians (-90));
    }
  }
}

String dragon (int order) {
  String dragon = "F";
  String nogard = "F";
  for (int i = 0; i < order; i++) {
    String temp = dragon;
    dragon = temp + "L" + nogard;
    nogard = temp + "R" + nogard;
  }
  return dragon;
}

/*String hilbert (int order) {
  if (order <= 1) {
    return "FRFRF";
  }
}*/

void hilbert (int len, int order) {
  if (order > 0) {
    int siz = (int) (len * (pow (2, order - 1) - 1));
    pushMatrix ();
    translate (0, -siz);
    rotate (radians (90));
    hilbert (len, order - 1);
    line (0, 0, -len, 0);
    translate (-len, 0);
    rotate (radians (-90));
    hilbert (len, order-1);
    translate (len + siz, 0);
    line (0, 0, -len, 0);
    hilbert (len, order-1);
    translate (siz, len);
    line (0, 0, 0, -len);
    rotate (radians (-90));
    translate (-siz, 0);
    hilbert (len, order-1);
    popMatrix ();
  }
}
