package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor accSensor;

    game_view gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        gameView = findViewById(R.id.gameView);
    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(accEListener, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accEListener);
    }
    public SensorEventListener accEListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0]*10;
            float y = event.values[1]*10;
            gameView.setRotationValues(x,y);
        }
    };


}