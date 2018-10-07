package frc.team7021.robot;

import frc.team7021.calfs.planners.Planner;

public class AutoPlanner extends Planner {
    private Robot mRobot;

    public AutoPlanner(Robot robot) {
        mRobot = robot;
    }
}
