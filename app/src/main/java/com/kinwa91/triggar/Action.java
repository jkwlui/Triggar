package com.kinwa91.triggar;

import android.content.Context;

/**
 * Created by kinwa91 on 2014-09-15.
 */
public abstract class Action {

    public abstract void execute();

    protected Context context;

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
