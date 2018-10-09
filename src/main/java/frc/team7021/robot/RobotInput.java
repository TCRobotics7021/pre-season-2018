package frc.team7021.robot;

import com.google.gson.Gson;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.team7021.calfs.IState;

public class RobotInput implements IState {
    private static RobotInput sCurrent;

    public int leftEncoder;
    public int rightEncoder;
    public double gyro;
    public boolean test;

    private static DigitalInput sInput = new DigitalInput(0);

    /**
     * Load the current robot state from the
     */
    public static void loadCurrentState() {
        sCurrent = new RobotInput();

        sCurrent.leftEncoder = 10;
        sCurrent.rightEncoder = 20;
        sCurrent.gyro = 5;
        sCurrent.test = sInput.get();
    }

    /**
     * Log the current state to a file
     */
    public static void log() {
        System.out.println(sCurrent.toJson());
    }

    /**
     * Get the current robot state
     *
     * @return Current robot state
     */
    public static RobotInput getState() {
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
