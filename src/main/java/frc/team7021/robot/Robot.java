package frc.team7021.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team7021.calfs.CalfRobotManager;
import frc.team7021.calfs.planners.TrajectoryPlanner;
import frc.team7021.robot.subsystems.Drive;

public class Robot extends TimedRobot {
    CalfRobotManager mRobotManager;

    Drive mDrive = new Drive();

    public Robot() {
        mRobotManager = new CalfRobotManager();

        mRobotManager.addSubsystem(mDrive);
    }

    @Override
    public void robotInit() {
        teleopInit();
    }

    @Override
    public void robotPeriodic() {
        teleopPeriodic();
    }

    @Override
    public void autonomousInit() {
        mRobotManager.startTeleop(new TrajectoryPlanner(mDrive, new BasicPath()));
    }

    @Override
    public void teleopInit() {
        mRobotManager.startTeleop(new TeleopPlanner(this));
    }

    @Override
    public void disabledInit() {
        mRobotManager.startDisabled();
    }

    @Override
    public void autonomousPeriodic() {
        mRobotManager.stepAuto();
    }

    @Override
    public void teleopPeriodic() {
        mRobotManager.stepTeleop();
    }

    @Override
    public void disabledPeriodic() {
        mRobotManager.stepDisabled();
    }
}
