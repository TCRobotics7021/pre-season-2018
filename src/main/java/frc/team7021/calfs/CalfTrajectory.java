package frc.team7021.calfs;

import jaci.pathfinder.followers.EncoderFollower;


abstract public class CalfTrajectory implements ITrajectory {
    private double leftSpeed = 0;
    private double rightSpeed = 0;

    private EncoderFollower mFollowerLeft;
    private EncoderFollower mFollowerRight;

    public void configureFollowers(EncoderFollower followerLeft, EncoderFollower followerRight) {
        mFollowerLeft = followerLeft;
        mFollowerRight = followerRight;
    }

    public void update(int leftEnc, int rightEnc) {
        leftSpeed = mFollowerLeft.calculate(leftEnc);
        rightSpeed = mFollowerRight.calculate(rightEnc);
    }

    public double getLeftSpeed() {
        return leftSpeed;
    }

    public double getRightSpeed() {
        return rightSpeed;
    }
}
