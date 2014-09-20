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
    private String name;
    private int state;
    private int id;

    private boolean isTriggered = false;


    public Profile(int id, String name, int state, ArrayList<Trigger> triggers, ArrayList<Action> actions) {
        this.id = id;
        this.name = name;
        this.state = state;
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

    public String getName() { return name; }

    public int getState() { return state; }

    public int getId() { return id; }

}
