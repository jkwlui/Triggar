package com.kinwa91.triggar;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kinwa91.triggar.actions.BrightnessAction;
import com.kinwa91.triggar.triggers.BluetoothTrigger;

import java.util.ArrayList;


public class MyActivity extends Activity {
    // fields
    private boolean isServiceRunning = false;
    // instance of custom classes
    private ArrayList<Profile> profiles;
    // UI
    private ListView listView;
    // Handler for Service
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // notification code
            Notification.Builder mBuilder =
                    new Notification.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_action_network_wifi)
                            .setContentTitle("Triggar Notification")
                            .setContentText("Wifi State Changed:" + msg.arg1 + " " + msg.arg2);

            int mNotificationId = 001;
            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.getNotification());

            triggerProfiles(msg);
        }
    };

    private void triggerProfiles(Message msg) {
        for (Profile p : profiles) {
            p.trigger(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

//        profiles = new ArrayList<Profile>();
//        ArrayList<Trigger> t = new ArrayList<Trigger>();
//        Trigger wt = new BluetoothTrigger();
//        t.add(wt);
//        ArrayList<Action> a = new ArrayList<Action>();
//        Action ba = new BrightnessAction();
//        ba.setContext(getApplicationContext());
//        a.add(ba);
//
//        Profile p = new Profile(t, a);
//        profiles.add(p);

        ProfileDbExchanger dbExchanger = new ProfileDbExchanger(this.getApplicationContext());
        dbExchanger.open();
        int profileId = dbExchanger.createProfile("Profile 1", 0);
        int triggerId = dbExchanger.createTrigger("Wifi", 1, profileId);
        int actionId = dbExchanger.createAction("Bluetooth", 0, profileId);

        Profile testProfile = dbExchanger.getProfile(profileId);
        dbExchanger.close();

        ArrayList<Profile> testProfiles = new ArrayList<Profile>();
        testProfiles.add(testProfile);

        ProfileArrayAdapter adapter = new ProfileArrayAdapter(this, R.layout.profile_list_item, testProfiles);

        listView = (ListView) findViewById(R.id.profile_listview);
        listView.setAdapter(adapter);

        // Launch Service

        Intent intent = new Intent(getApplicationContext(), BroadcastService.class);
        intent.putExtra(BroadcastService.EXTRA_MESSENGER, new Messenger(handler));

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                isServiceRunning = true;
                return true;
            }
        }
        isServiceRunning = false;
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
