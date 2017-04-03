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
	private ArrayList<Targets> targets;
	private int numTargets = 3;
	private double gravity = 9.8;

	public CannonAnimator()
	{
		myCannon = new Cannon(100);
		targets = new ArrayList<>();
		constructTargets();
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
	 * Tells the animation whether to go backwards.
	 * 
	 * @param b true iff animation is to go backwards.
	 */
	public void goBackwards(boolean b) {
		// set our instance variable
		goBackwards = b;
	}
	
	/**
	 * Action to perform on clock tick
	 * 
	 * @param g the graphics object on which to draw
	 */
	public void tick(Canvas g) {
		// bump our count either up or down by one, depending on whether
		// we are in "backwards mode".
		if (goBackwards) {
			count--;
		}
		else {
			count++;
		}

		// Determine the pixel position of our ball.  Multiplying by 15
		// has the effect of moving 15 pixel per frame.  Modding by 600
		// (with the appropriate correction if the value was negative)
		// has the effect of "wrapping around" when we get to either end
		// (since our canvas size is 600 in each dimension).
		int num = (count*15)%600;
		if (num < 0) num += 600;

		// Draw the ball in the correct position.
		Paint redPaint = new Paint();
		redPaint.setColor(Color.RED);
		g.drawCircle(num, num, 60, redPaint);

		myCannon.drawMe(g);
		myCannonBall.drawMe(g);
		for (Targets t:targets)
		{
			t.drawMe(g);
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
