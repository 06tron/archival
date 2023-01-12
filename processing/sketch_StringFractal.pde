PMatrix current;
String[] instructions;
int index;

void setup() {
  size(1000, 800);
  background(255);
  translate(width / 5, height / 2);
  current = getMatrix();
  instructions = travelThree(3 * width / 5, 7, false, false).toString().split("(?=[TR])");
  index = 0;
}

void draw() {
  if (index < instructions.length) {
    setMatrix(current);
    String command = instructions[index];
    char commandType = command.charAt(0);
    if (commandType == 'T') {
      float distance = Float.parseFloat(command.substring(1));
      line(0,0, distance, 0);
      translate(distance, 0);
    }
    if (commandType == 'R') {
      float radians = Float.parseFloat(command.substring(1, command.indexOf(',')));
      if (Boolean.parseBoolean(command.substring(command.indexOf(',') + 1))) {
        rotate(radians);
      } else {
        rotate(-radians);
      }
    }
    current = getMatrix();
    index++;
  }
}


StringBuilder travelThree(float distance, int complexity, boolean reflect, boolean alternate) {
  StringBuilder sb = new StringBuilder();
  if (complexity == 1) {
    sb.append('T');
    sb.append(Float.toString(distance));
  } else {
    distance /= sqrt(3);
    complexity--;
    if (alternate) {
      reflect = !reflect;
    }
    String travel = travelThree(distance, complexity, reflect, alternate).toString();
    sb.append('R');
    sb.append(Float.toString(PI / 6));
    sb.append(',');
    sb.append(Boolean.toString(!reflect));
    float radians = 2 * PI / 3;
    sb.append(travel);
    sb.append('R');
    sb.append(Float.toString(radians));
    sb.append(',');
    sb.append(Boolean.toString(reflect));
    sb.append(travel);
    sb.append('R');
    sb.append(Float.toString(radians));
    sb.append(',');
    sb.append(Boolean.toString(!reflect));
    sb.append(travel);
    sb.append('R');
    sb.append(Float.toString(PI / 6));
    sb.append(',');
    sb.append(Boolean.toString(reflect));
  }
  return sb;
}
