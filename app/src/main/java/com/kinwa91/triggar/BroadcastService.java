package com.kinwa91.triggar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;

/**
 * Created by kinwa91 on 2014-08-20.
 */
public class BroadcastService extends Service {

    public final static String EXTRA_MESSENGER = "com.kinwa91.triggar.EXTRA_MESSAGER";

    private Intent serviceIntent;

    private Bundle extras;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                int state = Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
                fireEvent(0,state);
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                fireEvent(1,state);
            } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                boolean connected = info.isConnected();
                int state = connected ? 1 : 0;
                fireEvent(2,state);
            } else if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                fireEvent(3,1);
            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                fireEvent(3,0);
            } else if (action.equals(Intent.ACTION_BATTERY_LOW)) {
                fireEvent(4,0);
            } else if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                fireEvent(5,state);
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.serviceIntent = intent;
        if (serviceIntent != null) {
            this.extras = serviceIntent.getExtras();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BroadcastService", "Service Started");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);

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

    private void fireEvent(int trigger, int state) {
        if (extras != null) {
            Messenger messenger = (Messenger) extras.get(EXTRA_MESSENGER);
            Message msg = Message.obtain();

            msg.arg1 = trigger;
            msg.arg2 = state;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.d("BroadcastService", "Messenger Error exception");
            }
        }
    }


}
