package main.java.chalmers.dat055.linear;

public class LinearAlgebra {

    public static boolean eq(Col2d u, Col2d v) {
        return u.x == v.x && u.y == v.y;
    }

    public static boolean eq(Mat2x2d a, Mat2x2d b) {
        return a.r1c1 == b.r1c1 && a.r2c1 == b.r2c1 && a.r1c2 == b.r1c2 && a.r2c2 == b.r2c2;
    }

    public static Col2d add(Col2d u, Col2d v) {
        return new Col2d(u.x + v.x, u.y + v.y);
    }

    public static Col2d mul(Col2d u, Col2d v) {
        return new Col2d(u.x * v.x, u.y * v.y);
    }

    public static Mat2x2d add(Mat2x2d a, Mat2x2d b) {
        return new Mat2x2d(
                a.r1c1 + b.r1c1, a.r2c1 + b.r2c1,
                a.r1c2 + b.r1c2, a.r2c2 + b.r2c2);
    }

    public static Mat2x2d mul(double s, Mat2x2d a) {
        return new Mat2x2d(s * a.r1c1, s * a.r2c1, s * a.r1c2, s * a.r2c2);
    }

    public static Col2d mul(Mat2x2d a, Col2d u) {
        return new Col2d(u.x * a.r1c1 + u.y * a.r1c2, u.x * a.r2c1 + u.y * a.r2c2);
    }

    public static Mat2x2d mul(Mat2x2d a, Mat2x2d b) {
        return new Mat2x2d(
                a.r1c1 * b.r1c1 + a.r1c2 * b.r2c1,
                a.r2c1 * b.r1c1 + a.r2c2 * b.r2c1,
                a.r1c1 * b.r1c2 + a.r1c2 * b.r2c2,
                a.r2c1 * b.r1c2 + a.r2c2 * b.r2c2);
    }
}
