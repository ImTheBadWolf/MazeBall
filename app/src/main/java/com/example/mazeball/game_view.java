package com.example.mazeball;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.SENSOR_SERVICE;


public class game_view extends View{

    Bitmap[] bmp, player;
    int width, height;
    int gameWidth = 20;
    int gameHeight = 10;
    int[][] emptyMaze = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,2,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,2,2,2,2,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,2,0,2,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    int[] playerPos = {6,1};
    int playerSizeMultiplier = 25;

    public void zakric(){
        Log.d("kric", "yeet");
    }


    public game_view(Context context) {
        super(context);
        init(context);
    }

    public game_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public game_view(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        bmp = new Bitmap[4];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile5_foreground);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile10_foreground);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile11_foreground);

        player = new Bitmap[1];
        player[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.player1_foreground);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / gameWidth;
        height = h / gameHeight;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                canvas.drawBitmap(bmp[emptyMaze[i][j]], null, new Rect(j * width, i * height, (j + 1) * width, (i + 1) * height), null);
            }
        }
        canvas.drawBitmap(player[0], null, new Rect(playerPos[0] * width-playerSizeMultiplier, playerPos[1] * height-playerSizeMultiplier, (playerPos[0] + 1) * width+playerSizeMultiplier, (playerPos[1] + 1) * height+playerSizeMultiplier), null);

    }
}

