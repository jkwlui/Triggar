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
import android.widget.Button;
import android.widget.TextView;

import com.kinwa91.triggar.actions.BluetoothAction;
import com.kinwa91.triggar.triggers.WifiTrigger;

import java.util.ArrayList;


public class MyActivity extends Activity {

    private boolean isServiceRunning = false;
    private Button b;
    private Button checkButton;
    private TextView checkText;

    private ArrayList<Profile> profiles;

    private String somethingNew;

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



        checkText = (TextView) findViewById(R.id.checkText);
        checkButton = (Button) findViewById(R.id.check);

        b = (Button) findViewById(R.id.button);

        profiles = new ArrayList<Profile>();
        ArrayList<Trigger> t = new ArrayList<Trigger>();
        Trigger wt = new WifiTrigger();
        t.add(wt);
        ArrayList<Action> a = new ArrayList<Action>();
        Action ba = new BluetoothAction();
        a.add(ba);
        Profile p = new Profile(t, a);
        profiles.add(p);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BroadcastService.class);
                intent.putExtra(BroadcastService.EXTRA_MESSENGER, new Messenger(handler));
                if (isServiceRunning) {
                    stopService(intent);
                    b.setText("Start Service");
                } else {
                    startService(intent);
                    b.setText("Stop Service");
                }
                isMyServiceRunning(BroadcastService.class);
                Log.d("MyActivity", "Button clicked");
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMyServiceRunning(BroadcastService.class);
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                isServiceRunning = true;
                checkText.setText("Service is running");
                return true;
            }
        }
        isServiceRunning = false;
        checkText.setText("Service is NOT running");
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
