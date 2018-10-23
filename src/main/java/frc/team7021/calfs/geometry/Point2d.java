package frc.team7021.calfs.geometry;

import com.google.gson.Gson;

public class Point2d {
    public final double x;
    public final double y;
    public final double theta;

    public Point2d(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.theta = angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point2d add(Vector2d vector, double angle) {
        return new Point2d(x + vector.getX(), y + vector.getY(), angle + this.theta);
    }

    public String toJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
