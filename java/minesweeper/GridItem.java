package mpc.sweep;

// import java.util.ArrayList;
// import java.util.List;

import mpc.math.Point;
import processing.core.PApplet;

// TODO comment this class

public class GridItem {
	
    private GridItem[][] items;

    protected Point[] points;

    public static final int NW = 0, NE = 1, SE = 2, SW = 3;

    public GridItem() {
        items = null;
        points = null;
    }

    public GridItem(Point nw, Point ne, Point se, Point sw) {
        items = null;
        // Point[] points = {nw, ne, se, sw};
        // updatePoints(points);
    }

    public GridItem(GridItem[][] items) {
        this.items = items;
        points = null;
    }

    public int rows() {
        return items.length;
    }

    public int columns() {
        return items[0].length;
    }

    public Point corner(int i) {
        return points[i];
    }

    public void makeScreen(byte[][][] template) {
        items = new GridItem[template[0].length][template.length];
        for (int r = 0; r < template.length; r++) {
            for (int c = 0; c < template[r].length; c++) {
                items[r][c] = new Pixel(template[r][c]);
            }
        }
    }

    /* FIXME
    public void updatePoints(Point[] points) {
        this.points = points;
        List<Point> west = new ArrayList<>();
        Point.addStrandOfPointsToList(points[NW], points[SW], rows()-1, west);
        List<Point> east = new ArrayList<>();
        Point.addStrandOfPointsToList(points[NE], points[SE], rows()-1, east);
        List<Point>[] intersectPoints = new ArrayList[rows()+1];
        for (int r = 0; r < intersectPoints.length; r++) {
             Point.addStrandOfPointsToList(west.get(r), east.get(r), columns()-1, intersectPoints[r]);
        }
        for (int r = 0; r < rows(); r++) {
            for (int c = 0; c < columns(); c++) {
                Point[] smlPoints = new Point[4];
                smlPoints[NW] = intersectPoints[r].get(c);
                smlPoints[NE] = intersectPoints[r].get(c+1);
                smlPoints[SE] = intersectPoints[r+1].get(c+1);
                smlPoints[SW] = intersectPoints[r+1].get(c);
                items[r][c].updatePoints(smlPoints);
            }
        }
    }*/

    public void draw(PApplet p) {
        for (GridItem[] row : items) {
            for (GridItem item : row) {
                item.draw(p);
            }
        }
    }

}
