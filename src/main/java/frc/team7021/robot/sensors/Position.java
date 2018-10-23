package frc.team7021.robot.sensors;

//import frc.team7021.calfs.VirtualSensor;
import frc.team7021.calfs.exceptions.BadHealthError;
import frc.team7021.calfs.geometry.Point2d;
import frc.team7021.calfs.geometry.Vector2d;
import frc.team7021.robot.RobotConfig;

public class Position {
    private Point2d mPosition = new Point2d(0, 0, 0);
    private double mAngle = 0;

    private boolean mEncoderLeftGood = true;
    private boolean mEncoderRightGood = true;

    private boolean mGyroGood = true;

    private class PeriodicIO {
        int left = 0;
        int right = 0;
        int leftDelta = 0;
        int rightDelta = 0;
        double angle = 0;
    }
    private PeriodicIO io = new PeriodicIO();

    /**
     * @return Value of the left encoder
     */
    private double getLeftEncoder() {
        return encToMeter(io.leftDelta);
    }

    /**
     * @return Value of the right encoder
     */
    private double getRightEncoder() {
        return encToMeter(io.rightDelta);
    }

    /**
     * Convert encoder ticks to meters traveled
     */
    private double encToMeter(int ticks) {
        return ticks * (RobotConfig.getInstance().wheelDiameter * Math.PI / RobotConfig.getInstance().encoderPPRev);
    }

    /**
     * @return Current gyro reading
     */
    private double getGyro() {
        return io.angle;
    }

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
        // TODO: Should this be radius subtracted
        double deltaX = radius - radius * Math.cos(theta);

        double deltaY = radius * Math.sin(theta);

        Vector2d vec =  new Vector2d(deltaX, deltaY).rotate(mPosition.theta);

        mPosition = mPosition.add(vec, theta);

        return vec;
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
            return new Vector2d(0, distLeft).rotate(mAngle);
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
    private Vector2d updateDisplacement() throws BadHealthError {
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

//    /**
//     * Update the virtual sensor
//     *
//     * @throws BadHealthError if unable to determine the displacement
//     */
//    @Override
//    public void update() throws BadHealthError {
//        mPosition = mPosition.add(updateDisplacement());
//    }
//
    public void update(int left, int right, double angle) throws BadHealthError {

        io.leftDelta = left - io.left;
        io.rightDelta = right - io.right;
        io.left = left;
        io.right = right;
        io.angle = angle - io.angle;

        updateDisplacement();
    }

    public Point2d getPosition() {
        return mPosition;
    }
}
