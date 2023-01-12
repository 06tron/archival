package mpc.sweep;

import mpc.math.Point;
import processing.core.PApplet;

public class Pixel extends GridItem {

    private byte[] argb;

    public static final int A = 0, R = 1, G = 2, B = 3;

    public Pixel(byte[] argb) {
        super();
        this.argb = argb;
    }

    public void updatePoints(Point[] points) {
        this.points = points;
    }

    public void draw(PApplet p) {
        if (argb[A] != -128) {
            p.noStroke();
            p.fill(argb[R] + 128, argb[G] + 128, argb[B] + 128, argb[A] + 128);
            p.quad(
                    (float) points[NW].get(Point.X), (float) points[NW].get(Point.Y),
                    (float) points[NE].get(Point.X), (float) points[NE].get(Point.Y),
                    (float) points[SE].get(Point.X), (float) points[SE].get(Point.Y),
                    (float) points[SW].get(Point.X), (float) points[SW].get(Point.Y)
            );
        }
    }

}
