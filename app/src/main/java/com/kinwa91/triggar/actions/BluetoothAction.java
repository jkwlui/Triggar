package com.kinwa91.triggar.actions;

import android.bluetooth.BluetoothAdapter;

import com.kinwa91.triggar.Action;
import com.kinwa91.triggar.R;

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

    @Override
    public String getName() {
        return context.getResources().getString(R.string.bluetooth_action);
    }
}
