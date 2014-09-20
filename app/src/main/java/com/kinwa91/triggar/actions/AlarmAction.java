package com.kinwa91.triggar.actions;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.kinwa91.triggar.Action;
import com.kinwa91.triggar.R;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class AlarmAction extends Action {
    @Override
    public void execute() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

    @Override
    public String getName() {
        return context.getString(R.string.alarm_action);
    }
}
