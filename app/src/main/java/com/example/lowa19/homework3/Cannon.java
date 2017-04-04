package com.example.lowa19.homework3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by lowa19 on 4/2/2017.
 */
public class Cannon
{
    private Paint cannonPaint, wheelPaint;
    private Point topLeft, topRight, bottomLeft, bottomRight;
    private Path cannonBody;
    private double cannonAngle = 0;
    int maxAngle = 90;
    int minAngle = 0;
    private int wheelRadius = 50;
    private int x = 100;
    private int y = 1200;
    private int power;

    public Cannon(int initPower)
    {
        cannonPaint = new Paint();
        cannonPaint.setColor(Color.GRAY);
        cannonPaint.setStyle(Paint.Style.FILL);
        wheelPaint = new Paint();
        wheelPaint.setColor(Color.YELLOW);
        wheelPaint.setStyle(Paint.Style.FILL);
        topLeft = new Point(x, y -100);
        topRight = new Point(x + 300, y - 100);
        bottomLeft = new Point(x, y);
        bottomRight = new Point (x + 300, y);
        power = initPower;
    }

    public void drawMe(Canvas canvas)
    {
        //draw cannon
        cannonBody = new Path();
        cannonBody.moveTo(bottomLeft.x, bottomLeft.y);
        cannonBody.lineTo(topLeft.x, topLeft.y);
        cannonBody.lineTo(topRight.x, topRight.y);
        cannonBody.lineTo(bottomRight.x, bottomRight.y);
        cannonBody.lineTo(bottomLeft.x, bottomLeft.y);
        cannonBody.close();
        canvas.drawPath(cannonBody, cannonPaint);
        canvas.drawCircle(x,y, wheelRadius, wheelPaint);
        //draw 'ground'
        Paint grass = new Paint();
        grass.setColor(Color.GREEN);
        grass.setStyle(Paint.Style.FILL);
        Rect ground = new Rect(0, y + wheelRadius, 2000, 2000);
        canvas.drawRect(ground, grass);
    }

    /**
     * using Matrix, rotate path given the change in angle
     * @param angle is the progress from seekbar
     */
    public void shiftCannon(double angle)
    {
        double resultingAngle = cannonAngle + angle;

        if(resultingAngle <= maxAngle && resultingAngle >= minAngle) //limitations
        {
            Matrix matrix = new Matrix();
            matrix.setRotate((float)angle, x, y);
            cannonBody.transform(matrix); //rotate path
            cannonAngle = resultingAngle; //change the saved value for angle
        }
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

    public int getRotationAxisX()
    {
        return this.x;
    }

    public int getRotationAxisY()
    {
        return this.y;
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

    public int getGroundHeight()
    {
        return (this.y + this.wheelRadius);
    }

}
