package frc.team7021.robot;

public class RobotConfig {
    private static RobotConfig ourInstance = new RobotConfig();

    public static RobotConfig getInstance() {
        return ourInstance;
    }

    private RobotConfig() {
    }


    // Robot config
    public final double WIDTH = 0.5;
}
