package frc.team7021.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team7021.calfs.CalfRobotManager;
import frc.team7021.calfs.planners.Planner;
import frc.team7021.calfs.planners.TrajectoryPlanner;
import frc.team7021.robot.subsystems.Drive;

public class Robot extends TimedRobot {
    CalfRobotManager mRobotManager;

    Drive mDrive = new Drive();

    Robot() {
        mRobotManager = new CalfRobotManager();

        mRobotManager.addSubsystem(mDrive);
    }

    /**
     * Get the current state of the robot
     *
     * @return Current robot state
     */
    private RobotState getState() {
        return RobotState.getState();
    }

    @Override
    public void autonomousInit() {
        Planner[] planners = {
                new TrajectoryPlanner(mDrive, new BasicPath())
        };

        mRobotManager.startTeleop(planners);
    }

    @Override
    public void teleopInit() {
        Planner[] planners = {
                new TeleopPlanner(this)
        };

        mRobotManager.startTeleop(planners);
    }

    @Override
    public void disabledInit() {
//        mRobotManager.startDisabled();
    }

    @Override
    public void autonomousPeriodic() {
        RobotState.loadCurrentState();

        mRobotManager.stepAuto();

        RobotState.log();
    }

    @Override
    public void teleopPeriodic() {
        RobotState.loadCurrentState();

        mRobotManager.stepTeleop();

        RobotState.log();
    }

    @Override
    public void disabledPeriodic() {
//        mRobotManager.stepDisabled();
    }
}
