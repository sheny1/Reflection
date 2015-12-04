package com.main.activities;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.perfect.json.HandleJson;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.internal.TwitterApi;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

public class TwitterLoginActivity extends Activity {

	private TwitterLoginButton loginButton;
	public static int flag = 1;
	public static String secret;
	public static String token;

	ListView list;
	List<Long> tweetsId;

	public static TwitterSession session;
	LinearLayout myLayout;
	TweetViewFetchAdapter adapter;

	SharedPreferences pref;
	Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_login_activity);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
		editor = pref.edit();

		// list = (ListView) findViewById(R.id.list1);
		tweetsId = new ArrayList<Long>();

		adapter = new TweetViewFetchAdapter<BaseTweetView>(this, tweetsId);

		flag = 0;

		/***************************** tweet view ***************************************/
		myLayout = (LinearLayout) findViewById(R.id.my_tweet_layout);

		loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
		loginButton.setCallback(new Callback<TwitterSession>() {

			@Override
			public void success(Result<TwitterSession> result) {

				session = result.data;
				// ///////////////////////////////
				TwitterAuthToken authToken = session.getAuthToken();
				token = authToken.token;
				secret = authToken.secret;

				Log.e("token", token);

				startActivity(new Intent(TwitterLoginActivity.this,
						MainActivity.class));

				// ////////////////////////////////////////////////
				// TwitterCore
				// .getInstance()
				// .getApiClient(session)
				// .getStatusesService()
				// .userTimeline(null, "DrAliAlrabieei", 100, null, null,
				// null, null, null, null,
				// new Callback<List<Tweet>>() {
				//
				// @Override
				// public void failure(
				// TwitterException exception) {
				// // TODO Auto-generated method stub
				// android.util.Log.d("twittercommunity",
				// "exception " + exception);
				// }
				//
				// @Override
				// public void success(
				// Result<List<Tweet>> result) {
				// // TODO Auto-generated method stub
				// for (Tweet t : result.data) {
				// // android.util.Log.d(
				// // "twittercommunity",
				// // "tweet is " + t.text);
				// long g = t.id;
				// tweetsId.add(g);
				//
				// }
				// TweetUtils
				// .loadTweets(
				// tweetsId,
				// new LoadCallback<List<Tweet>>() {
				// @Override
				// public void success(
				// List<Tweet> tweets) {
				// for (Tweet tweet : tweets) {
				// CompactTweetView compact = new CompactTweetView(
				// TwitterLoginActivity.this,
				// tweet,
				// R.style.tw__TweetLightWithActionsStyle);
				// compact.setTweetActionsEnabled(true);
				// myLayout.addView(compact);
				// }
				// }
				//
				// @Override
				// public void failure(
				// TwitterException exception) {
				//
				// }
				// });
				// // list.setAdapter(adapter);
				// // adapter.setTweetIds(tweetsId,
				// // new Callback<List<Tweet>>() {
				// //
				// // @Override
				// // public void failure(
				// // TwitterException exception) {
				// // // TODO
				// // // Auto-generated
				// // // method stub
				// //
				// // }
				// //
				// // @Override
				// // public void success(
				// // Result<List<Tweet>> result) {
				// //
				// // }
				// //
				// // });
				// }
				//
				// });

				// ///////////////////////////////////////////////////////////////////

				// new getTimeLine()
				// .execute("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=DrAliAlrabieei");
				// new getTimeLine()
				// .execute("api.twitter.com/1.1/statuses/user_timeline.json");

				// TwitterAuthConfig authConfig = TwitterCore.getInstance()
				// .getAuthConfig();
				//
				//
				// OAuthSigning oauthSigning = new OAuthSigning(authConfig,
				// authToken);
				// oauthSigning.
				//
				// Map<String, String> authHeaders = oauthSigning
				// .getOAuthEchoHeadersForVerifyCredentials();
				//
				// URL url;
				// try {
				// url = new URL(
				// "https://api.twitter.com/1.1/DrAliAlrabieei/verify_credentials.json");
				// HttpsURLConnection connection = (HttpsURLConnection) url
				// .openConnection();
				// connection.setRequestMethod("GET");
				//
				// // Add OAuth Echo headers to request
				// for (Map.Entry<String, String> entry : authHeaders
				// .entrySet()) {
				// connection.setRequestProperty(entry.getKey(),
				// entry.getValue());
				// }
				// connection.connect();

				// startActivity(new Intent(getBaseContext(),
				// MainActivity.class));
			}

			@Override
			public void failure(TwitterException exception) {
				Toast.makeText(getBaseContext(), exception.getMessage(),
						Toast.LENGTH_SHORT).show();
			}
		});

		// if (session == null) {
		// loginButton.setVisibility(View.VISIBLE);
		// }else{
		// loginButton.setVisibility(View.GONE);
		// showTweets();
		// }
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		this.finish();
	}
	
	

	public void showTweets() {
		TwitterCore
				.getInstance()
				.getApiClient(session)
				.getStatusesService()
				.userTimeline(null, "DrAliAlrabieei", 100, null, null, null,
						null, null, null, new Callback<List<Tweet>>() {

							@Override
							public void failure(TwitterException exception) {
								// TODO Auto-generated method stub
								android.util.Log.d("twittercommunity",
										"exception " + exception);
							}

							@Override
							public void success(Result<List<Tweet>> result) {
								// TODO Auto-generated method stub
								for (Tweet t : result.data) {
									// android.util.Log.d(
									// "twittercommunity",
									// "tweet is " + t.text);
									long g = t.id;
									tweetsId.add(g);

								}
								TweetUtils.loadTweets(tweetsId,
										new LoadCallback<List<Tweet>>() {
											@Override
											public void success(
													List<Tweet> tweets) {
												for (Tweet tweet : tweets) {
													CompactTweetView compact = new CompactTweetView(
															TwitterLoginActivity.this,
															tweet,
															R.style.tw__TweetLightWithActionsStyle);
													compact.setTweetActionsEnabled(true);
													myLayout.addView(compact);
												}
											}

											@Override
											public void failure(
													TwitterException exception) {

											}
										});

							}

						});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the login button.
		loginButton.onActivityResult(requestCode, resultCode, data);
	}

	public class getTimeLine extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = HandleJson.getDataFromServer(params[0], "get");
			try {

				JSONObject json = new JSONObject(url);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

}
