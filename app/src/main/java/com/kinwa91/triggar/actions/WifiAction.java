package com.kinwa91.triggar.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.kinwa91.triggar.Action;
import com.kinwa91.triggar.R;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class WifiAction extends Action {
    @Override
    public void execute() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (state == 0) {
            wifiManager.setWifiEnabled(false);
        } else if (state == 1) {
            wifiManager.setWifiEnabled(true);
        }
    }
    @Override
    public String getName() {
        return context.getString(R.string.wifi_action);
    }
}
