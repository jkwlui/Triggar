package com.kinwa91.triggar.actions;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.kinwa91.triggar.Action;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class BrightnessAction extends Action {
    @Override
    public void execute() {
        Settings.System.putInt(context.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, 50);
    }



}
