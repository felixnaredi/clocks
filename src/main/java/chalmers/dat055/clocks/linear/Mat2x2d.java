package main.java.chalmers.dat055.linear;

/**
 * Two by two matrix represented with doubles.
 *
 * <p>Going by math notation, rows first then columns and (oh the shame) begin the count on one.
 */
public class Mat2x2d {
    double r1c1;
    double r2c1;
    double r1c2;
    double r2c2;

    /**
     * Initializes a 2x2 matrix.
     *
     * @param r1c1 Row 1, Column 1
     * @param r2c1 Row 2, Column 1
     * @param r1c2 Row 1, Column 2
     * @param r2c2 Row 2, Column 2
     */
    public Mat2x2d(double r1c1, double r2c1, double r1c2, double r2c2) {
        this.r1c1 = r1c1;
        this.r2c1 = r2c1;
        this.r1c2 = r1c2;
        this.r2c2 = r2c2;
    }

    /**
     * Makes a new 2x2 identity matrix.
     *
     * @return The identity matrix.
     */
    public static Mat2x2d id() {
        return new Mat2x2d(1, 0, 0, 1);
    }
}
