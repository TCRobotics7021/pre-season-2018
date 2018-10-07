package frc.team7021.robot.sensors;

import frc.team7021.calfs.VirtualSensor;
import frc.team7021.calfs.exceptions.BadHealthError;

public class Angle extends VirtualSensor {
    private double mAngle = 0;

    private boolean isGyroConnected() {
        return false;
    }

    @Override
    public void update() throws BadHealthError {
        if (!isGyroConnected()) {
            throw new BadHealthError();
        }
        mAngle = mAngle + 1;
    }

    public double getAngle() {
        return mAngle;
    }
}
