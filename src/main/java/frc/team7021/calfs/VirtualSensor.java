package frc.team7021.calfs;

import frc.team7021.calfs.exceptions.BadHealthError;

abstract public class VirtualSensor<T extends CalfConfig> {
    private boolean mIsOk = true;
    private T mConfig;

    public VirtualSensor(T config) {
        mConfig = config;
    }

    public VirtualSensor() {

    }

    public void setConfig(T config) {
        mConfig = config;
    }

    public T getConfig() {
        return mConfig;
    }

    /**
     * Check the health of this virtual sensor
     *
     * @return
     */
    public boolean isOk() {
        return mIsOk;
    }

    public void updateValues() {
        try {
            update();
            mIsOk = true;
        } catch (BadHealthError e) {
            mIsOk = false;
            // TODO:
            return;
        }

    }

    abstract public void update() throws BadHealthError;
}
