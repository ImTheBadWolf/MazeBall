package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Settings_view settingsView;

    int playerAvatar = 5;
    boolean muted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsView = findViewById(R.id.settingsView);
        setGray();
        setPlayer(playerAvatar);
    }

    public void launchGame(View view){
        Intent myIntent = new Intent(this, LevelSelector.class);
        myIntent.putExtra("avatar", playerAvatar);
        startActivity(myIntent);
    }
    public void openSettings(View view){
        settingsView.setVisibility(View.VISIBLE);
        findViewById(R.id.settingsButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.playButton).setVisibility(View.INVISIBLE);
    }
    public void closeSettings(View view){
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
        setTint(tmpOldAvatar);
    }
    public void mute(View view){
        ImageButton muteBtn = findViewById(R.id.imageButtonMute);
        muted = !muted;
        muteBtn.setImageResource(muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground);
    }
    private void setTint(int oldAvatar){
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
    private void setGray(){
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