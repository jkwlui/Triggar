package com.kinwa91.triggar.actions;

import android.provider.Settings;
import android.util.TypedValue;

import com.kinwa91.triggar.Action;
import com.kinwa91.triggar.R;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class BrightnessAction extends Action {

    @Override
    public void execute() {
        int adjustedState = 0;
        if (state < 20 ) {
            adjustedState = 20;
        } else if (state > 100) {
            adjustedState = 100;
        }
        Settings.System.putInt(context.getContentResolver(),
                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, adjustedState * 255 / 100 );
    }
    @Override
    public String getName() {
        return context.getResources().getString(R.string.brightness_action);
    }

}
