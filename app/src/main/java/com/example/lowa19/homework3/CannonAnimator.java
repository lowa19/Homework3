package com.example.lowa19.homework3;

import android.content.Context;
import android.graphics.*;
import android.util.DisplayMetrics;
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

	private Cannon myCannon;
	private FireButtonRect myFireButton;
	private ArrayList<Cannonball> activeCannonballs, idleCannonBalls;
	private ArrayList<Targets> targets;
	private int numTargets = 4;
	private int cannonBallRadius = 30;
	private int cannonPower = 50;
	private double gravity = 9.8;
	Point startDrag = new Point(0,0);
	Point endDrag = new Point(0,0);

	public CannonAnimator()
	{
		myCannon = new Cannon(cannonPower);
		myFireButton = new FireButtonRect();
		targets = new ArrayList<>();
		constructTargets();
		activeCannonballs = new ArrayList<>();
	}//ctor

	public int interval() {
		return 30;
	}

	public int backgroundColor() { return Color.rgb(180, 200, 255); }

	/**
	 * Action to perform on clock tick
	 *
	 * @param g the graphics object on which to draw
	 */
	public void tick(Canvas g) {
		//draw all of the objects on the canvas
		myFireButton.drawMe(g);
		myCannon.drawMe(g);
		for (Cannonball b: activeCannonballs)
		{
			b.drawMe(g);
		}
		for (Targets t:targets)
		{
			t.drawMe(g);
		}
		//check if any of the targets are hit or cannonball hit ground
		checkIfHit(g);

		//update the cannonball positions
		for (Cannonball b:activeCannonballs)
		{
			b.updatePosition();
		}
		for (Targets t: targets)
		{
			t.moveTargets();
		}
	}

	public boolean doPause() {
		return false;
	}

	public boolean doQuit() {
		return false;
	}

	/**
	 * used to change cannon angle by dragging finger on screen
	 * or fires cannon if the 'button' is touched
	 * @param event a MotionEvent describing the touch
     */
	public void onTouch(MotionEvent event)
	{
		int xPos = (int)event.getX();
		int yPos = (int)event.getY();
		double angle;
		if(myFireButton.fireClick(xPos,yPos))
		{
			shootCannon();
		}
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
	}//onTouch

	/**
	 * creates cannonballs starting at tip of cannon
	 */
	public void shootCannon()
	{
		 Cannonball myCannonBall = new Cannonball(cannonBallRadius, myCannon.getCannonMuzzleX(),
				myCannon.getCannonMuzzleY(), myCannon.getPowerX(), myCannon.getPowerY(), gravity);
		activeCannonballs.add(myCannonBall);
	}//shootCannon

	/**
	 * creates targets at random locations and adds to arraylist
	 */
	public void constructTargets()
	{
		for(int i = 0; i < numTargets; i++)
		{
			int randomX = (int)((1700 - myCannon.getCannonMuzzleX())*Math.random());
			int randomY = (int)(myCannon.getGroundHeight()*Math.random());
			if(randomY == myCannon.getGroundHeight())
			{
				randomY = randomY -100;
			}
			Targets temp = new Targets(myCannon.getCannonMuzzleX() + randomX, randomY,
					myCannon.getCannonMuzzleX() + 100 + randomX, 100 + randomY);
			targets.add(temp);
		}
	}//constructTargets

	/**
	 * goes through each cannonball in the array
	 * if the cannonball hits a target, they both get removed
	 * sends a HIT  message to the GUI
	 */
	public void checkIfHit(Canvas canvas)
	{
		ArrayList<Cannonball> removeBalls = new ArrayList<>();
		ArrayList<Targets> removeTargets = new ArrayList<>();
		for (Cannonball b: activeCannonballs)
		{
			if((b.getyCoor()+b.getRadius()) >= myCannon.getGroundHeight()) //check if hits ground
			{
				b.rolling();
			}
			if ((b.getxCoor() + b.getRadius()) >= 2000) //check if goes off screen
			{
				removeBalls.add(b);
			}
			else {
				for (Targets t : targets) {
					if (b.hitTarget(t)) {
						Paint textPaint = new Paint();
						textPaint.setColor(Color.WHITE);
						textPaint.setTextSize(50);
						canvas.drawText("HIT!", t.xTopLeft, t.yTopLeft, textPaint);
						removeTargets.add(t);
						removeBalls.add(b);
					}
				}
			}
		}
		activeCannonballs.removeAll(removeBalls);
		targets.removeAll(removeTargets);
	}//checkIfHit

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
		theta = Math.acos(((alphaX*betaX)+(alphaY*betaY))/(magnitudeAlpha*magnitudeBeta));
		theta = theta*(180/Math.PI); //convert radians to degrees
		return theta;
	}//calculateAngle
}//class TextAnimator
