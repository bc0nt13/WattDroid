package edu.hawaii.wattdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Intro extends Activity implements OnClickListener{ 
	/** Called when the activity is first created. */ 
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro);
		
		// Setup Button Press Handlers
		View continueButton = findViewById(R.id.continue_button); 
		continueButton.setOnClickListener(this); 
		View aboutButton = findViewById(R.id.about_button); 
		aboutButton.setOnClickListener(this); 
		View exitButton = findViewById(R.id.exit_button); 
		exitButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.about_button:
				Intent about = new Intent(this, About.class);
				startActivity(about);
				break;
			case R.id.continue_button:
				Intent all = new Intent(this, WattDroid.class);
				startActivity(all);
				break;
			case R.id.exit_button:
				finish();
				break;
		}
		
	}
}
