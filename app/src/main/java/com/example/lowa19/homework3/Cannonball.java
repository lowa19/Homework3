package com.example.lowa19.homework3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by lowa19 on 4/2/2017.
 */
public class Cannonball {

    private int radius, xCoor, yCoor;
    private double xVelocity, yVelocity;
    private Paint ballPaint;

    public Cannonball(int initRadius, int initX, int initY, double initVelocityX, double initVelocityY)
    {
        ballPaint = new Paint();
        ballPaint.setColor(Color.BLACK);
        ballPaint.setStyle(Paint.Style.FILL);
        radius = initRadius;
        xCoor = initX;
        yCoor = initY;
        xVelocity = initVelocityX;
        yVelocity = initVelocityY;
    }

    public void drawMe(Canvas canvas)
    {
        canvas.drawCircle(xCoor, yCoor, radius, ballPaint);
    }

    public int getRadius()
    {
        return this.radius;
    }
    public int getxCoor()
    {
        return this.xCoor;
    }
    public int getyCoor()
    {
        return this.yCoor;
    }

    public boolean hitTarget(Targets t)
    {
        if(xCoor+radius >= t.getxTopLeft() && yCoor+radius >= t.getyTopLeft()
                && xCoor-radius <= t.getxBottomRight() && yCoor-radius <=t.getyBottomRight())
        {
            return true;
        }
        return false;
    }

    public void updatePosition()
    {
        //changes the x and y coordinates of the cannonball
    }

}