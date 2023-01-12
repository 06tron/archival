float dist;
String instructions;

void setup() {
  size(800, 800);
  translate(width/2, 2*height/5);
  background(255);
  dist = 4;
  instructions = travelFour(6);
  for (int i = 0; i < instructions.length(); i++) {
    follow(i);
  }
}

void follow(int index) {
  char command = instructions.charAt(index);
  if (command == 'F') {
    line(0,0, dist, 0);
    translate(dist, 0);
  }
  if (command == 'L') {
    rotate(-2 * PI / 3);
  }
  if (command == 'R') {
    rotate(2 * PI / 3);
  }
}

String travelFour(int complexity) {
  CharChain chain = new CharChain("F");
  for (int c = 0; c < complexity; c++) {
    int size = chain.size();
    for (int i = size - 1; i >= 0; i--) {
      if (chain.get(i) == 'F') {
        chain.remove(i);
        chain.add(i, new CharChain("FRFFLF"));
      }
    }
  }
  StringBuilder sb = new StringBuilder();
  chain.build(sb);
  return sb.toString();
}

static class CharChain {
  
  private java.util.List<CharChain> chain;
  
  private int size;
  
  protected CharChain() { // used by CharLink
    size = 1;
    chain = null;
  }
  
  public CharChain(String initial) {
    size = initial.length();
    chain = new java.util.ArrayList<CharChain>();
    for (char c : initial.toCharArray()) {
      chain.add(new CharLink(c));
    }
  }
  
  public int size() {
    return size;
  }
  
  public void add(CharChain next) {
    chain.add(next);
    size += next.size;
  }
  
  public void add(int target, CharChain insert) {
    if (target < 0 || target > size) {
      throw new IllegalArgumentException();
    } else if (target == size) {
      add(insert);
    } else {
      int index, count = 0;
      for (index = 0; count < target; index++) {
        count += chain.get(index).size;
      }
      if (count == target) {
        chain.add(index, insert);
      } else { // overshoot
        count -= chain.get(index - 1).size;
        chain.get(index - 1).add(target - count, insert);
      }
      size += insert.size;
    }
  }
  
  public char get(int target) {
    if (target < 0 || target >= size) {
      throw new IllegalArgumentException();
    }
    int index, count = 0;
    for (index = 0; count < target; index++) {
      count += chain.get(index).size;
    }
    if (count == target) {
      return chain.get(index).get(0);
    } else { // overshoot
      count -= chain.get(index - 1).size;
      return chain.get(index - 1).get(target - count);
    }
  }
  
  public void remove(int target) {
    if (target < 0 || target >= size) {
      throw new IllegalArgumentException();
    }
    int index, count = 0;
    for (index = 0; count < target; index++) {
      count += chain.get(index).size;
    }
    if (count == target) {
      CharChain segment = chain.get(index);
      if (segment instanceof CharLink) {
        chain.remove(index);
      } else {
        segment.remove(0);
      }
    } else { // overshoot
      count -= chain.get(index - 1).size;
      chain.get(index - 1).remove(target - count);
    }
    size--;
  }
  
  public void build(StringBuilder sb) {
    for (CharChain c : chain) {
      c.build(sb);
    }
  }
  
}

static class CharLink extends CharChain {
  
  private char link;
  
  public CharLink(char initial) {
    super();
    link = initial;
  }
  
  @Override
  public void add(CharChain next) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void add(int target, CharChain insert) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public char get(int target) {
    if (target != 0) {
      throw new IllegalArgumentException();
    }
    return link;
  }
  
  @Override
  public void remove(int target) {
    throw new UnsupportedOperationException();
  }
  
  @Override
  public void build(StringBuilder sb) {
    sb.append(link);
  }
  
}
