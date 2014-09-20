package com.kinwa91.triggar;

import android.app.Notification;
import android.app.NotificationManager;
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
    private Context context;

    private boolean isTriggered = false;


    public Profile(int id, String name, int state, ArrayList<Trigger> triggers, ArrayList<Action> actions, Context context) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.triggers = triggers;
        this.actions = actions;
        this.context = context;
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
            if (t.getId() == msg.arg1 && t.getState() == msg.arg2)
               executeActions();
        }
    }


    public void trigger(int trigger, int state) {
        for (Trigger t : triggers) {
            if (t.getId() == trigger && t.getState() == state) {
                sendNotification();
                executeActions();
            }
        }
    }

    private void sendNotification() {

        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_action_network_wifi)
                        .setContentTitle("Triggar Notification")
                        .setContentText("Profile: \"" + name + "\" is active");

        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.getNotification());
    }

    public String getName() { return name; }

    public int getState() { return state; }

    public int getId() { return id; }

}
