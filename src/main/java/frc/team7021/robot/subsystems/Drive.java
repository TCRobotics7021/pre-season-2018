package frc.team7021.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.google.gson.Gson;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.team7021.calfs.exceptions.BadHealthError;
import frc.team7021.calfs.geometry.Point2d;
import frc.team7021.calfs.subsystems.Subsystem;
import frc.team7021.calfs.subsystems.TankDriveSubsystem;
import frc.team7021.robot.sensors.Position;

public class Drive extends Subsystem implements TankDriveSubsystem {
    private TalonSRX motor1 = new TalonSRX(1);
    private TalonSRX motor2 = new TalonSRX(4);

    private TalonSRX motor3 = new TalonSRX(2);
    private TalonSRX motor4 = new TalonSRX(3);

    private Position mPosition = new Position();

    private class PeriodicIO {
        // Inputs
        public int leftEncoder;
        public int rightEncoder;
        public Point2d position;

        // Outputs
        public double leftSpeed;
        public double rightSpeed;
    }

    @Override
    public void writeOutputs() {
        motor1.set(ControlMode.PercentOutput, -io.leftSpeed);
        motor2.set(ControlMode.PercentOutput, -io.leftSpeed);

        motor3.set(ControlMode.PercentOutput, io.rightSpeed);
        motor4.set(ControlMode.PercentOutput, io.rightSpeed);
    }

    public void readInputs() {
        io.leftEncoder = motor2.getSelectedSensorPosition(0);
        io.rightEncoder = motor3.getSelectedSensorPosition(0);

        try {
            mPosition.update(io.leftEncoder, io.rightEncoder, 0);
            io.position = mPosition.getPosition();
            System.out.println(io.position.toJson());
        } catch (BadHealthError e) {
            System.out.println("Can't read position");
        }


    }

    private PeriodicIO io = new PeriodicIO();

    public void setSpeed(double left, double right) {
        left = clipSpeed(left);
        right = clipSpeed(right);

        io.leftSpeed = left;
        io.rightSpeed = right;
    }

    @Override
    public int getLeftEncoder() {
        return io.leftEncoder;
    }

    @Override
    public int getRightEncoder() {
        return io.rightEncoder;
    }

    private double clipSpeed(double speed) {
        return Math.max(0, Math.min(1, speed));
    }

    @Override
    public String toJson() {
        return new Gson().toJson(io);
    }
}
