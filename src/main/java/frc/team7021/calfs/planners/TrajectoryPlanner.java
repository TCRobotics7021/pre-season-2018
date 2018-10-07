package frc.team7021.calfs.planners;

import frc.team7021.calfs.ITrajectory;
import frc.team7021.calfs.subsystems.TankDriveSubsystem;

public class TrajectoryPlanner extends Planner {
    TankDriveSubsystem mTankDriveSubsystem;
    ITrajectory mTrajectory;

    public TrajectoryPlanner(
            TankDriveSubsystem tankDriveSubsystem,
            ITrajectory trajectory) {
        mTankDriveSubsystem = tankDriveSubsystem;
        mTrajectory = trajectory;
    }

    @Override
    public void start() {
        mTrajectory.configure();
    }

    @Override
    public void step() {
        mTrajectory.update(mTankDriveSubsystem.getLeftEncoder(), mTankDriveSubsystem.getRightEncoder());

        mTankDriveSubsystem.setSpeed(mTrajectory.getLeftSpeed(), mTrajectory.getRightSpeed());
    }
}
