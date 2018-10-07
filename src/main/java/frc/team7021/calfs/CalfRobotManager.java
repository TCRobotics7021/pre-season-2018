package frc.team7021.calfs;

import frc.team7021.calfs.planners.Planner;
import frc.team7021.calfs.subsystems.Subsystem;

import java.util.List;

public class CalfRobotManager {
    private IState mState;

    private List<Subsystem> mSubsystems;

    private Planner[] mAutoPlanners;
    private Planner[] mTeleopPlanners;

    private enum Mode {
        AUTO,
        TELEOP,
        DISABLED
    }

    private Mode mMode;

    public CalfRobotManager() {
    }

    public void addSubsystem(Subsystem subsystem) {
        mSubsystems.add(subsystem);
    }

    private void setMode(Mode mode) {
        mMode = mode;
    }

    private void updateSubsystems() {
        for (Subsystem subsystem : mSubsystems) {
            subsystem.writeOutputs();
        }
    }

    public void startAuto(Planner[] planners) {
        stopDisabled();
        stopTeleop();
        mAutoPlanners = planners;

        for (Planner planner : planners) {
            planner.start();
        }
    }

    public void startTeleop(Planner[] planners) {
        stopAuto();
        stopDisabled();

        mTeleopPlanners = planners;

        for (Planner planner : planners) {
            planner.start();
        }
    }

    public void startDisabled(Planner[] planner) {
        stopAuto();
        stopTeleop();
    }

    public void stepAuto() {
        for (Planner planner : mAutoPlanners) {
            planner.step();
        }

        updateSubsystems();
    }

    public void stepTeleop() {
        for (Planner planner : mTeleopPlanners) {
            planner.step();
        }

        updateSubsystems();
    }

    public void stepDisabled() {
    }

    public void stopAuto() {
        for (Planner planner : mAutoPlanners) {
            planner.stop();
        }
    }

    public void stopTeleop() {
        for (Planner planner : mTeleopPlanners) {
            planner.stop();
        }
    }

    public void stopDisabled() {

    }
}
