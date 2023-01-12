void setup() {
  size(1200, 900);
  translate(width/2, height/2);
  seashell(800, 0);
  println(angle);
}

static float angle = (1 * PI) / (1 + sqrt(5));

void seashell(float radius, float startAngle) {
  if (radius > 5) {
    strokeWeight(radius / 100);
    arc(0, 0, radius, radius, startAngle, startAngle + angle, PIE);
    seashell(radius * 0.9, startAngle + angle);
  }
}
