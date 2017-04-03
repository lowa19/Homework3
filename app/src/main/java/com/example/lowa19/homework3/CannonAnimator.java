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
			int random = (int)(100*Math.random());
			Targets temp = new Targets(500 + random, 500, 600 + random, 600);
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
	 * reverse the ball's direction when the screen is tapped
	 */
	public void onTouch(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			goBackwards = !goBackwards;
		}
	}

}//class TextAnimator
