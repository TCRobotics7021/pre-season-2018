package frc.team7021.robot.subsystems;

import com.google.gson.Gson;
import frc.team7021.calfs.subsystems.Subsystem;
import frc.team7021.calfs.subsystems.TankDriveSubsystem;

public class Drive extends Subsystem implements TankDriveSubsystem {
    private State mState = new State();

    private class State {
        public double leftSpeed = 0;
        public double rightSpeed = 0;
    }

    public void setSpeed(double left, double right) {
        left = clipSpeed(left);
        right = clipSpeed(right);

        mState.leftSpeed = left;
        mState.rightSpeed = right;
    }

    @Override
    public int getLeftEncoder() {
        return 0;
    }

    @Override
    public int getRightEncoder() {
        return 0;
    }

    private double clipSpeed(double speed) {
        return Math.max(0, Math.min(1, speed));
    }

    @Override
    public void writeOutputs() {
        // TODO: Send values to motors
    }

    @Override
    public String toJson() {
        return new Gson().toJson(mState);
    }
}
