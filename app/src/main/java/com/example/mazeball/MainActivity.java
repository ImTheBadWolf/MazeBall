package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Settings_view settingsView;
    SoundPlayer soundPlayer;
    SharedPreferences sharedpreferences;
    ImageButton muteBtn;
    int playerAvatar;
    boolean muted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        playerAvatar = sharedpreferences.getInt("avatarId", 1);
        muted = sharedpreferences.getBoolean("muted", false);
        settingsView = findViewById(R.id.settingsView);
        muteBtn = findViewById(R.id.imageButtonMute);
        muteBtn.setImageResource(muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground);
        setGrayAll();
        setPlayer(playerAvatar);
        soundPlayer = new SoundPlayer(this, muted);

    }
    @Override
    protected void onResume() {
        super.onResume();
        muted = sharedpreferences.getBoolean("muted", false);
        soundPlayer.setMuted(muted);
        muteBtn.setImageResource(muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground);
    }

    public void launchGame(View view){
        soundPlayer.playClickSound();
        Intent myIntent = new Intent(this, LevelSelector.class);
        startActivity(myIntent);
    }
    public void openSettings(View view){
        soundPlayer.playClickSound();
        settingsView.setVisibility(View.VISIBLE);
        findViewById(R.id.settingsButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.playButton).setVisibility(View.INVISIBLE);
    }
    public void closeSettings(View view){
        soundPlayer.playClickSound();
        settingsView.setVisibility(View.INVISIBLE);
        findViewById(R.id.settingsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.playButton).setVisibility(View.VISIBLE);
    }
    public void setPlayer(View view){
        this.setPlayer(Integer.parseInt(view.getTag().toString()));
    }
    private void setPlayer(int playerId){
        int tmpOldAvatar = playerAvatar;
        playerAvatar = playerId;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("avatarId", playerAvatar);
        editor.commit();
        setGray(tmpOldAvatar);
        if(soundPlayer != null)
            soundPlayer.playClickSound();
    }
    public void mute(View view){
        muted = !muted;
        muteBtn.setImageResource(muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("muted", muted);
        editor.commit();
        soundPlayer.setMuted(muted);
        /*if(!muted)
            bgMusic.startMusic();
        else
            bgMusic.stopMusic();*/
        soundPlayer.playClickSound();
    }
    private void setGray(int oldAvatar){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        int resID = getResources().getIdentifier("imageButton"+oldAvatar, "id", getPackageName());
        ImageButton btn = findViewById(resID);
        btn.setColorFilter(filter);

        resID = getResources().getIdentifier("imageButton"+playerAvatar, "id", getPackageName());
        btn = findViewById(resID);
        btn.clearColorFilter();
    }

    private void setGrayAll(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        for(int i=1; i<9; i++){
            int resID = getResources().getIdentifier("imageButton"+i, "id", getPackageName());
            ImageButton btn = findViewById(resID);
            btn.setColorFilter(filter);
        }
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
        else{

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