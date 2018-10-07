package frc.team7021.calfs.subsystems;

public interface TankDriveSubsystem {
    void setSpeed(double left, double right);

    int getLeftEncoder();

    int getRightEncoder();
}
