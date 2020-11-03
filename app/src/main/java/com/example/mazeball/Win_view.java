package com.example.mazeball;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Win_view extends ConstraintLayout {
    public Win_view(Context context) {
        super(context);
        init(context);
    }

    public Win_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
