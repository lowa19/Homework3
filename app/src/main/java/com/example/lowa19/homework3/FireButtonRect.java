package com.example.lowa19.homework3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * This class creates a "button" that fires the cannon
 * Created by kaleolow on 4/4/17.
 */

public class FireButtonRect
{
    Rect fireButton;
    Paint buttonPaint, textPaint;

    public FireButtonRect()
    {
        fireButton = new Rect(50, 50, 150, 150);
        buttonPaint = new Paint();
        buttonPaint.setColor(Color.MAGENTA);
        buttonPaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW);
        textPaint.setTextSize(25);
    }

    public void drawMe(Canvas canvas)
    {
        canvas.drawRect(fireButton, buttonPaint);
        canvas.drawText("FIRE!", 80, 100, textPaint);
    }

    /**
     *
     * @param x x-pos of touch
     * @param y y-pos of touch
     * @return true if touch was inside 'button'
     */
    public boolean fireClick(int x, int y)
    {
        if(x>=50 && x<=150 && y>=50 && y<=150)
        {
            return true;
        }
        return false;
    }
}
