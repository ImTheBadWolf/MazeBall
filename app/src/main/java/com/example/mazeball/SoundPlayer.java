package com.example.mazeball;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int  winSound, clickSound, dClick;
    private boolean muted;

    public SoundPlayer(Context context, boolean muted){
        this.muted = muted;
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        winSound = soundPool.load(context, R.raw.win, 1);
        clickSound = soundPool.load(context, R.raw.click, 1);
        dClick = soundPool.load(context, R.raw.dclick, 1);
    }
    public void setMuted(boolean muted){
        this.muted = muted;
    }

    public void playWinSound(){
        if(!muted)
            soundPool.play(winSound, 1, 1, 1, 0, 1);
    }
    public void playClickSound(){
        if(!muted)
            soundPool.play(clickSound, 1, 1, 1, 0, 1);
    }
    public void playDClickSound(){
        if(!muted)
            soundPool.play(dClick, 1, 1, 1, 0, 1);
    }
}
