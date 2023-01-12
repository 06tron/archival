long time;

void setup() {
  size(1024, 1024);
  background(255);
  //background(120, 232, 170);
  //noStroke();
  //squareFractal(7, 0, 0, width, false);
  translate(width / 5, height / 2);
  time = System.nanoTime();
  travelFour(3 * width / 5, 10, false, false);
  System.out.println((System.nanoTime() - time) / 1e9);
}

void squareFractal(int complexity, float nwX, float nwY, float sideLength, boolean under) {
  if (complexity <= 0) {
    fill(72, 207, 166);
    rect(nwX, nwY, sideLength, sideLength);
  } else {
    complexity--;
    sideLength /= 4;
    squareFractal(complexity - 1, nwX, nwY, sideLength, !under);
    squareFractal(complexity, nwX + sideLength, nwY + sideLength, 2 * sideLength, !under);
    squareFractal(complexity - 1, nwX + (3 * sideLength), nwY + (3 * sideLength), sideLength, !under);
    // squareFractal(complexity - 1, nwX + (3 * sideLength), nwY, sideLength, !under);
    // squareFractal(complexity - 1, nwX, nwY + (3 * sideLength), sideLength, !under);
    fill(51, 157, 148);
    if (under) {
      rect(nwX, nwY + sideLength, sideLength, 3 * sideLength);
      rect(nwX + sideLength, nwY + (3 * sideLength), 2 * sideLength, sideLength);
    } else {
      rect(nwX + sideLength, nwY, 3 * sideLength, sideLength);
      rect(nwX + (3 * sideLength), nwY + sideLength, sideLength, 2 * sideLength);
    }
  }
}

// (ante : cons) is the alternation ratio (normal travels : reflected travels)
void travelExperiment(float distance, int complexity, boolean reflect,
                        int ante, int cons, int count) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= sqrt(5);
    complexity--;
    if (reflect) {
      if (count == ante) {
        reflect = !reflect;
        count = 0;
      } else {
        count++;
      }
    } else {
      if (count == cons) {
        reflect = !reflect;
        count = 0;
      } else {
        count++;
      }
    }
    rotate(atan(2), !reflect);
    float radians = PI / 2;
    travelExperiment(distance, complexity, reflect, ante, cons, count); // 1
    rotate(radians, reflect);
    travelExperiment(distance, complexity, reflect, ante, cons, count); // 2
    rotate(radians, reflect);
    travelExperiment(distance, complexity, reflect, ante, cons, count); // 3
    rotate(radians, !reflect);
    travelExperiment(distance, complexity, reflect, ante, cons, count); // 4
    rotate(radians, !reflect);
    travelExperiment(distance, complexity, reflect, ante, cons, count); // 5
    rotate(atan(2), reflect);
  }
}

void travel(float distance) {
  line(0,0, distance, 0);
  translate(distance, 0);
}

void travelTwo(float distance, int complexity, boolean reflect, boolean alternate) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= sqrt(2);
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    rotate(PI / 4, !reflect);
    travelTwo(distance, complexity, reflect, alternate); // 1
    rotate(PI / 2, reflect);
    travelTwo(distance, complexity, reflect, alternate); // 2
    rotate(PI / 4, !reflect);
  }
}

void travelThree(float distance, int complexity, boolean reflect, boolean alternate) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= sqrt(3);
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    rotate(PI / 6, !reflect);
    float radians = 2 * PI / 3;
    travelThree(distance, complexity, reflect, alternate); // 1
    rotate(radians, reflect);
    travelThree(distance, complexity, reflect, alternate); // 2
    rotate(radians, !reflect);
    travelThree(distance, complexity, reflect, alternate); // 3
    rotate(PI / 6, reflect);
  }
}

void travelFour(float distance, int complexity, boolean reflect, boolean alternate) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= 2; // sqrt(4)
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    rotate(PI / 3, !reflect);
    float radians = 2 * PI / 3;
    travelFour(distance, complexity, reflect, alternate); // 1
    rotate(radians, reflect);
    travelFour(distance, complexity, reflect, alternate); // 2
    travelFour(distance, complexity, reflect, alternate); // 3
    rotate(radians, !reflect);
    travelFour(distance, complexity, reflect, alternate); // 4
    rotate(PI / 3, reflect);
  }
}

void travelFive(float distance, int complexity, boolean reflect, boolean alternate) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= sqrt(5);
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    rotate(atan(2), !reflect);
    float radians = PI / 2;
    travelFive(distance, complexity, reflect, alternate); // 1
    rotate(radians, reflect);
    travelFive(distance, complexity, reflect, alternate); // 2
    rotate(radians, reflect);
    travelFive(distance, complexity, reflect, alternate); // 3
    rotate(radians, !reflect);
    travelFive(distance, complexity, reflect, alternate); // 4
    rotate(radians, !reflect);
    travelFive(distance, complexity, reflect, alternate); // 5
    rotate(atan(2), reflect);
  }
}

void travelSix(float distance, int complexity, boolean reflect, boolean alternate) {
  if (complexity == 1) {
    travel(distance);
  } else {
    distance /= 3;
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    float radians = PI / 3;
    travelSix(distance, complexity, reflect, alternate);
    pushMatrix();
    rotate(radians, reflect);
    travelSix(distance, complexity, reflect, alternate);
    rotate(2 * radians, !reflect);
    travelSix(distance, complexity, reflect, alternate);
    popMatrix();
    rotate(radians, !reflect);
    travelSix(distance, complexity, reflect, alternate);
    rotate(2 * radians, reflect);
    travelSix(distance, complexity, reflect, alternate);
    rotate(radians, !reflect);
    travelSix(distance, complexity, reflect, alternate);
  }
}

void rotate(float radians, boolean clockwise) {
  if (clockwise) {
    rotate(radians);
  } else {
    rotate(-radians);
  }
}
