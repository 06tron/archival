package mpc.math;

import java.util.List;
import java.util.ArrayList;

/**
 * CLASS, meant to represent and perform functions on a point in space. The
 * number of dimensions to the point is variable
 * 
 * TODO hashcode and equals
 * 
 * @author mrichardson
 */
public class Point {

	/**
	 * VARIABLE, the array containing the coordinates of this point
	 */
	private double[] coords;

	/**
	 * CONSTRUCTOR, sets the coordinate values of this point
	 * 
	 * @param coords The given array which contains this point's coordinates
	 */
	public Point(double... coords) {
		this.coords = coords;
	}

	/**
	 * COPY CONSTRUCTOR, sets the coordinate values of this point to that of another
	 * 
	 * @param a The point from which to copy the coordinates
	 */
	public Point(Point a) {
		coords = new double[a.dimens()];
		for (int i = 0; i < dimens(); i++) {
			coords[i] = a.coords[i];
		}
	}

	/**
	 * CONSTANTS, letters that can be used to easily get coordinates out of a point
	 */
	public static final int X = 0, Y = 1, Z = 2, A = 3;

	/**
	 * CONSTANT, a point with all its coordinates equal to zero
	 */
	public static final Point ORIGIN = new Point(new double[0]);

	/**
	 * ACCESSOR, gives the number of coordinates, or dimensions, that go into this
	 * point
	 * 
	 * @return The length of this point's coordinate array
	 */
	public int dimens() {
		return coords.length;
	}

	/**
	 * ACCESSOR, returns a coordinate at the given index, or zero if the index is
	 * too high
	 * 
	 * @param i An index of the coordinate array, should be at least zero
	 * @return The element in this point's coordinate array at the given index
	 */
	public double get(int i) {
		if (i < coords.length) {
			return coords[i];
		} else {
			return 0;
		}
	}

	/**
	 * MUTATOR, sets the value of an element in the coordinate array, and resizes
	 * the array if necessary
	 * 
	 * @param i     An index of the coordinate array, should be at least zero
	 * @param coord The value to be placed in the coordinate array the given index
	 */
	public void set(int i, double coord) {
		if (i < coords.length) {
			coords[i] = coord;
		} else {
			double[] newCoords = new double[i + 1];
			for (int j = 0; j < coords.length; j++) {
				newCoords[j] = coords[j];
			}
			newCoords[i] = coord;
			coords = newCoords;
		}
	}

	/**
	 * CALCULATION, finds the distance between a given point and this point
	 * 
	 * @param b The second point to use in the distance calculation
	 * @return The square root of the sum of the squares of the differences between
	 *         corresponding coordinates of the two points
	 */
	public double getDistFrom(Point b) {
		int dimens = Math.max(dimens(), b.dimens());
		double sumSquares = 0;
		for (int i = 0; i < dimens; i++) {
			double diff = get(i) - b.get(i);
			sumSquares += diff * diff;
		}
		return Math.sqrt(sumSquares);
	}

	/**
	 * MUTATOR, takes the distance between the two given points, and moves this
	 * point along the line connecting them to the requested distance
	 * 
	 * @param b       The second point to use in the distance reset (is not mutated)
	 * @param newDist The distance the two given points will be from each other
	 *                after setDistFrom() has run
	 */
	public void setDistFrom(Point b, double newDist) {
		int dimens = Math.max(dimens(), b.dimens());
		double oldDist = getDistFrom(b);
		for (int i = dimens - 1; i >= 0; i--) {
			double diff = get(i) - b.get(i);
			set(i, diff * newDist / oldDist);
		}
	}

	/**
	 * STATIC MUTATOR, adds a given number of points to a pre-existing list. Points
	 * created are evenly spaced between two given points
	 * 
	 * @param a        The first of the two points
	 * @param b        The second of the two points
	 * @param pointNum The number of points to be added to the list
	 * @param points   A pre-existing list of points to be added to
	 */
	public static void addStrandOfPointsToList(Point a, Point b, int pointNum, List<Point> points) {
		int dimens = Math.max(a.dimens(), b.dimens());
		double[] spacing = new double[dimens];
		for (int i = 0; i < dimens; i++) {
			spacing[i] = (b.get(i) - a.get(i)) / (pointNum + 1);
		}
		Point progress = new Point(a);
		for (int i = 0; i < pointNum; i++) {
			for (int j = progress.dimens() - 1; j >= 0; j--) {
				progress.set(j, progress.get(j) + spacing[j]);
			}
			if (i < pointNum - 1) {
				points.add(new Point(progress));
			} else {
				points.add(progress);
			}
		}
	}

	/**
	 * STATIC MUTATOR, adds a given number squared of points to a pre-existing list.
	 * Points created are spaced inside the quadrilateral created by four given
	 * points. The points should be given in a circular pattern,
	 * 
	 * like this:  a  b 		not like this:  a  b
	 * 
	 *             d  c                         c  d
	 * 
	 * @param a        The first of the four points
	 * @param b        The second of the four points
	 * @param c        The third of the four points
	 * @param d        The fourth of the four points
	 * @param pointNum The number of points in a row or column of the points added
	 * @param points   A pre-existing list of points to be added to
	 */
	public static void addSheetOfPointsToList(Point a, Point b, Point c, Point d, int pointNum, List<Point> points) {
		List<Point> tempA = new ArrayList<>();
		addStrandOfPointsToList(a, b, pointNum, tempA);
		List<Point> tempB = new ArrayList<>();
		addStrandOfPointsToList(d, c, pointNum, tempB);
		for (int i = 0; i < pointNum; i++) {
			addStrandOfPointsToList(tempA.get(i), tempB.get(i), pointNum, points);
		}
	}

	/**
	 * CONSTANT, a double value close to the square root of 3. Used so that
	 * Math.sqrt() is not called on the same value repeatedly
	 */
	protected static final double SQRT3 = Math.sqrt(3);

	/**
	 * CALCULATION, converts a 3D point to a 2D one. Uses an isometric projection,
	 * so there is no perspective
	 * 
	 * @return A two-dimensional representation of a three-dimensional point, unless
	 *         this point has more than three dimensions, in which case a illegal
	 *         argument exception will be thrown
	 */
	public Point isometric3Dto2D() {
		if (dimens() < 4) {
			double[] coords2D = new double[2];
			coords2D[X] = (get(X) - get(Y)) * SQRT3 / 2;
			coords2D[Y] = get(Z) - (get(X) + get(Y)) / 2;
			return new Point(coords2D);
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}

	/**
	 * CALCULATION, converts a 4D point to a 3D one. Uses an isometric projection,
	 * so there is no perspective
	 * 
	 * @return A three-dimensional representation of a four-dimensional point,
	 *         unless this point has more than four dimensions, in which case a
	 *         illegal argument exception will be thrown
	 */
	public Point isometric4Dto3D() {
		if (dimens() < 5) {
			double[] coords3D = new double[3];
			coords3D[X] = (get(X) + get(Y) - get(Z) - get(A)) / SQRT3;
			coords3D[Y] = (get(X) - get(Y) - get(Z) + get(A)) / SQRT3;
			coords3D[Z] = (get(X) + get(Y) + get(Z) + get(A)) / SQRT3;
			return new Point(coords3D);
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}

	/**
	 * CALCULATION, edits this point's coordinates so that it is rotated around a
	 * given point. The rotation only occurs in two dimensions, so a point rotated
	 * in the x-y plane would only have its x and y coordinates edited. This
	 * calculation ignores coordinates other than the two given axes.
	 * 
	 * @param a       The point for the rotation calculation to center on
	 * @param radians The angle in radians of the rotation
	 * @param axis1   The first axis of the plane in which rotation is occurring
	 * @param axis2   The first axis of the plane in which rotation is occurring
	 */
	public void rotateAround(Point a, double radians, int axis1, int axis2) {
		double diffInAxis1 = get(axis1) - a.get(axis1);
		double diffInAxis2 = get(axis2) - a.get(axis2);
		double planeDist = Math.sqrt(diffInAxis1 * diffInAxis1 + diffInAxis2 * diffInAxis2);
		if (get(axis1) < a.get(axis1)) {
			radians += Math.PI;
		}
		double totalAngle = radians;
		if (diffInAxis1 == 0) {
			if (diffInAxis2 > 0) {
				totalAngle += Math.PI / 2;
			} else {
				totalAngle -= Math.PI / 2;
			}
		} else {
			totalAngle += Math.atan(diffInAxis2 / diffInAxis1);
		}
		set(axis1, a.get(axis1) + planeDist * Math.cos(totalAngle));
		set(axis2, a.get(axis2) + planeDist * Math.sin(totalAngle));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (coords.length > 0) {
			for (double coord : coords) {
				sb.append(", ");
				sb.append(coord);
			}
			return "(" + sb.substring(2) + ")";
		} else {
			return "(0)";
		}
	}

}
