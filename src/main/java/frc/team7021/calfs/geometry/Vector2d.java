package frc.team7021.calfs.geometry;

public class Vector2d {
    private final double x;
    private final double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d rotate(double angle) {
        double sn = Math.sin(angle);
        double cs = Math.cos(angle);

        return new Vector2d(x * cs - y * sn, x * sn + y * cs);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
