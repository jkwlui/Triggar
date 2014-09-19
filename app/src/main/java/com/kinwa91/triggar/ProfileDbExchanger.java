package com.kinwa91.triggar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.kinwa91.triggar.ProfileDb;

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

            return new Profile(name, state, new ArrayList<Trigger>(), new ArrayList<Action>());
        }
        else {
            return null;
        }

    }

}
