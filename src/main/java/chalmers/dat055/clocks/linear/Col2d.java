package main.java.chalmers.dat055.linear;

/**
 * Two valued column vector (2x1 matrix) using {@code doubles}.
 */
public class Col2d {
    public double x;
    public double y;

    public Col2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Col2d() {
        this(0, 0);
    }
}
