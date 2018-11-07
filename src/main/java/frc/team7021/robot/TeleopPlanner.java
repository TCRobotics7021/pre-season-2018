package frc.team7021.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.team7021.calfs.planners.Planner;

public class TeleopPlanner extends Planner {
    Robot mRobot;

    Joystick mController = new Joystick(0);

    public TeleopPlanner(Robot robot) {
        mRobot = robot;
    }

    @Override
    public void start() {

    }

    @Override
    public void step() {
        double speedFac = 0.7;

        double leftSpeed = mController.getRawAxis(1) * speedFac;
        double rightSpeed = mController.getRawAxis(3) * speedFac;

        mRobot.mDrive.setSpeed(leftSpeed, rightSpeed, true);
    }

    @Override
    public void stop() {

    }
}
