package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LevelSelector extends AppCompatActivity {

    boolean muted;
    int maxLevel;
    SoundPlayer soundPlayer;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        sharedpreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        muted = sharedpreferences.getBoolean("muted", false);
        maxLevel = sharedpreferences.getInt("maxLevel", 1);
        soundPlayer = new SoundPlayer(this, muted);
        setGrayAll();
    }
    @Override
    protected void onResume() {
        super.onResume();
        muted = sharedpreferences.getBoolean("muted", false);
        soundPlayer.setMuted(muted);
    }
    public void close(View view){
        soundPlayer.playClickSound();
        NavUtils.navigateUpFromSameTask(this);
    }
    public void openLevel(View view){
        int level = Integer.parseInt(view.getTag().toString());
        if (level <= maxLevel) {
            soundPlayer.playClickSound();
            Intent myIntent = new Intent(this, GameActivity.class);
            myIntent.putExtra("level", level);
            startActivity(myIntent);
        }
        else
            soundPlayer.playDClickSound();

    }

    private void setGrayAll(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        for(int i=maxLevel+1; i<9; i++){
            int resID = getResources().getIdentifier("levelBtn"+i, "id", getPackageName());
            ImageView imgv = findViewById(resID);
            imgv.setColorFilter(filter);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}