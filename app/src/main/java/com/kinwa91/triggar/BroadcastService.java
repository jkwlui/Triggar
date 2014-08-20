package com.kinwa91.triggar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by kinwa91 on 2014-08-20.
 */
public class BroadcastService extends Service {

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                boolean connected = info.isConnected();

                // do when wifi changed

                Notification.Builder mBuilder =
                          new Notification.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_action_network_wifi)
                                .setContentTitle("Triggar Notification")
                                .setContentText("Wifi State Changed");

                int mNotificationId = 001;
                NotificationManager mNotifyMgr =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifyMgr.notify(mNotificationId, mBuilder.getNotification());

                //


            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BroadcastService", "Service Started");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
