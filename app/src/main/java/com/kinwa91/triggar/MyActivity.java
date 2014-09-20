package com.kinwa91.triggar;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MyActivity extends Activity {
    // fields
    private boolean isServiceRunning = false;
    // instance of custom classes
    private ArrayList<Profile> profiles;
    // UI
    private ListView listView;

    private int MY_ACTIVITY_REQUEST_CODE = 0;

    private Context context;
    // Handler for Service
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // notification code
            Notification.Builder mBuilder =
                    new Notification.Builder(getApplication())
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

    private void triggerProfiles(int trigger, int state) {
        for (Profile p : profiles) {
            p.trigger(trigger, state);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        context = getApplicationContext();

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


        startBroadcastService();

        listView = (ListView) findViewById(R.id.profile_listview);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long profileId) {


                AlertDialog dialog = createDialog(view, i, profileId);
                dialog.show();
                return true;
            }
        });
        populateProfileListView();

        // Launch Service
        hack();


    }

    private void hack() {

        Intent intent = getIntent();
        if (intent != null) {
            int receivedTrigger = intent.getIntExtra("trigger", -1);
            int receivedState = intent.getIntExtra("state", -1);
            triggerProfiles(receivedTrigger, receivedState);
        }
    }

    private AlertDialog createDialog(View view, int i, long profileId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_profile)+" " + profiles.get(i).getName() + "?");
        final long deleteId = profileId;
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProfileDbExchanger db = new ProfileDbExchanger(context);
                db.open();
                db.deleteProfile((int) deleteId);
                db.close();
                startBroadcastService();
                populateProfileListView();

            }
        });


        builder.setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    private void populateProfileListView() {
        ProfileDbExchanger dbExchanger = new ProfileDbExchanger(this.getApplicationContext());
        dbExchanger.open();
        profiles = dbExchanger.getAllProfiles();
        dbExchanger.close();


        ProfileArrayAdapter adapter = new ProfileArrayAdapter(this, R.layout.profile_list_item, profiles);

        listView.setAdapter(adapter);
    }

    private void startBroadcastService() {
        Intent intent = new Intent(getApplicationContext(), BroadcastService.class);
        intent.putExtra(BroadcastService.EXTRA_MESSENGER, new Messenger(handler));

        startService(intent);
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
        if (id == R.id.action_new_profile) {
            Intent intent = new Intent(this, NewProfileActivity.class);
            startActivityForResult(intent, MY_ACTIVITY_REQUEST_CODE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                startBroadcastService();
                populateProfileListView();
            }
        }
    }
}
