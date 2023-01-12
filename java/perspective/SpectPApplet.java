package spect;

import java.util.ArrayList;
import java.util.List;

import mpc.math.Equation;
import mpc.math.Point;
import processing.core.PApplet;

public class SpectPApplet extends PApplet {
	
	public static void main(String[] args) {
		PApplet.main("spect.SpectPApplet");
	}
	
	private enum GeoShape {
		
		ORTHOPLEX, HYPERCUBE, SHIP, PYRAMID;
		
		public List<Point> getPoints(int dimens) {
			switch(this) {
				case ORTHOPLEX:
					return Geometry.orthoplexPoints(dimens);
				case HYPERCUBE:
					return Geometry.hypercubePoints(dimens);
				case SHIP:
					return Geometry.shipPoints();
				case PYRAMID:
					return Geometry.pyramidPoints(false);
				default:
					return null;
			}
		}
		
		public List<IndexConnection> getICs(int dimens) {
			switch(this) {
				case ORTHOPLEX:
					return Geometry.orthoplexICs(dimens);
				case HYPERCUBE:
					return Geometry.hypercubeICs(dimens);
				case SHIP:
					return Geometry.shipICs();
				case PYRAMID:
					return Geometry.pyramidICs(false);
				default:
					return null;
			}
		}
		
	}
	
	List<SizePoint> axisPoints;
	
	private List<SizePoint> mainPoints;
	private List<SizePoint> spectPoints;
	
	private Point pov;
	private Equation screen3D;
	
	
	// FINISHED VARIABLES
	
	private GeoShape template;
	private int dimens;
	private double pointSize;
	private double sphereRadius;
	private int[][] rotationSpeed;
	private int rotaxis1;
	private boolean rotaxis1Assigned;
	private int rotaxis2;
	private boolean dispRotaxes;
	private boolean dispInfo;

	@Override
	public void settings() {
		size(1200, 900);
	}
	
	public void initializeVariables() {
		template = GeoShape.HYPERCUBE; // edit
		dimens = 4; // edit
		pointSize = 1; // edit
		sphereRadius = 50; // edit
		pov = new Point(0, 0, 20 * sphereRadius); // edit
		initializeAxisPoints();
		initializeMainPoints(); // add to
		rotationSpeed = new int[dimens][dimens]; // has default
		rotaxis1 = 0; // set default
		rotaxis1Assigned = false; // set default
		rotaxis2 = 0; // set default
		dispRotaxes = false; // set default
		dispInfo = false; // has default
	}
	
	public void initializeAxisPoints() {
		axisPoints = new ArrayList<>();
		for (Point onAxis : Geometry.orthoplexPoints(dimens)) {
			onAxis.setDistFrom(Point.ORIGIN, 2 * sphereRadius);
			axisPoints.add(new SizePoint(0, onAxis));
		}
	}
	
	public void initializeMainPoints() {
		mainPoints = new ArrayList<>();
		for (Point inTemplate : template.getPoints(dimens)) {
			inTemplate.setDistFrom(Point.ORIGIN, inTemplate.getDistFrom(Point.ORIGIN) * 10);
			mainPoints.add(new SizePoint(pointSize, inTemplate));
		}
	}

	@Override
	public void setup() {
		initializeVariables();
		fill(0);
		double[] coeffs3D = {0, 0, 1};
		screen3D = new Equation(coeffs3D, -30 * sphereRadius);
		//start4D = new Point(0, 0, 0, 200);
		//double[] coeffs4D = {0, 0, 0, 1};
		//screen4D = new Equation(coeffs4D, -200);
	}

	@Override
	public void draw() {
		background(255); // ~~~~~~~~~~~~~~~~~~~~~~~~~~
		translate(width/2, height/2); // ~~~~~~~~~~~~~
		if (dispInfo) {
			dispInfo(); // ~~~~~~~~~~~~~~~~~~~~~~~~~~~
		}
		spectPoints = new ArrayList<>();
		for (SizePoint mP : mainPoints) {
			rotatePoint(mP);
			SizePoint spect = to2D(dimens, mP);
			spect.plot2D(this);
			spectPoints.add(spect);
		}

		List<IndexConnection> knex = template.getICs(dimens);
		for (IndexConnection ic : knex) { // TODO question: is knex re-evaluated after each pass through the for-loop?
			SizePoint.plotConnection2D(spectPoints.get(ic.i()), spectPoints.get(ic.j()), this);
			// plotLine(spectPoints.get(ic.i()), spectPoints.get(ic.j()));
		}
		
	}
	
	private static final double quarterDegInRads = Math.PI / 720; // edit
	
	public void rotatePoint(SizePoint point) {
		for (int a1 = 0; a1 < dimens; a1++) {
			for (int a2 = 0; a2 < dimens; a2++) {
				if (a1 != a2 && rotationSpeed[a1][a2] != 0) {
					point.rotateAround(Point.ORIGIN, rotationSpeed[a1][a2] * quarterDegInRads, a1, a2);
				}
			}
		}
	}
	
	@Override
	public void keyPressed() {
		
		if (Character.isDigit(key) && Character.getNumericValue(key) < dimens) {
			if (!rotaxis1Assigned) {
				rotaxis1 = Character.getNumericValue(key);
				rotaxis1Assigned = true;
			} else {
				rotaxis2 = Character.getNumericValue(key);
				if (rotaxis1 != rotaxis2) {
					rotationSpeed[rotaxis1][rotaxis2]++;
				}
				rotaxis1Assigned = false;
			}
		}
		
		if (key == '=') {
			pov.set(Z, pov.get(Z) - 10);		
		}
		if (key == '-') {
			pov.set(Z, pov.get(Z) + 10);
		}
		
		// TODO polar coordinate system
		if (key == CODED) {
			if (keyCode == UP) {
				pov.set(Y, pov.get(Y) - 10);
			}
			if (keyCode == DOWN) {
				pov.set(Y, pov.get(Y) + 10);
			}
			if (keyCode == LEFT) {
				pov.set(X, pov.get(X) - 10);
			}
			if (keyCode == RIGHT) {
				pov.set(X, pov.get(X) + 10);
			}
		}
		
		if (key == 'i') {
			dispInfo = !dispInfo;
		}
		
	}
	
	public SizePoint to2D(int dimens, SizePoint a) {
		
		if (dimens >= 5) {
			
			a = to2D(4, a.enforce(4));
			
		} else if (dimens == 4) {
			
//			a = to2D(3, a.enforce(3));
			
			a = to2D(3, a.isometric4Dto3D());
			
		} else if (dimens == 3) {
			
//			a = a.flattened(2);
			
//			a = a.isometric3Dto2D();
					
			a = a.projectedOntoEquation(pov, screen3D).enforce(2);
			
		}
		
		return a;
		
	}
	
	public void plotLine(SizePoint a, SizePoint b) {
		a = to2D(dimens, a);
		b = to2D(dimens, b);
		float x1 = (float) a.get(Point.X);
		float y1 = (float) a.get(Point.Y);
		float x2 = (float) b.get(Point.X);
		float y2 = (float) b.get(Point.Y);
		line(x1, y1, x2, y2);
	}
	
	public void dispInfo() {
		stroke(255, 180, 0); // gold
		for (SizePoint aP : axisPoints) {
			plotLine(aP, SizePoint.ORIGIN);
		}
		fill(255, 180, 0);
		text(infoString(), (width / -2) + 2, (height / -2) + 12);
		fill(0);
		noStroke();
	}
	
	public String infoString() {
		StringBuilder sb = new StringBuilder();
		
		// POV String
		sb.append("POV: ");
		sb.append(pov);
		
		// screen String
		sb.append("\n\nscreen:");
		sb.append(screen3D);
		
		// rotationSpeed String
		sb.append("\n\nrotationSpeed:\n_");
		for (int c = 0; c < dimens; c++) {
			sb.append("_");
			sb.append(c);
		}
		for (int r = 0; r < dimens; r++) {
			sb.append("\n");
			sb.append(r);
			for (int c = 0; c < dimens; c++) {
				if (r != c) {
					sb.append("_");
					sb.append(rotationSpeed[r][c]);
				} else {
					sb.append("__");
				}
			}
		}
		
		// rotaxes String
		sb.append("\n\nrotaxes: ");
		if (rotaxis1Assigned || dispRotaxes) {
			dispRotaxes = true;
			sb.append(rotaxis1);
			if (!rotaxis1Assigned) {
				sb.append(", ");
				sb.append(rotaxis2);
			}
		}
		
		return sb.toString();
	}

}
