package com.kinwa91.triggar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kinwa91.triggar.ProfileDb;
import com.kinwa91.triggar.actions.AlarmAction;
import com.kinwa91.triggar.actions.BluetoothAction;
import com.kinwa91.triggar.actions.BrightnessAction;
import com.kinwa91.triggar.actions.MusicAction;
import com.kinwa91.triggar.actions.VolumeAction;
import com.kinwa91.triggar.actions.WifiAction;
import com.kinwa91.triggar.triggers.AirplaneTrigger;
import com.kinwa91.triggar.triggers.BatteryTrigger;
import com.kinwa91.triggar.triggers.BluetoothTrigger;
import com.kinwa91.triggar.triggers.HeadsetTrigger;
import com.kinwa91.triggar.triggers.PowerTrigger;
import com.kinwa91.triggar.triggers.WifiTrigger;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;

/**
 * Created by EdNKuma on 17/09/2014.
 */
public class ProfileDbExchanger  {

    private ProfileDb profileDb;
    private SQLiteDatabase db;

    public ProfileDbExchanger(Context context) {
        this.profileDb = ProfileDb.getInstance(context);
    }

    public void open() throws SQLiteException {
        db = profileDb.getWritableDatabase();
    }

    public void close() {
        profileDb.close();
    }

    public int createProfile(String name, int state) {
        ContentValues values = new ContentValues();
        values.put(ProfileDb.PROFILE_NAME, name);
        values.put(ProfileDb.PROFILE_STATE, state);

        long profileId = db.insert(ProfileDb.PROFILE_TABLE_NAME, null, values);

        return (int) profileId;
    }

    public int createTrigger(String name, int state, int profileId) {
        ContentValues values = new ContentValues();
        values.put(ProfileDb.TRIGGER_NAME, name);
        values.put(ProfileDb.TRIGGER_STATE, state);
        values.put(ProfileDb.TRIGGER_PROFILE_ID, profileId);

        long triggerId = db.insert(ProfileDb.TRIGGER_TABLE_NAME, null, values);

        return (int) triggerId;
    }

    public int createAction(String name, int state, int profileId) {
        ContentValues values = new ContentValues();
        values.put(ProfileDb.ACTION_NAME, name);
        values.put(ProfileDb.ACTION_STATE, state);
        values.put(ProfileDb.ACTION_PROFILE_ID, profileId);

        long actionId = db.insert(ProfileDb.ACTION_TABLE_NAME, null, values);

        return (int) actionId;
    }

    public Profile getProfile(int profileId) {
        Cursor cursor = null;
        try {
            cursor = db.query(ProfileDb.PROFILE_TABLE_NAME, null,
                    ProfileDb.PROFILE_ID + " = " + Integer.toString(profileId),
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(ProfileDb.PROFILE_ID));
            String name = cursor.getString(cursor.getColumnIndex(ProfileDb.PROFILE_NAME));
            int state = cursor.getInt(cursor.getColumnIndex(ProfileDb.PROFILE_STATE));

            return new Profile(name, state, getAllTriggers(id), getAllActions(id));
        }
        else {
            return null;
        }

    }

    public ArrayList<Profile> getAllProfiles() {
        ArrayList<Profile> profiles = new ArrayList<Profile>();
        Cursor cursor = null;
        try {
            cursor = db.query(ProfileDb.PROFILE_TABLE_NAME, null,
                    null, null, null, null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ProfileDb.PROFILE_ID));
                String name = cursor.getString(cursor.getColumnIndex(ProfileDb.PROFILE_NAME));
                int state = cursor.getInt(cursor.getColumnIndex(ProfileDb.PROFILE_STATE));

                profiles.add(new Profile(name, state, getAllTriggers(id), getAllActions(id)));
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }

        cursor.close();

        for (Profile p : profiles) {
            if (p.getTriggers().size() != 0) {
                Log.d("Profile: ", "has trigger");
            }
        }

        return profiles;

    }

    public ArrayList<Trigger> getAllTriggers(int profileId) {
        ArrayList<Trigger> triggers = new ArrayList<Trigger>();
        Cursor cursor = null;

        try {
            cursor = db.query(ProfileDb.TRIGGER_TABLE_NAME, null,
                    ProfileDb.TRIGGER_PROFILE_ID + " = " + Integer.toString(profileId),
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ProfileDb.TRIGGER_ID));
                String name = cursor.getString(cursor.getColumnIndex(ProfileDb.TRIGGER_NAME));
                Log.d("ProfileDbExchanger", "Trigger names: " + name);
                int state = cursor.getInt(cursor.getColumnIndex(ProfileDb.TRIGGER_STATE));

                if (name.contains("Airplane")) {
                    AirplaneTrigger at = new AirplaneTrigger();
                    at.setState(state);
                    triggers.add(at);
                } else if (name.contains("Battery")) {
                    BatteryTrigger bt = new BatteryTrigger();
                    bt.setState(state);
                    triggers.add(bt);
                } else if (name.contains("Bluetooth")) {
                    BluetoothTrigger bt = new BluetoothTrigger();
                    bt.setState(state);
                    triggers.add(bt);
                } else if (name.contains("Headset")) {
                    HeadsetTrigger ht = new HeadsetTrigger();
                    ht.setState(state);
                    triggers.add(ht);
                } else if (name.contains("Power")) {
                    PowerTrigger pt = new PowerTrigger();
                    pt.setState(state);
                    triggers.add(pt);
                } else if (name.contains("Wifi")) {
                    WifiTrigger wt = new WifiTrigger();
                    wt.setState(state);
                    triggers.add(wt);
                }
                Log.d("Trigger", "size:" + triggers.size());
            } while (cursor.moveToNext());
            Log.d("Trigger", "size:" + triggers.size());
        }
        else {
            return null;
        }
        cursor.close();

        Log.d("Trigger", "size:" + triggers.size());

        return triggers;
    }

    public ArrayList<Action> getAllActions(int profileId) {
        ArrayList<Action> actions = new ArrayList<Action>();
        Cursor cursor = null;

        try {
            cursor = db.query(ProfileDb.ACTION_TABLE_NAME, null,
                    ProfileDb.ACTION_PROFILE_ID + " = " + Integer.toString(profileId),
                    null, null, null, null, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ProfileDb.ACTION_ID));
                String name = cursor.getString(cursor.getColumnIndex(ProfileDb.ACTION_NAME));

                Log.d("ProfileDbExchanger", "Action names: " + name);
                int state = cursor.getInt(cursor.getColumnIndex(ProfileDb.ACTION_STATE));

                if (name.contains("Alarm")) {
                    AlarmAction aa = new AlarmAction();
                    aa.setState(state);
                    actions.add(aa);
                } else if (name.contains("Brightness")) {
                    BrightnessAction ba = new BrightnessAction();
                    ba.setState(state);
                    actions.add(ba);
                } else if (name.contains("Bluetooth")) {
                    BluetoothAction ba = new BluetoothAction();
                    ba.setState(state);
                    actions.add(ba);
                } else if (name.contains("Music")) {
                    MusicAction ma = new MusicAction();
                    ma.setState(state);
                    actions.add(ma);
                } else if (name.contains("Volume")) {
                    VolumeAction va = new VolumeAction();
                    va.setState(state);
                    actions.add(va);
                } else if (name.contains("Wifi")) {
                    WifiAction wa = new WifiAction();
                    wa.setState(state);
                    actions.add(wa);
                }
            } while (cursor.moveToNext());
        }
        else {
            return null;
        }
        cursor.close();

        return actions;
    }

    public void deleteProfile(int profileId) {
        try {
            db.delete(ProfileDb.PROFILE_TABLE_NAME,
                    ProfileDb.PROFILE_ID + " = " + Integer.toString(profileId), null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void deleteTriggersByProfileId(int profileId) {
        try {
            db.delete(ProfileDb.TRIGGER_TABLE_NAME,
                    ProfileDb.TRIGGER_PROFILE_ID + " = " + Integer.toString(profileId), null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void deleteActionsByProfileId(int profileId) {
        try {
            db.delete(ProfileDb.ACTION_TABLE_NAME,
                    ProfileDb.ACTION_PROFILE_ID + " = " + Integer.toString(profileId), null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

}
