package frc.team7021.robot;

import frc.team7021.calfs.CalfTrajectory;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class BasicPath extends CalfTrajectory {
    double P = 0.1;
    double I = 0;
    double D = 0;

    // Robot Properties
    int encoderPPRev = 1400;
    double wheelDiameter = 0.174625;

    // This isn't correct, but it seems to work for now. Probably the maxVelocities are off.
    double width = 2 * 0.50165;

    // Max possible motor speed in encoder ticks per second
    double maxVelocityLeftEnc = 8000;
    double maxVelocityRightEnc = 8000;

    // Used to scale the output
    double maxVelocityLeft = maxVelocityLeftEnc * wheelDiameter * Math.PI / encoderPPRev;
    double maxVelocityRight = maxVelocityRightEnc * wheelDiameter * Math.PI / encoderPPRev;
    double maxAccelLeft = 60;
    double maxAccelRight = 60;

    // These values are used to generate the trajectory
    double dt = 1.0 / 50.0;
    double maxTravelVelocity = 0.9;
    double maxAccel = 30;
    double maxJerk = 60;

    public void configure() {
        // Specify path
        Waypoint[] points = new Waypoint[]{
                new Waypoint(0, 1, 0),

                new Waypoint(4, 2, Pathfinder.d2r(90)),
        };


        // Configure trajectory
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_FAST, dt, maxTravelVelocity, maxAccel, maxJerk);

        Trajectory trajectory = Pathfinder.generate(points, config);

        TankModifier modifier = new TankModifier(trajectory).modify(width);

        EncoderFollower left = new EncoderFollower(modifier.getLeftTrajectory());
        EncoderFollower right = new EncoderFollower(modifier.getRightTrajectory());

        // Configure encoder followers
        left.configureEncoder(0, encoderPPRev, wheelDiameter);
        left.configurePIDVA(P, I, D, 1 / maxVelocityLeft, 1 / maxAccelLeft);

        right.configureEncoder(0, encoderPPRev, wheelDiameter);
        right.configurePIDVA(P, I, D, 1 / maxVelocityRight, 1 / maxAccelRight);

        configureFollowers(left, right);
    }
}
