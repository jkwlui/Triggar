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
        switch (state) {
            case 0:
                adapter.disable();
                break;
            case 1:
                adapter.enable();
                break;
        }
    }

    @Override
    public String getName() {
        return context.getResources().getString(R.string.bluetooth_action);
    }
}
