package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class GameActivity extends AppCompatActivity implements EventListener {

    SensorManager sensorManager;
    Sensor accSensor;
    game_view gameView;
    pause_view pauseView;
    Win_view winView;
    ImageButton muteButton;
    ArrayList<ArrayList<String>> mazeMapsS = new ArrayList();
    ArrayList<ArrayList<Integer[]>> mazeMapsI = new ArrayList();
    SoundPlayer soundPlayer;
    SharedPreferences sharedpreferences;
    int mapIndex = 0;
    float accX;
    float accY;

    boolean muted;
    int playerAvatar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        sharedpreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        playerAvatar = sharedpreferences.getInt("avatarId", 1);
        muted = sharedpreferences.getBoolean("muted", false);
        soundPlayer = new SoundPlayer(this, muted);

        Intent i = getIntent();
        mapIndex  = i.getIntExtra("level", 1) - 1;
        loadLevels();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gameView = findViewById(R.id.gameView);
        pauseView = findViewById(R.id.pauseView);
        winView = findViewById(R.id.winview);
        muteButton = findViewById(R.id.imageButton4);
        muteButton.setImageResource( muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground );
        gameView.setAvatar(playerAvatar);
        gameView.setMazeMap(cloneArray(mapIndex));
        Toast.makeText(this, "Level " + (mapIndex+1), Toast.LENGTH_SHORT).show();
        gameView.attachActivity(this);
    }


    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accEListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        muted = sharedpreferences.getBoolean("muted", false);
        soundPlayer.setMuted(muted);
        muteButton.setImageResource( muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground );
    }
    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accEListener);
    }
    public SensorEventListener accEListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            accX = event.values[0]*10;
            accY = event.values[1]*10;
            gameView.setRotationValues(accX,accY);
        }
    };

    public void pauseGame(View view) {
        soundPlayer.playClickSound();
        pauseView.setVisibility(View.VISIBLE);
        findViewById(R.id.imageButton).setVisibility(View.INVISIBLE);
        gameView.setPaused(true);
    }
    public void resumeGame(View view) {
        soundPlayer.playClickSound();
        pauseView.setVisibility(View.INVISIBLE);
        findViewById(R.id.imageButton).setVisibility(View.VISIBLE);
        gameView.setPaused(false);
    }
    public void calibrateAccelerometer(View view){
        soundPlayer.playClickSound();
        gameView.calibrateAccelerometer(accX, accY);
        pauseView.setVisibility(View.INVISIBLE);
        findViewById(R.id.imageButton).setVisibility(View.VISIBLE);
        gameView.setPaused(false);
    }
    public void quitGame(View view){
        soundPlayer.playClickSound();
        NavUtils.navigateUpFromSameTask(this);
    }
    public void muteGame(View view){
        muted = !muted;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("muted", muted);
        editor.commit();
        muteButton.setImageResource( muted ? R.mipmap.mute_foreground : R.mipmap.muteoff_foreground );
        soundPlayer.setMuted(muted);
        soundPlayer.playClickSound();
    }
    public void nextLevel(View view){
        soundPlayer.playClickSound();
        this.nextLevel();
    }
    public void nextLevel(){
        mapIndex++;
        if(mapIndex > mazeMapsI.size()-1)
            mapIndex = 0;
        gameView.setMazeMap(cloneArray(mapIndex));
        Toast.makeText(this, "Level " + (mapIndex+1), Toast.LENGTH_SHORT).show();
        winView.setVisibility(View.INVISIBLE);
        findViewById(R.id.imageButton).setVisibility(View.VISIBLE);
    }
    private Integer[][] cloneArray(int mapIndex){
        Integer[][] mazeMap = new Integer[mazeMapsI.get(mapIndex).size()][];
        for (int i = 0; i < mazeMapsI.get(mapIndex).size(); i++)
            mazeMap[i] = mazeMapsI.get(mapIndex).get(i).clone();
        return mazeMap;
    }

    @Override
    public void levelFinished() {
        winView.setVisibility(View.VISIBLE);
        findViewById(R.id.imageButton).setVisibility(View.INVISIBLE);
        soundPlayer.playWinSound();
        int tmpLevel = sharedpreferences.getInt("maxLevel", 1);
        if(tmpLevel < 8 && !(mapIndex+1 < tmpLevel)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("maxLevel", tmpLevel+1);
            editor.commit();
        }
    }
    private void loadLevels(){
        AssetManager assetManager = getAssets();
        InputStream input;
        try {
            input = assetManager.open("levels.txt");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            String text = new String(buffer);
            Scanner scanner = new Scanner(text);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<String> tempLevelLines = new ArrayList<>();
                while(line.length()>0){
                    tempLevelLines.add(line.replace("{","").replace("}","").replace(",",""));
                    if(scanner.hasNextLine()){
                        line = scanner.nextLine();
                    }
                    else
                        break;
                }
                mazeMapsS.add(tempLevelLines);
            }
            scanner.close();

            for(ArrayList<String> textLevel : mazeMapsS){
                ArrayList<Integer[]> tempLevelLines = new ArrayList<>();
                for (String s: textLevel){
                    char[] ch = (s).toCharArray();
                    Integer[] line = new Integer[ch.length];
                    int x = 0;
                    for(char c : ch){
                        line[x] = Character.getNumericValue(c);
                        x++;
                    }
                    tempLevelLines.add(line);
                }
                mazeMapsI.add(tempLevelLines);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}