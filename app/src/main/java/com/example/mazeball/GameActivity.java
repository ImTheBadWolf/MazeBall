package com.example.mazeball;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor gyroscopeSensor;

    game_view gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        gameView = (game_view)findViewById(R.id.gameView);
        gameView.zakric();
    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroscopeEListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroscopeEListener);
    }
    public SensorEventListener gyroscopeEListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int acc) {
        }

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0]*100;
            float y = event.values[1]*100;
            Log.d("gyroX", Float.toString(x));
            Log.d("gyroY", Float.toString(y));
        }
    };


}