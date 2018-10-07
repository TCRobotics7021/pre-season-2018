package frc.team7021.calfs.geometry;

public class Point2d {
    private final double x;
    private final double y;

    Point2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point2d add(Vector2d vector) {
        return new Point2d(x + vector.getX(), y + vector.getY());
    }
}
