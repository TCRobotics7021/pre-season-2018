package frc.team7021.robot;

import com.google.gson.Gson;
import frc.team7021.calfs.IState;

public class RobotState implements IState {
    private static RobotState sCurrent;

    public int leftEncoder;
    public int rightEncoder;
    public double gyro;

    /**
     * Load the current robot state from the
     */
    public static void loadCurrentState() {
        sCurrent = new RobotState();

        sCurrent.leftEncoder = 10;
        sCurrent.rightEncoder = 20;
        sCurrent.gyro = 5;
    }

    /**
     * Log the current state to a file
     */
    public static void log() {
        // For now do nothing...
    }

    /**
     * Get the current robot state
     *
     * @return Current robot state
     */
    public static RobotState getState() {
        return sCurrent;
    }

    /**
     * Serialize this object to
     * @return The json representation of this robot state
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
