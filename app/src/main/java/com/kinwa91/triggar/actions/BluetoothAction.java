package com.kinwa91.triggar.actions;

import android.bluetooth.BluetoothAdapter;

import com.kinwa91.triggar.Action;

/**
 * Created by kinwa91 on 2014-09-15.
 */
public class BluetoothAction extends Action {
    @Override
    public void execute() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled()) {
            adapter.disable();
        } else {
            adapter.enable();
        }
    }
}
