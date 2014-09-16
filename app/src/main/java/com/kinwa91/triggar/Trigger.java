package com.kinwa91.triggar;

/**
 * Created by kinwa91 on 2014-09-15.
 */
public abstract class Trigger {

    private boolean triggered;
    protected int state;

    public boolean isTriggered() {
        return triggered;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public abstract int getId();
}
