package mpc.math;

/**
 * CLASS, turns a rectangular array (of doubles) into a matrix that can be used
 * in matrix operations
 * 
 * @author mrichardson
 */
public class Matrix {

	/**
	 * VARIABLE, the two-dimensional array of numbers that are elements in this
	 * matrix
	 */
	private double[][] elemes;

	/**
	 * CONSTRUCTOR, sets the values of the elements in this matrix
	 * 
	 * @param elemes The 2D array given to become this matrix's elements
	 */
	public Matrix(double[][] elemes) {
		this.elemes = elemes;
	}

	/**
	 * COPY CONSTRUCTOR, sets the elements in this matrix to those of another
	 * 
	 * @param a The matrix from which to copy the elements
	 */
	public Matrix(Matrix a) {
		elemes = new double[a.rows()][a.columns()];
		for (int r = 0; r < rows(); r++) {
			for (int c = 0; c < columns(); c++) {
				elemes[r][c] = a.elemes[r][c];
			}
		}
	}

	/**
	 * ACCESSOR, returns the number of rows in this matrix
	 * 
	 * @return The length of the element array, each element in the array being a
	 *         row
	 */
	public int rows() {
		return elemes.length;
	}

	/**
	 * ACCESSOR, returns the number of columns in this matrix
	 * 
	 * @return The length of the first row of this matrix, or the number of columns
	 */
	public int columns() {
		return elemes[0].length;
	}

	/**
	 * ACCESSOR, returns the element in this matrix at a given row and column
	 * 
	 * @param r The row index of the chosen element
	 * @param c The column index of the chosen element
	 * @return The element at row number r and column number c
	 */
	public double get(int r, int c) {
		return elemes[r][c];
	}
	
	/**
	 * ACCESSOR, spits out this matrix into a single array
	 * 
	 * @return Every element of this array in an array
	 */
	public double[] toArray() {
		double[] array = new double[rows() * columns()];
		int i = 0;
		for (int r = 0; r < rows(); r++) {
			for (int c = 0; c < columns(); c++) {
				array[i] = elemes[r][c];
				i++;
			}
		}
		return array;
	}

	/**
	 * CALCULATION, multiplies each element in the matrix by the given number
	 * 
	 * @param num The number to multiply each element by
	 * @return A new matrix which is the original matrix scaled by the given number
	 */
	public Matrix scaleBy(double num) {
		double[][] scaElemes = new double[rows()][columns()];
		for (int r = 0; r < rows(); r++) {
			for (int c = 0; c < columns(); c++) {
				scaElemes[r][c] = elemes[r][c] * num;
			}
		}
		return new Matrix(scaElemes);
	}

	/**
	 * CALCULATION, finds the determinant of the this matrix
	 * 
	 * @return The determinant as long as the given matrix is square, otherwise
	 *         throws an arithmetic exception
	 */
	public double determinant() {
		int length = rows();
		if (columns() == length && length > 0) {
			if (length == 1) {
				return elemes[0][0];
			} else if (length == 2) {
				return elemes[0][0] * elemes[1][1] - elemes[0][1] * elemes[1][0];
			} else {
				int result = 0;
				boolean sign = true;
				for (int c = 0; c < length; c++) {
					if (sign) {
						result += elemes[0][c] * minor(0, c);
					} else {
						result -= elemes[0][c] * minor(0, c);
					}
					sign = !sign;
				}
				return result;
			}
		} else {
			System.out.println("invalid matrix dimensions");
			throw new ArithmeticException();
		}
	}

	/**
	 * PRIVATE CALCULATION, finds the minor of this matrix
	 * 
	 * @param row    The row of the original matrix to exclude from the minor matrix
	 * @param column The column of the original matrix to exclude from the minor
	 *               matrix
	 * @return The determinant of the minor matrix
	 */
	private double minor(int row, int column) {
		double[][] minElemes = new double[rows() - 1][columns() - 1];
		int r = 0, mr = 0;
		while (r < rows()) {
			if (r != row) {
				int c = 0, mc = 0;
				while (c < columns()) {
					if (c != column) {
						minElemes[mr][mc] = elemes[r][c];
						mc++;
					}
					c++;
				}
				mr++;
			}
			r++;
		}
		return new Matrix(minElemes).determinant();
	}

	/**
	 * CALCULATION, finds the adjugate matrix of this matrix
	 * 
	 * @return A new matrix as long as the original matrix is square, otherwise
	 *         throws an arithmetic exception
	 */
	public Matrix adjugate() {
		int length = rows();
		if (columns() == length) {
			double[][] adjElemes = new double[rows()][columns()];
			boolean startSign = true;
			for (int r = 0; r < rows(); r++) {
				boolean sign = startSign;
				for (int c = 0; c < columns(); c++) {
					if (sign) {
						adjElemes[c][r] = minor(r, c);
					} else {
						adjElemes[c][r] = minor(r, c) * -1;
					}
					sign = !sign;
				}
				startSign = !startSign;
			}
			return new Matrix(adjElemes);
		} else {
			System.out.println("invalid matrix dimensions");
			throw new ArithmeticException();
		}
	}

	/**
	 * CALCULATION, finds the inverse of this matrix
	 * 
	 * @return A new matrix which is the adjugate multiplied by one over the
	 *         determinant
	 */
	public Matrix inverse() {
		return adjugate().scaleBy(1 / determinant());
	}

	/**
	 * CALCULATION, adds two given matrices (commutative)
	 * 
	 * @param a The first matrix in the matrix addition
	 * @param b The second matrix in the matrix addition
	 * @return A new matrix as long as both matrices have the same dimensions,
	 *         otherwise throws an arithmetic exception
	 */
	public static Matrix add(Matrix a, Matrix b) {
		if (a.rows() == b.rows() && a.columns() == b.columns()) {
			double[][] addElemes = new double[b.rows()][a.columns()];
			for (int r = 0; r < a.rows(); r++) {
				for (int c = 0; c < b.columns(); c++) {
					addElemes[r][c] = a.get(r, c) + b.get(r, c);
				}
			}
			return new Matrix(addElemes);
		} else {
			System.out.println("invalid matrix addition");
			throw new ArithmeticException();
		}
	}

	/**
	 * CALCULATION, subtracts two given matrices (not commutative)
	 * 
	 * @param a The first matrix in the matrix subtraction
	 * @param b The second matrix in the matrix subtraction
	 * @return The addition of the first matrix to the second matrix scaled by
	 *         negative one
	 */
	public static Matrix subtract(Matrix a, Matrix b) {
		return add(a, b.scaleBy(-1));
	}

	/**
	 * CALCULATION, multiplies two given matrices (not commutative)
	 * 
	 * @param a The first matrix in the matrix multiplication
	 * @param b The second matrix in the matrix multiplication
	 * @return A new matrix as long as the first matrix's width is the same as the
	 *         second matrix's height, otherwise throws an arithmetic exception
	 */
	public static Matrix multiply(Matrix a, Matrix b) {
		int length = a.columns();
		if (b.rows() == length) {
			double[][] mulElemes = new double[a.rows()][b.columns()];
			for (int r = 0; r < a.rows(); r++) {
				for (int c = 0; c < b.columns(); c++) {
					for (int i = 0; i < length; i++) {
						mulElemes[r][c] += a.get(r, i) * b.get(i, c);
					}
				}
			}
			return new Matrix(mulElemes);
		} else {
			System.out.println("invalid matrix multiplication");
			throw new ArithmeticException();
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(rows());
		sb.append("x");
		sb.append(columns());
		sb.append(" Matrix:\n");
		for (int r = 0; r < rows(); r++) {
			sb.append("[");
			for (int c = 0; c < columns(); c++) {
				sb.append(" ");
				sb.append(String.format("%7.3f", elemes[r][c]));
			}
			sb.append(" ]\n");
		}
		return sb.toString();
	}

}
