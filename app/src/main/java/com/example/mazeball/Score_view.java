package com.example.mazeball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Score_view extends ConstraintLayout {
    public Score_view(Context context) {
        super(context);
        init(context);
    }

    public Score_view(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
    }

    @Override
    protected void onAttachedToWindow() {
        //TODO load storage, display list
        
        super.onAttachedToWindow();
        TextView txtView =  ( (View) getParent()).findViewById(R.id.scoretext);
        try {
            txtView.setText("this shitdoesnt woork");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
