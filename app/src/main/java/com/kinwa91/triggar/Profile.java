package com.kinwa91.triggar;

import android.content.Context;
import android.os.Message;

import java.util.ArrayList;

/**
 * Created by kinwa91 on 2014-09-15.
 */
public class Profile {


    private ArrayList<Trigger> triggers;
    private ArrayList<Action> actions;

    private boolean isEnabled = false;
    private boolean isTriggered = false;


    public Profile(ArrayList<Trigger> triggers, ArrayList<Action> actions) {
        this.triggers = triggers;
        this.actions = actions;
    }

    private void executeActions() {
        for (Action a : actions) {
            a.execute();
        }
    }

    public void addTrigger(Trigger t) {
        triggers.add(t);
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void trigger(Message msg) {
        for (Trigger t : triggers) {
            if (t.getId() == msg.arg1)// && t.getState() == msg.arg2)
               executeActions();
        }
    }

}
