package frc.team7021.robot.sensors;

import frc.team7021.calfs.VirtualSensor;
import frc.team7021.calfs.exceptions.BadHealthError;
import frc.team7021.calfs.geometry.Point2d;
import frc.team7021.calfs.geometry.Vector2d;
import frc.team7021.robot.RobotConfig;

public class Position extends VirtualSensor {
    private Point2d mPosition;

    private boolean mEncoderLeftGood = false;
    private boolean mEncoderRightGood = false;

    private boolean mGyroGood = false;

    /**
     * @return Value of the left encoder
     */
    private double getLeftEncoder() {
        return 1;
    }

    /**
     * @return Value of the right encoder
     */
    private double getRightEncoder() {
        return 1;
    }

    /**
     * @return Current gyro reading
     */
    private double getGyro() { return 1; }

    /**
     * Calculate the turn radius from the encoders
     *
     * @param left The left encoder value
     * @param right The right encoder value
     * @param theta Angle of the turn
     * @return Turn radius
     */
    private double turnRadiusFromEnc(double left, double right, double theta) {
        if (right < left) {
            return right / theta;
        } else {
            return left / theta;
        }
    }

    /**
     * Get the displacement vector from the radius of the turn and the angle of the turn
     *
     * @param radius The length of the turn radius
     * @param theta The angle of the turn
     * @return Displacement vector
     */
    private Vector2d vecFromRadiusTheta(double radius, double theta) {
        return new Vector2d(radius - radius * Math.cos(theta), radius * Math.sin(theta));
    }

    /**
     * Use both encoders to determine the robot displacement vector
     *
     * @return Robot displacement vector from both encoders
     */
    private Vector2d vecFromEnc() {
        double distLeft = getLeftEncoder();
        double distRight = getRightEncoder();
        if (distLeft == distRight) {
            return new Vector2d(0, distLeft);
        }
        double theta = (distLeft - distRight) / RobotConfig.getInstance().WIDTH;
        double radius = turnRadiusFromEnc(distLeft, distRight, theta);

        return vecFromRadiusTheta(radius, theta);
    }

    /**
     * Determine if this is a right turn
     *
     * @param theta The turning angle
     * @return True if the angle is a right turn
     */
    private boolean isRightTurn(double theta) {
        return theta > 0;
    }

    /**
     * Get the displacement vector from the left encoder and the gyro
     *
     * @return Robot displacement vector
     */
    private Vector2d vecFromEncLeftGyro() {
        double theta = getGyro();
        double dispLeft = getLeftEncoder();

        if (theta == 0) {
            return new Vector2d(0, dispLeft);
        }

        double radius = dispLeft / theta;
        if (isRightTurn(theta)) {
            radius -= RobotConfig.getInstance().WIDTH;
        }

        return vecFromRadiusTheta(radius, theta);
    }

    /**
     * Get the displacement vector from the right encoder and the gyro
     *
     * @return Robot displacement vector
     */
    private Vector2d vecFromEncRightGyro() {
        double theta = getGyro();
        double dispRight = getRightEncoder();

        if (theta == 0) {
            return new Vector2d(0, dispRight);
        }

        double radius = dispRight / theta;
        if (!isRightTurn(theta)) {
            radius -= RobotConfig.getInstance().WIDTH;
        }

        return vecFromRadiusTheta(radius, theta);
    }

    /**
     * Get the displacement of the robot
     *
     * @return The displacement vector
     * @throws BadHealthError
     */
    private Vector2d getDisplacement() throws BadHealthError {
        // Common case (left/right encoder)
        if (mEncoderLeftGood && mEncoderRightGood) {
            return vecFromEnc();
        } else if (mEncoderLeftGood && mGyroGood) {
            return vecFromEncLeftGyro();
        } else if (mEncoderRightGood && mGyroGood) {
            return vecFromEncRightGyro();
        }
        throw new BadHealthError();
    }

    /**
     * Update the virtual sensor
     *
     * @throws BadHealthError if unable to determine the displacement
     */
    @Override
    public void update() throws BadHealthError {
        mPosition = mPosition.add(getDisplacement());
    }
}
