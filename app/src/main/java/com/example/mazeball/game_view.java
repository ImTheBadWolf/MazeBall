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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class game_view extends View {

    Bitmap[] bmp;
    int width, height;

    private int[][] emptyMaze = {
            {1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6},
            {7,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,9}
    };

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
        bmp = new Bitmap[10];

        bmp[0] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[1] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[2] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[3] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[4] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[5] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[6] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[7] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[8] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
        bmp[9] = BitmapFactory.decodeResource(getResources(), R.drawable.eightbutton_background);
    }

    private void init(Context context) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 20;
        height = h / 20;
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                canvas.drawBitmap(bmp[emptyMaze[i][j]], null,
                        new Rect(j * width, i * height, (j + 1) * width, (i + 1) * height), null);
            }
        }
    }
}

