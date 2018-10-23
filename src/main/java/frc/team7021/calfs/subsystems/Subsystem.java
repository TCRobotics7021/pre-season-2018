package frc.team7021.calfs.subsystems;

import frc.team7021.calfs.exceptions.BadHealthError;

abstract public class Subsystem {
    private boolean mOk = true;

    /**
     * Convert the motor outputs to a json representation
     *
     * @return Json string of the motor outputs
     */
    abstract public String toJson();

    /**
     * Read sensor inputs
     */
    abstract public void readInputs();


    /**
     * Write the outputs to the motors
     */
    abstract public void writeOutputs();


    public boolean isOk() {
        return mOk;
    }
}
