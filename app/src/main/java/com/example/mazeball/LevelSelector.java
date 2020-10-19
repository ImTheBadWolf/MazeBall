package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
    }
    public void close(View view){
        NavUtils.navigateUpFromSameTask(this);
    }
    public void openLevel(View view){
        Intent myIntent = new Intent(this, GameActivity.class);
        startActivity(myIntent);
    }
}