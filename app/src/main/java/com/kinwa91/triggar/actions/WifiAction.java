package com.kinwa91.triggar.actions;

import android.content.Context;
import android.net.wifi.WifiManager;

import com.kinwa91.triggar.Action;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class WifiAction extends Action {
    @Override
    public void execute() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }
}
