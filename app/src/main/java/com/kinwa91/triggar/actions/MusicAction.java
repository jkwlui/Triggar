package com.kinwa91.triggar.actions;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.kinwa91.triggar.Action;

/**
 * Created by kinwa91 on 2014-09-16.
 */
public class MusicAction extends Action {

    public static final String CMDTOGGLEPAUSE = "togglepause";
    public static final String CMDPAUSE = "pause";
    public static final String CMDPREVIOUS = "previous";
    public static final String CMDNEXT = "next";
    public static final String SERVICECMD = "com.android.music.musicservicecommand";
    public static final String CMDNAME = "command";
    public static final String CMDSTOP = "stop";
    public static final String CMDSTART = "play";


    @Override
    public void execute() {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        Intent i = new Intent(SERVICECMD);
        i.putExtra(CMDNAME, CMDSTART);
        context.sendBroadcast(i);
    }
}
