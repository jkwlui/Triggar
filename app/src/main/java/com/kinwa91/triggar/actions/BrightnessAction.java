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
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 50);
    }
    @Override
    public String getName() {
        return context.getString(R.string.brightness_action);
    }

}
