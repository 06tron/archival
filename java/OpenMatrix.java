package mpc.linear;

import java.util.Scanner;
import java.util.stream.Stream;

public class OpenMatrix {
  
  double defaultEntry;
  
  int rows, cols;
  double[] entries;
  
  public OpenMatrix(double defaultEntry) {
    this.defaultEntry = defaultEntry;
    rows = 0;
    cols = 0;
    entries = null;
  }
  
  public OpenMatrix(int rows, int cols) {
    this(rows, cols, readInput());
  }
  
  private static double[] readInput() {
    try (Scanner sc = new Scanner(System.in)) {
      return Stream.of(sc.next().split(",")).mapToDouble(Double::parseDouble).toArray();
    }
  }
  
  public OpenMatrix(OpenMatrix input) {
    defaultEntry = input.defaultEntry;
    rows = input.rows;
    cols = input.cols;
    entries = new double[rows * cols];
    for (int i = 0; i < entries.length; i++) {
      entries[i] = input.entries[i];
    }
  }
  
  public OpenMatrix(int rows, int cols, OpenMatrix other) {
    defaultEntry = 0;
    this.rows = rows;
    this.cols = cols;
    entries = new double[rows * cols];
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        entries[indexOf(r, c)] = other.entryAt(r, c);
      }
    }
  }
  
  public OpenMatrix(int rows, int cols, double... input) {
    defaultEntry = 0;
    this.rows = rows;
    this.cols = cols;
    entries = new double[rows * cols];
    int length = Math.min(input.length, entries.length);
    for (int i = 0; i < length; i++) {
      entries[i] = input[i];
    }
  }
  
  public OpenMatrix(double[][] input) {
    defaultEntry = 0;
    rows = input.length;
    int max = 0;
    for (int r = 0; r < input.length; r++) {
      if (max < input[r].length) {
        max = input[r].length;
      }
    }
    cols = max;
    entries = new double[rows * cols];
    for (int r = 0; r < input.length; r++) {
      for (int c = 0; c < input[r].length; c++) {
        entries[indexOf(r, c)] = input[r][c];
      }
    }
  }
  
  public int rows() {
    return rows;
  }
  
  public int columns() {
    return cols;
  }
  
  /**
   * @return a positive integer
   */
  private int indexOf(int r, int c) {
    if (r < 0 || c < 0) {
      throw new IndexOutOfBoundsException("r == " + r + " && c == " + c);
    }
    return r * cols + c;
  }
  
  public double entryAt(int r, int c) {
    if (r < rows && c < cols) {
      return entries[indexOf(r, c)];
    }   
    return defaultEntry;
  }
  
  public static void main(String[] args) {
    int rows = 3;
    int cols = 2;
    OpenMatrix a = new OpenMatrix(rows, cols, new OpenMatrix(2));
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        System.out.print(a.entryAt(r, c) + " ");
      }
      System.out.println();
    }
  }

}
