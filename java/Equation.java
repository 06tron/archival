package mpc.math;

/**
 * CLASS, represents a linear equation in standard form: Ax + By + .. = C
 * 
 * @author mrichardson
 */
public class Equation {

	/**
	 * VARIABLE, an array to store the coefficents of a linear equation in standard
	 * form
	 * 
	 * Ex: Equation Ax + By + Cz = D would have coeffs {A, B, C}
	 */
	private double[] coeffs;

	/**
	 * VARIABLE, the constant of a linear equation in standard form
	 * 
	 * Ex: Equation Ax + By + Cz = D would have constant D
	 */
	private double constant;

	/**
	 * CONSTRUCTOR, sets the values of this equation's constant and coefficents
	 * 
	 * @param coeffs   An array of coefficents for this equation
	 * @param constant A constant value for this equation
	 */
	public Equation(double[] coeffs, double constant) {
		this.coeffs = coeffs;
		this.constant = constant;
	}

	/**
	 * COPY CONSTRUCTOR, sets the values of this equation to those of another
	 * 
	 * @param a The equation from which to copy coefficents and a constant
	 */
	public Equation(Equation a) {
		coeffs = new double[a.coeffs.length];
		for (int i = 0; i < coeffs.length; i++) {
			coeffs[i] = a.coeffs[i];
		}
		constant = a.constant;
	}
	
	// TODO create equation given points

	/**
	 * ACCESSOR, returns this equation's coefficents
	 * 
	 * @return The array that represents this equation's coefficents
	 */
	public double[] getCoeffs() {
		return coeffs;
	}

	/**
	 * ACCESSOR, returns this equation's constant
	 * 
	 * @return The number that represents this equations's constant
	 */
	public double getConst() {
		return constant;
	}

	/**
	 * CALCULATION, finds the intersection point of multiple equations using
	 * matrices. Will not work correctly in cases this method is not meant for
	 * 
	 * @param equats An array of equations to find the intersection of
	 * @return A point with as many coordinates as there are given equations
	 */
	public static Point intersectionBetween(Equation... equats) {
		int varNum = equats.length;
		double[][] allCoeffs = new double[varNum][varNum];
		double[][] allConsts = new double[varNum][1];
		for (int r = 0; r < varNum; r++) {
			for (int c = 0; c < varNum; c++) {
				if (c < equats[r].coeffs.length) {
					allCoeffs[r][c] = equats[r].coeffs[c];
				} else {
					allCoeffs[r][c] = 0.0;
				}
			}
			allConsts[r][0] = equats[r].constant;
		}
		Matrix solvedVars = Matrix.multiply(new Matrix(allCoeffs).inverse(), new Matrix(allConsts));
		return new Point(solvedVars.toArray());
	}

	/**
	 * CALCULATION, finds the point where a ray (beginning at the starting point and
	 * going through the other given point) intersects the given equation. In the
	 * example, a ray starts at S, goes through A, and intersects the screen at E.
	 * 
	 *	S
	 *	 \         / 
	 *	  \       / <- screen
	 *	   A     /     S is start
	 *		\   /      A is a
	 *		 \ /       E is end (return value)
	 *		  E
	 *		 /
	 *		/
	 * 
	 * @param start  The starting point of the ray though the other given point (a)
	 * @param a      The point to be projected onto the screen
	 * @param screen The equation used as the screen in this projection
	 * @return The point of intersection between the given equation and the ray
	 *         formed by the two given points
	 */
	public static Point projectPointOntoEquation(Point start, Point a, Equation screen) {
		int dimens = screen.coeffs.length;
		double[] diffs = new double[dimens];
		for (int i = 0; i < dimens; i++) {
			diffs[i] = a.get(i) - start.get(i);
		}
		double sumToSubtract = 0;
		double sumDenominator = 0;
		for (int i = 0; i < dimens; i++) {
			sumToSubtract += screen.coeffs[i] * start.get(i);
			sumDenominator += screen.coeffs[i] * diffs[i];
		}
		double scaleFactor = (screen.constant - sumToSubtract) / sumDenominator;
		double[] endCoords = new double[dimens];
		for (int i = 0; i < dimens; i++) {
			endCoords[i] = start.get(i) + (diffs[i] * scaleFactor);
		}
		return new Point(endCoords);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < coeffs.length; i++) {
			sb.append("+ ");
			sb.append(coeffs[i]);
			sb.append("(v");
			sb.append(i);
			sb.append(") ");
		}
		return sb.substring(2) + "= " + constant;
	}

}
