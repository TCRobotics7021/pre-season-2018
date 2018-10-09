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

    /**
     * Get the current state of the robot
     *
     * @return Current robot state
     */
    private RobotInput getState() {
        return RobotInput.getState();
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
//        mRobotManager.startDisabled();
    }

    @Override
    public void autonomousPeriodic() {
        RobotInput.loadCurrentState();

        mRobotManager.stepAuto();

        RobotInput.log();
    }

    @Override
    public void teleopPeriodic() {
        RobotInput.loadCurrentState();

        mRobotManager.stepTeleop();

        RobotInput.log();
    }

    @Override
    public void disabledPeriodic() {
//        mRobotManager.stepDisabled();
    }
}
