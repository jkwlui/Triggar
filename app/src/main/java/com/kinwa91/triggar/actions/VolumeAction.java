package com.kinwa91.triggar.actions;

import android.content.Context;
import android.media.AudioManager;

import com.kinwa91.triggar.Action;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class VolumeAction extends Action {
    @Override
    public void execute() {
        AudioManager audioManager =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, AudioManager.FLAG_VIBRATE);

    }
}
