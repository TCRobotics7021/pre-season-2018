package frc.team7021.calfs;

import frc.team7021.calfs.planners.Planner;
import frc.team7021.calfs.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.List;

public class CalfRobotManager {
    private IState mState;

    private List<Subsystem> mSubsystems;

    private Planner mAutoPlanner;
    private Planner mTeleopPlanner;

    private enum Mode {
        AUTO,
        TELEOP,
        DISABLED
    }

    private Mode mMode;

    public CalfRobotManager() {
        mSubsystems = new ArrayList<>();
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

    public void startAuto(Planner planner) {
        stopDisabled();
        stopTeleop();

        mAutoPlanner = planner;

        mAutoPlanner.start();
    }

    public void startTeleop(Planner planner) {
        stopAuto();
        stopDisabled();

        mTeleopPlanner = planner;

        mTeleopPlanner.start();
    }

    public void startDisabled(Planner[] planner) {
        stopAuto();
        stopTeleop();
    }

    public void stepAuto() {
        mAutoPlanner.step();

        updateSubsystems();
    }

    public void stepTeleop() {
        mTeleopPlanner.step();

        updateSubsystems();
    }

    public void stepDisabled() {
    }

    public void stopAuto() {
        if (mAutoPlanner != null) {
            mAutoPlanner.stop();
        }
    }

    public void stopTeleop() {
        if (mTeleopPlanner != null) {
            mTeleopPlanner.stop();
        }
    }

    public void stopDisabled() {

    }
}
