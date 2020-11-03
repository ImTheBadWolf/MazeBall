package com.example.mazeball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

enum moveDir{
    UP,
    DOWN,
    LEFT,
    RIGHT
}

public class game_view extends View{

    Bitmap[] bmp, player;
    int width, height;
    float accXOffset = -999999999;
    float accYOffset = -999999999;
    boolean end = false;
    boolean paused = false;
    int[][] mazeMap = {
            {0},
    };

    int gameWidth = mazeMap[0].length;
    int gameHeight = mazeMap.length;

    int[] playerPos = new int[2];
    int[] goalPos = new int[]{-1,-1};
    int playerSizeMultiplier = 25;

    private EventListener listener;

    public void setRotationValues(float x, float y){
        //Log.d("accX", Float.toString(x));
        //Log.d("accY", Float.toString(y));
        if(accXOffset == -999999999 && accYOffset == -999999999){
            //setOffset for accelerometer, TODO this shitcode needs to be redone
            accXOffset = x;
            accYOffset = y;
        }
        else{
            if(!end && !paused)
                movePlayer(x-accXOffset,y-accYOffset);
        }

    }

    public void movePlayer(float x, float y){
        if(y > 5 && canMove(moveDir.RIGHT)){
            playerPos[0] += 1;
        }
        else if(y < -5 && canMove(moveDir.LEFT)){
            playerPos[0] -= 1;
        }
        if(x > 5 && canMove(moveDir.DOWN)){
            playerPos[1] += 1;
        }
        else if(x < -5 && canMove(moveDir.UP)){
            playerPos[1] -= 1;
        }
        invalidate();
    }
    private boolean canMove(moveDir dir){
        switch (dir){
            case RIGHT:
                return (mazeMap[playerPos[1]][playerPos[0]+1] == 0 );
            case LEFT:
                return (mazeMap[playerPos[1]][playerPos[0]-1] == 0 );
            case UP:
                return (mazeMap[playerPos[1]-1][playerPos[0]] == 0 );
            case DOWN:
                return (mazeMap[playerPos[1]+1][playerPos[0]] == 0 );
        }
        return false;
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
        bmp = new Bitmap[3];
        bmp[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile5_foreground);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile10_foreground);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.mipmap.tile11_foreground);

        player = new Bitmap[1];
        player[0] = BitmapFactory.decodeResource(getResources(), R.mipmap.player1_foreground);

        //find spawn and goal
        for(int y = 0; y < mazeMap.length; y++){
            for(int x = 0; x < mazeMap[0].length; x++){
                if(mazeMap[y][x] == 3){
                    playerPos[0] = x;
                    playerPos[1] = y;
                    mazeMap[y][x] = 0;
                }
                else if(mazeMap[y][x] == 4){
                    goalPos[0] = x;
                    goalPos[1] = y;
                    mazeMap[y][x] = 0;
                }
            }
        }
    }

    public void restart(){
        //TODO remove restart fnction, replace by accelerometer recalibration
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setMazeMap(int[][] mazeMap) {
        this.mazeMap = mazeMap;
        gameWidth = mazeMap[0].length;
        gameHeight = mazeMap.length;
        init(getContext());
        end = false;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w/gameWidth;
        height = h/gameHeight;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < gameHeight; i++) {
            for (int j = 0; j < gameWidth; j++) {
                canvas.drawBitmap(bmp[mazeMap[i][j]], null, new Rect(j * width, i * height, (j + 1) * width, (i + 1) * height), null);
            }
        }
        canvas.drawBitmap(player[0], null, new Rect(playerPos[0] * width-playerSizeMultiplier, playerPos[1] * height-playerSizeMultiplier, (playerPos[0] + 1) * width+playerSizeMultiplier, (playerPos[1] + 1) * height+playerSizeMultiplier), null);

        if(playerPos[0] == goalPos[0] && playerPos[1] == goalPos[1]){
            playerPos[0] =-1;//TODO remove this shitcode
            playerPos[1] = -1;//E: its good for now
            end = true;
            listener.levelFinished();
        }
    }

    public void attachActivity(Activity activity)
    {
        if(activity instanceof EventListener) {
            listener = (EventListener)activity;
        }
    }

}

