package com.example.lowa19.homework3;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;


/**
 * class that animates a ball repeatedly moving diagonally on
 * simple background
 * 
 * @author Steve Vegdahl
 * @author Andrew Nuxoll
 * @version September 2012
 */
public class CannonAnimator implements Animator {

	// instance variables
	private int count = 0; // counts the number of logical clock ticks
	private boolean goBackwards = false; // whether clock is ticking backwards

	private Cannon myCannon;
	private Cannonball myCannonBall;
	private ArrayList<Cannonball> cannonballs;
	private ArrayList<Targets> targets;
	private int numTargets = 3;
	private double gravity = 9.8;
	Point startDrag = new Point(0,0);
	Point endDrag = new Point(0,0);

	public CannonAnimator()
	{
		myCannon = new Cannon(100);
		targets = new ArrayList<>();
		constructTargets();
		cannonballs = new ArrayList<>();
	}

	public void constructTargets()
	{
		for(int i = 0; i < numTargets; i++)
		{
			int randomX = (int)(800*Math.random());
			int randomY = (int)(1000*Math.random());
			Targets temp = new Targets(700 + randomX, 400 + randomY, 800 + randomX, 500 + randomY);
			targets.add(temp);
		}
	}

	public void shootCannon()
	{
		myCannonBall = new Cannonball(10, myCannon.getCannonMuzzleX(),
				myCannon.getCannonMuzzleY(), myCannon.getPowerX(), myCannon.getPowerY());
		cannonballs.add(myCannonBall);
	}
	public void checkIfHit()
	{
		//TODO: have this method change the text in statusText
		for (Cannonball b: cannonballs)
		{
			for (Targets t: targets)
			{
				if(b.hitTarget(t))
				{
					targets.remove(t);
					cannonballs.remove(b);
				}
			}
		}
	}
	/**
	 * Interval between animation frames: .03 seconds (i.e., about 33 times
	 * per second).
	 * 
	 * @return the time interval between frames, in milliseconds.
	 */
	public int interval() {
		return 30;
	}
	
	/**
	 * The background color: a light blue.
	 * 
	 * @return the background color onto which we will draw the image.
	 */
	public int backgroundColor() {
		// create/return the background color
		return Color.rgb(180, 200, 255);
	}

	/**
	 * Action to perform on clock tick
	 * 
	 * @param g the graphics object on which to draw
	 */
	public void tick(Canvas g) {
		//draw all of the objects on the canvas
		myCannon.drawMe(g);
		for (Cannonball b: cannonballs)
		{
			b.drawMe(g);
		}
		for (Targets t:targets)
		{
			t.drawMe(g);
		}
		//check if any of the targets are hit
		checkIfHit();
		//update the cannonball positions
		for (Cannonball b:cannonballs)
		{
			b.updatePosition(gravity);
		}
	}

	/**
	 * Tells that we never pause.
	 * 
	 * @return indication of whether to pause
	 */
	public boolean doPause() {
		return false;
	}

	/**
	 * Tells that we never stop the animation.
	 * 
	 * @return indication of whether to quit.
	 */
	public boolean doQuit() {
		return false;
	}

	/**
	 * used to change cannon angle by dragging finger on screen
	 * @param event a MotionEvent describing the touch
     */
	public void onTouch(MotionEvent event)
	{
		double angle;
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			startDrag.x = (int)event.getX();
			startDrag.y = (int)event.getY();
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP)
		{
			endDrag.x = (int)event.getX();
			endDrag.y = (int)event.getY();
			angle = calculateAngle(startDrag, endDrag);
			myCannon.shiftCannon(angle);
		}

	}

	/**
	 * Use dot product formula with distances between points
	 * and cannon point of rotation ( a.b = |a||b|cos(angle) )
	 * @param start
	 * @param end
     * @return angle
     */
	public double calculateAngle(Point start, Point end)
	{
		double theta;
		double alphaX = start.x - myCannon.getRotationAxisX();
		double alphaY = start.y - myCannon.getRotationAxisY();
		double betaX = end.x - myCannon.getRotationAxisX();
		double betaY = end.y - myCannon.getRotationAxisY();
		double magnitudeAlpha = Math.sqrt( (alphaX*alphaX) + (alphaY*alphaY));
		double magnitudeBeta = Math.sqrt((betaX*betaX) + (betaY*betaY));
		theta = Math.cosh(((alphaX*betaX)+(alphaY*betaY))/(magnitudeAlpha*magnitudeBeta));
		return theta;
	}
}//class TextAnimator
