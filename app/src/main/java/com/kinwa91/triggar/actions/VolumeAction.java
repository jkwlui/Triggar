package com.kinwa91.triggar.actions;

import android.content.Context;
import android.media.AudioManager;

import com.kinwa91.triggar.Action;
import com.kinwa91.triggar.R;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class VolumeAction extends Action {
    @Override
    public void execute() {
        AudioManager audioManager =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(audioManager.STREAM_SYSTEM);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, state * max / 100, AudioManager.FLAG_SHOW_UI);

    }
    @Override
    public String getName() {
        return context.getString(R.string.volume_action);
    }
}
