package com.main.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class SplashScreen extends Activity {

	// Note: Your consumer key and secret should be obfuscated in your source
	// code before shipping.
	// private static final String TWITTER_KEY = "i8XezNNxsfVDrukGZkB4lki5a";
	// private static final String TWITTER_SECRET =
	// "gHyH4lRzF5yfplWMOMSRQy39Aicvpn5ftvQPtMbHpneFpIpVSf";

	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	private static final String TWITTER_KEY = "lfvuQx8XXwVc1YyRxvFfeqGUk";
	private static final String TWITTER_SECRET = "cJWX5C2zFDkJv4ABQNxpsdxfsYWU9dyeELJuJFyB33DnnTefm2";
	
	// Splash screen timer
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
		getActionBar().hide();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,
		// TWITTER_SECRET);
		// Fabric.with(this, new Twitter(authConfig));
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));

		Typeface custom_font = Typeface.createFromAsset(this
				.getAssets(), "alarabiya.ttf");
		
		setContentView(R.layout.splash_screen);
		TextView text=(TextView)findViewById(R.id.textone);
		text.setTypeface(custom_font);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);

				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}
