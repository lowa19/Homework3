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

	private Cannon playerOneCannon;
	private FireButtonRect myFireButton;
	private ArrayList<Cannonball> activeCannonballs;
	private ArrayList<Targets> targets;
	private int numTargets = 3;
	private int cannonBallRadius = 30;
	private int cannonPower = 80;
	private double gravity = 2.0;
	Point startDrag = new Point(0,0);
	Point endDrag = new Point(0,0);
	private boolean gameStart = false;

	private Cannon playerTwoCannon;
	//private ArrayList<Cannonball> player2Cannonballs;
	//private int playerOneScore = 0;
	//private int playerTwoScore = 0;
	//Point startDragPlayerTwo = new Point(0,0);
	//Point endDragPlayerTwo = new Point(0,0);
	//private int playerOneFireCooldown = 0;
	//private int playerTwoFireCooldown = 0;

	public CannonAnimator()
	{
		playerOneCannon = new Cannon(cannonPower, 1);
		playerTwoCannon = new Cannon( cannonPower, 2);
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
		if(gameStart == false)
		{
			startScreen(g);
		}
		else {
			//draw all of the objects on the canvas
			myFireButton.drawMe(g);
			//playerTwoFireButton.drawMe(g);
			playerOneCannon.drawMe(g);
			playerTwoCannon.drawMe(g);
			for (Cannonball b : activeCannonballs) {
				b.drawMe(g);
			}
			/*
			for (Cannonball b: playerTwoCannonballs)
			{
				b.drawMe(g);
			}
			 */
			for (Targets t : targets) {
				t.drawMe(g);
			}
			//check if any of the targets are hit or cannonball hit ground
			checkIfHit(g);

			//update the cannonball positions
			for (Cannonball b : activeCannonballs) {
				b.updatePosition();
			}
			/*
			for(Cannonball b : playerTwoCannonballs)
			{
				b.updatePosition();
			}
			 */
			for (Targets t : targets) {
				t.moveTargets();
			}
			if(targets.isEmpty()) //make new targets if there are none left
			{
				numTargets++;
				constructTargets();
			}
			// checkIfWin(g);

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
		if(gameStart == false)
		{
			gameStart = true;
		}
		int xPos = (int)event.getX();
		int yPos = (int)event.getY();
		double angle;
		if(myFireButton.fireClick(xPos,yPos))
		{
			shootCannon(playerOneCannon);
		}
		else if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			startDrag.x = xPos;
			startDrag.y = yPos;
		}
		else if ( event.getAction() == MotionEvent.ACTION_UP)
		{
			endDrag.x = xPos;
			endDrag.y = yPos;
			angle = calculateAngle(startDrag, endDrag);
			playerOneCannon.shiftCannon(angle);
		}
	}//onTouch

	/**
	 * creates cannonballs starting at tip of cannon
	 */
	public void shootCannon(Cannon c)
	{
		 Cannonball myCannonBall = new Cannonball(cannonBallRadius, c.getCannonMuzzleX(),
				c.getCannonMuzzleY(), c.getPowerX(), c.getPowerY(), gravity);
		activeCannonballs.add(myCannonBall);
	}//shootCannon

	/**
	 * creates targets at random locations between the two cannons and adds to arraylist
	 * each target is a 80x80 square
	 */
	public void constructTargets()
	{
		int minPosX = playerOneCannon.getRotationAxisX() + playerOneCannon.getWidth();
		int maxPosX = playerTwoCannon.getRotationAxisX() - playerTwoCannon.getWidth();
		for(int i = 0; i < numTargets; i++)
		{
			int randomX = (int)((maxPosX - minPosX)*Math.random());
			int randomY = (int)(playerOneCannon.getGroundHeight()*Math.random());
			if(randomY == playerOneCannon.getGroundHeight())
			{
				randomY = randomY -100;
			}
			Targets temp = new Targets(minPosX + randomX, randomY,
					minPosX + 80 + randomX, 80 + randomY);
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
			if((b.getPredictedY()+b.getRadius()) >= myCannon.getGroundHeight()) //check if goint to hit ground
			{
				b.rolling(myCannon);
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
						textPaint.setTextSize(60);
						canvas.drawText("HIT!", t.xTopLeft, t.yTopLeft, textPaint);
						removeTargets.add(t);
						removeBalls.add(b);
					}
				}
			}
		}
		activeCannonballs.removeAll(removeBalls);
		targets.removeAll(removeTargets);
		/*
		copy code above but with the playerTwoCannonballs
		 */
	}//checkIfHit

	/**
	 * Use dot product formula with distances between points
	 * and cannon point of rotation ( a.b = |a||b|cos(angle) )
	 * @param start
	 * @param end
     * @return angle in radians
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
		//theta = theta*(180/Math.PI); //convert radians to degrees
		if(start.y < end.y)//up to down motion
		{
			theta = -theta;
		}
		return theta;
	}//calculateAngle

	/**
	 * creates a "start screen" that gives directions
	 * @param canvas
     */
	public void startScreen(Canvas canvas)
	{
		int centerScreenX = canvas.getWidth()/2;
		int centerScreenY = canvas.getHeight()/2;
		Paint startPaint = new Paint();
		startPaint.setColor(Color.WHITE);
		startPaint.setTextSize(200);
		canvas.drawText("Cannon Game", centerScreenX - 610, centerScreenY - 200, startPaint);
		startPaint.setTextSize(60);
		canvas.drawText("drag finger up and down to aim cannon", centerScreenX - 480,
				centerScreenY - 100, startPaint);
		canvas.drawText("first player to hit 30 targets wins",
				centerScreenX - 480, centerScreenY - 50, startPaint);
		startPaint.setColor(Color.RED);
		canvas.drawText("TAP SCREEN TO BEGIN", centerScreenX - 250, centerScreenY +50, startPaint);
	}

	/*
	public void checkIfWin(Canvas canvas)
	{
	if(playerOneScore >= 30 && playerTwoScore < 30)
			{
				drawText "Game Over Player One Wins"
			}
			else if (playerTwoScore >= 30 && playerOneScore < 30)
			{
				g.drawText("Game Over Player Two Wins")
			}
	}
	 */
}//class TextAnimator
