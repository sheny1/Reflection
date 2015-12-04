package com.app.application;

import io.fabric.sdk.android.Fabric;
import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;



public class DrApp extends Application {

	// Note: Your consumer key and secret should be obfuscated in your source
	// code before shipping.
	private static final String TWITTER_KEY = "jqDYOGG7RovUacLYmBDk2SFLH";
	private static final String TWITTER_SECRET = "VBKLhfXgoHnSSBZU1UHSKp9lHt31usUUmilwkVgSE54fmCPX0s";

	public static final String TAG = DrApp.class.getSimpleName();
	private static DrApp mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY,
				TWITTER_SECRET);
		try {
			Fabric.with(this, new Twitter(authConfig));

			if (Fabric.isInitialized()) {
				Log.e("success", "authentication");
			}
		} catch (Exception e) {
			Log.e("nooooooooo", e.getMessage());
		}

	}

	public static synchronized DrApp getInstance() {
		return mInstance;
	}

}
