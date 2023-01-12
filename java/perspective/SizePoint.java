package spect;

import mpc.math.Equation;
import mpc.math.Point;
import processing.core.PApplet;

// TODO comment this class
public class SizePoint extends Point {
	
	private double size;
	
	public SizePoint(double size, double... coords) {
		super(coords);
		this.size = size;
	}

	public SizePoint(double size, Point a) {
		super(a);
		this.size = size;
	}

	public SizePoint(SizePoint a) {
		super(a);
		size = a.size;
	}
	
	public double size() {
		return size;
	}
	
	public static final SizePoint ORIGIN = new SizePoint(0, new double[0]);
	
	public SizePoint enforce(int dimens) {
		double[] coords = new double[dimens];
		for (int i = 0; i < dimens; i++) {
			coords[i] = get(i);
		}
		return new SizePoint(size, coords);
	}
	
	/**
	 * CALCULATION, returns the point where this point would be if it were projected
	 * from the given starting point onto the given equation. Uses the method
	 * Equation.projectPointOntoEquation() for the main calculation, and then uses
	 * this point's placement between the starting point and the screen to determine
	 * how much the projected point's size should be scaled by
	 * 
	 * @param start  The starting point of the ray though this point
	 * @param screen The equation used as the screen in this projection
	 * @return The point of intersection between the given equation and the ray
	 *         formed by the two given points, scaled to simulate distance
	 */
	public SizePoint projectedOntoEquation(Point start, Equation screen) {
		Point projected = Equation.projectPointOntoEquation(start, this, screen);
		double totalDist = start.getDistFrom(projected);
		double projectionDist = getDistFrom(projected);
		double scaledSize = (size * totalDist) / (totalDist - projectionDist);
		return new SizePoint(scaledSize, projected);
	}
	
	public void plot2D(PApplet p) {
		if (dimens() < 3) {
			p.ellipse((float) get(X), (float) get(Y), (float) size, (float) size);
			// TODO use circle() instead. Does processing need to be updated?
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}
	
	public static void plotConnection2D(SizePoint a, SizePoint b, PApplet p) {
		int dimens = Math.max(a.dimens(), b.dimens());
		if (dimens < 3) {
			double dist = a.getDistFrom(b);
			double aScaleFactor = (a.size / 2) / dist; // divide size by 2 for radius
			double bScaleFactor = (b.size / 2) / dist;
			double xDiff = b.get(X) - a.get(X);
			double yDiff = b.get(Y) - a.get(Y);
			float x1 = (float) (a.get(X) - yDiff * aScaleFactor);
			float y1 = (float) (a.get(Y) + xDiff * aScaleFactor);
			float x2 = (float) (b.get(X) - yDiff * bScaleFactor);
			float y2 = (float) (b.get(Y) + xDiff * bScaleFactor);
			float x3 = (float) (b.get(X) + yDiff * bScaleFactor);
			float y3 = (float) (b.get(Y) - xDiff * bScaleFactor);
			float x4 = (float) (a.get(X) + yDiff * aScaleFactor);
			float y4 = (float) (a.get(Y) - xDiff * aScaleFactor);
			p.quad(x1, y1, x2, y2, x3, y3, x4, y4);
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}

	@Override
	public SizePoint isometric3Dto2D() {
		if (dimens() < 4) {
			double[] coords2D = new double[2];
			coords2D[X] = (get(X) - get(Y)) * SQRT3 / 2;
			coords2D[Y] = get(Z) - (get(X) + get(Y)) / 2;
			return new SizePoint(size, coords2D);
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public SizePoint isometric4Dto3D() {
		if (dimens() < 5) {
			double[] coords3D = new double[3];
			coords3D[X] = (get(X) + get(Y) - get(Z) - get(A)) / SQRT3;
			coords3D[Y] = (get(X) - get(Y) - get(Z) + get(A)) / SQRT3;
			coords3D[Z] = (get(X) + get(Y) + get(Z) + get(A)) / SQRT3;
			return new SizePoint(size, coords3D);
		} else {
			System.out.println("invalid point dimensions");
			throw new IllegalArgumentException();
		}
	}

}
