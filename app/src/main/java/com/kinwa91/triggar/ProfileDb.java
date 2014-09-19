package com.kinwa91.triggar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EdNKuma on 17/09/2014.
 */
public class ProfileDb extends SQLiteOpenHelper {

    private static ProfileDb sInstance;

    private static final String DATABASE_NAME = "profileDatabase";
    private static final int DATABASE_VERSION = 1;

    // Trigger Table

    // Table Name
    public static final String TRIGGER_TABLE_NAME = "trigger";

    // Fields
    public static final String TRIGGER_ID = "id";
    public static final String TRIGGER_NAME = "name";
    public static final String TRIGGER_STATE = "state";
    public static final String TRIGGER_PROFILE_ID = "profile_id";

    // Create Table Query

    private static final String CREATE_TABLE_TRIGGER = "CREATE TABLE "
            + TRIGGER_TABLE_NAME + "("
            + TRIGGER_ID + " INTEGER PRIMARY KEY,"
            + TRIGGER_NAME + " TEXT,"
            + TRIGGER_STATE + " INTEGER,"
            + TRIGGER_PROFILE_ID + " INTEGER"
            + ")";

    // Action Table

    // Table Name
    public static final String ACTION_TABLE_NAME = "action";

    // Fields
    public static final String ACTION_ID = "id";
    public static final String ACTION_NAME = "name";
    public static final String ACTION_STATE = "state";
    public static final String ACTION_PROFILE_ID = "profile_id";

    // Create Table Query

    private static final String CREATE_TABLE_ACTION = "CREATE TABLE "
            + ACTION_TABLE_NAME + "("
            + ACTION_ID + " INTEGER PRIMARY KEY,"
            + ACTION_NAME + " TEXT,"
            + ACTION_STATE + " INTEGER,"
            + ACTION_PROFILE_ID + " INTEGER"
            + ")";

    // Profile Table

    // Table Name
    public static final String PROFILE_TABLE_NAME = "profile";

    // Fields
    public static final String PROFILE_ID = "id";
    public static final String PROFILE_NAME = "name";
    public static final String PROFILE_STATE = "state";

    // Create Table Query

    private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "
            + PROFILE_TABLE_NAME + "("
            + PROFILE_ID + " INTEGER PRIMARY KEY,"
            + PROFILE_NAME + " TEXT,"
            + PROFILE_STATE + " INTEGER"
            + ")";

    public static ProfileDb getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProfileDb(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
        }
        return sInstance;
    }

    public ProfileDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_TABLE_PROFILE);
            sqLiteDatabase.execSQL(CREATE_TABLE_TRIGGER);
            sqLiteDatabase.execSQL(CREATE_TABLE_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRIGGER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACTION_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
