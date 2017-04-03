package com.example.lowa19.homework3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by lowa19 on 4/2/2017.
 */
public class Cannon
{
    private Paint cannonPaint, wheelPaint;
    private Point topLeft, topRight, bottomLeft, bottomRight;
    private Path cannonBody;
    private int cannonAngle;
    private int wheelRadius = 15;
    private int x = 100;
    private int y = 100;
    private int power;

    public Cannon(int initPower)
    {
        cannonPaint = new Paint();
        cannonPaint.setColor(Color.GRAY);
        cannonPaint.setStyle(Paint.Style.FILL);
        wheelPaint = new Paint();
        wheelPaint.setColor(Color.YELLOW);
        wheelPaint.setStyle(Paint.Style.FILL);
        topLeft = new Point(x + 50, y);
        topRight = new Point(x + 50, y+50);
        bottomLeft = new Point(x, y);
        bottomRight = new Point (x, y + 50);
        cannonAngle = 0;
        power = initPower;
    }

    public void drawMe(Canvas canvas)
    {
        cannonBody = new Path();
        cannonBody.moveTo(bottomLeft.x, bottomLeft.y);
        cannonBody.lineTo(topLeft.x, topLeft.y);
        cannonBody.lineTo(topRight.x, topRight.y);
        cannonBody.lineTo(bottomRight.x, bottomRight.y);
        cannonBody.lineTo(bottomLeft.x, bottomLeft.y);
        cannonBody.close();
        canvas.drawPath(cannonBody, cannonPaint);
        canvas.drawCircle(x,y, wheelRadius, wheelPaint);
    }

    public void shiftCannon()
    {
        //TODO:finish this method
        //changes coordinates of each of the points
        //using the updated cannonAngle
    }
    /**
     * Used to set initial position of the cannonball
     * @return using top and bottom right positions
     * this method returns the X position using the midpoint formula
     */
    public int getCannonMuzzleX()
    {
        int midX = (topRight.x + bottomRight.x)/2;
        return midX;
    }
    /**
     * Used to set initial position of the cannonball
     * @return using top and bottom right positions
     * this method returns the X position using the midpoint formula
     */
    public int getCannonMuzzleY()
    {
        int midY = (topRight.y + bottomRight.y)/2;
        return midY;
    }

    public int getCannonAngle()
    {
        return this.getCannonAngle();
    }

    public void setCannonAngle(int angle)
    {
        this.cannonAngle = angle;
    }

    /**
     * Used to set initial velocity of the cannonball
     * @return using set force that the cannon exerts
     * this method returns the force in the X-direction
     */
    public double getPowerX()
    {
        double forceX = power*Math.cos(cannonAngle);
        return forceX;
    }

    /**
     * Used to set initial velocity of the cannonball
     * @return using set force that the cannon exerts
     * this method returns the force in the Y-direction
     */
    public double getPowerY()
    {
        double forceY = power*Math.sin(cannonAngle);
        return forceY;
    }

}
