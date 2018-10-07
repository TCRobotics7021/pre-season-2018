package frc.team7021.calfs;

public interface ITrajectory {
    void update(int leftEnc, int rightEnc);

    void configure();

    double getLeftSpeed();

    double getRightSpeed();
}
