package com.example.mazeball;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class pause_view extends ConstraintLayout {
    public pause_view(Context context) {
        super(context);
        init(context);
    }

    public pause_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
