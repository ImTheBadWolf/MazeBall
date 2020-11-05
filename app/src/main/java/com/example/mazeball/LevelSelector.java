package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LevelSelector extends AppCompatActivity {

    int playerAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        Intent i = getIntent();
        playerAvatar = i.getIntExtra("avatar", 1);//TODO instead of getting playe avatar from parent activity, load it from shared preferences
    }
    public void close(View view){
        NavUtils.navigateUpFromSameTask(this);
    }
    public void openLevel(View view){

        Intent myIntent = new Intent(this, GameActivity.class);
        myIntent.putExtra("level", Integer.parseInt(view.getTag().toString()));
        myIntent.putExtra("avatar", playerAvatar);
        startActivity(myIntent);
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