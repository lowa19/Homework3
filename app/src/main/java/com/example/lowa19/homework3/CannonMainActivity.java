package com.example.lowa19.homework3;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * CannonMainActivity
 * 
 * This is the activity for the cannon animation. It creates a AnimationCanvas
 * containing a particular Animator object
 * 
 * @author Andrew Nuxoll
 * @version September 2012
 * 
 */
public class CannonMainActivity extends Activity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener{

	private SeekBar aimSeekBar;
	private Button fireButton;
	private TextView statusText;
	private CannonAnimator cannonAnim;
	/**
	 * creates an AnimationCanvas containing a TestAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create an animation canvas and place it in the main layout
		cannonAnim = new CannonAnimator();
		AnimationCanvas myCanvas = new AnimationCanvas(this, cannonAnim);
		LinearLayout mainLayout = (LinearLayout) this.findViewById(R.id.topLevelLayout);
		mainLayout.addView(myCanvas);

		//get references to GUI
		aimSeekBar = (SeekBar) findViewById(R.id.aimCannonSeekBar);
		fireButton = (Button) findViewById(R.id.fireButton);
		statusText = (TextView) findViewById(R.id.statusText);

		//register listeners
		aimSeekBar.setOnSeekBarChangeListener(this);
		fireButton.setOnClickListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onClick(View v) {
		cannonAnim.shootCannon();
	}
}
