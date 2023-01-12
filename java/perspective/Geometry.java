package spect;

import java.util.ArrayList;
import java.util.List;

import mpc.math.Point;
// import spect.math.Graph;

// TODO comment this class
public class Geometry {
	
//	public static Graph<Point> hypercube(int dimens) {
//		return new Graph<Point>();
//	}
	
	public static List<Point> pyramidPoints(boolean steep) {
		List<Point> points = new ArrayList<>();
		if (steep) {
			
		} else {
			int num = 85;
			for (int h = 0; h < num+1; h++) {
				for (int w = -h; w <= h; w++) {
					points.add(new Point(w, num-h, h));
					points.add(new Point(w, num-h, -h));
					points.add(new Point(h, num-h, w));
					points.add(new Point(-h, num-h, w));
				}
			}
		}
		return points;
	}
	
	public static List<IndexConnection> pyramidICs(boolean steep) {
		return new ArrayList<IndexConnection>();
	}
	
	public static final double PHI = (1 + Math.sqrt(5)) / 2;
	
	public static List<Point> shipPoints() {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < 1; i++) {
			points.add(new Point(0, 1, PHI));
			points.add(new Point(0, -1, PHI));
			points.add(new Point(0, 1, -PHI));
			points.add(new Point(0, -1, -PHI));
			points.add(new Point(1, PHI, 0));
			points.add(new Point(-1, PHI, 0));
			points.add(new Point(1, -PHI, 0));
			points.add(new Point(-1, -PHI, 0));
			points.add(new Point(PHI, 0, 1));
			points.add(new Point(PHI, 0, -1));
			points.add(new Point(-PHI, 0, 1));
			points.add(new Point(-PHI, 0, -1));
		}
		for (Point p : points) {
			p.rotateAround(Point.ORIGIN, Math.atan(PHI), Point.X, Point.Y);
		}
		double scaleFactor = 2;
		for (int i = 0; i < points.size(); i++) {
			points.get(i).set(Point.X, points.get(i).get(Point.X) * scaleFactor); 
		}
		return points;
	}
	
	public static List<IndexConnection> shipICs() {
		return new ArrayList<IndexConnection>();
	}
	
	public static List<Point> hypercubePoints(int dimens) {
		List<Point> points;
		if (dimens < 2) {
			points = new ArrayList<>();
			points.add(new Point(1));
			points.add(new Point(-1));
		} else {
			points = hypercubePoints(dimens - 1);
			int length = points.size();
			int i = 0;
			do {
				points.get(i).set(dimens - 1, 1);
				Point duplicate = new Point(points.get(i));
				duplicate.set(dimens - 1, -1);
				points.add(duplicate);
				i++;
			} while (i < length);
		}
		return points;
	}
	
	public static List<IndexConnection> hypercubeICs(int dimens) {
		List<IndexConnection> knex;
		if (dimens < 2) {
			knex = new ArrayList<>();
			knex.add(new IndexConnection(0, 1));
		} else {
			knex = hypercubeICs(dimens - 1);
			int length = knex.size();
			int powerOf2 = (int) Math.pow(2, dimens - 1);
			for (int i = 0; i < length; i++) { // duplication
				knex.add(new IndexConnection(knex.get(i).i() + powerOf2, knex.get(i).j() + powerOf2));
			}
			for (int i = 0; i < powerOf2; i++) { // connection
				knex.add(new IndexConnection(i, i + powerOf2));
			}
		}
		return knex;
	}
	
	public static List<Point> orthoplexPoints(int dimens) {
		List<Point> points = new ArrayList<>();
		for (int i = 0; i < dimens; i++) {
			Point pos = new Point(Point.ORIGIN);
			pos.set(i, 1);
			points.add(pos);
			Point neg = new Point(Point.ORIGIN);
			neg.set(i, -1);
			points.add(neg);
		}
		return points;
	}
	
	public static List<IndexConnection> orthoplexICs(int dimens) {
		List<IndexConnection> knex = new ArrayList<>();
		int length = dimens * 2;
		for (int i = 0; i < length; i++) {
			for (int j = (i + 2) - (i % 2); j < length; j++) {
				knex.add(new IndexConnection(i, j));
			}
		}
		return knex;
	}
	
//	public static List<IndexConnection> hypercubeIncorrectICs(int dimens, int crossDimens) {
//		List<IndexConnection> knex = new ArrayList<>();
//		int length = (int) Math.pow(2, dimens);
//		int groupSize = (int) Math.pow(2, crossDimens);
//		int[] indexGroup = new int[groupSize];
//		for (int incr = 1; incr < length; incr *= 2) {
//			int indexCount = 0;
//			for (int i = 0; i < length; i += incr) {
//				indexGroup[indexCount++] = i;
//				if (indexCount == groupSize) {
//					for (int j = 0; j < incr; j++) {
//						for (int k = 0; k < groupSize / 2; k++) {
//							knex.add(new IndexConnection(indexGroup[k] + j, indexGroup[(groupSize - 1) - k] + j));
//						}
//					}
//					indexCount = 0;
//				}
//			}
//		}
//		return knex;
//	}
//	
//	public static class IndexList extends ArrayList<Integer> {}
//	
//	public static List<IndexConnection> hypercubeCorrectICs(int dimens, int crossDimens) {
//		List<IndexConnection> knex = new ArrayList<>();
//		int length = (int) Math.pow(2, dimens);
//		int groupSize = (int) Math.pow(2, crossDimens);
//		for (int line = 0; line < dimens; line++) {
//			IndexList[] groups = new IndexList[length / groupSize];
//			int groupIndex = 0;
//			int count = 0;
//			for (int i = 0; i < length; i++) {
//				if (count == groupSize / (line + 1)) {
//					groupIndex++;
//				}
//				groups[groupIndex].add(i);
//				count++;
//			}
//		}
//		return knex;
//	}
//	
//	public static void main(String[] args) {
//		System.out.println(hypercubeCorrectICs(3, 2));
//	}

}
