package com.main.list.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.activities.R;
import com.main.activities.TwitterLoginActivity;
import com.main.activities.TwitterLoginActivity.getTimeLine;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.BaseTweetView;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterFragment extends Fragment {

	// private TwitterLoginButton loginButton;
	public static final String ARG_PLANET_NUMBER = "planet_number";
	ListView list;

	Button login;
	private TwitterLoginButton loginButton;

	List<Long> tweetsId;

	LinearLayout myLayout;
	TweetViewFetchAdapter adapter;

	TextView text;
	public static int x;

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (TwitterLoginActivity.session == null) {
			text.setVisibility(View.VISIBLE);
			myLayout.setVisibility(View.GONE);

		} else {

			new getTweets().execute("");
		}

	}
@Override
public void onDestroyView() {
	// TODO Auto-generated method stub
	super.onDestroyView();
	myLayout.removeAllViews();
}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// int i = getArguments().getInt(ARG_PLANET_NUMBER);

		View rootView = inflater.inflate(R.layout.twitter_layout, container,
				false);

		text = (TextView) rootView.findViewById(R.id.text);
		tweetsId = new ArrayList<Long>();

		adapter = new TweetViewFetchAdapter<BaseTweetView>(getActivity(),
				tweetsId);

		/***************************** tweet view ***************************************/
		myLayout = (LinearLayout) rootView.findViewById(R.id.tweet_layout);

		//

		// /************************** time line ****************************/
		// list = (ListView) rootView.findViewById(R.id.list);
		// final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) rootView
		// .findViewById(R.id.swipe_layout);
		//
		// swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
		// android.R.color.holo_green_light,
		// android.R.color.holo_orange_light,
		// android.R.color.holo_red_light);
		//
		// UserTimeline userTimeline = new UserTimeline.Builder()
		// .screenName("DrAliAlrabieei").includeReplies(true)
		// .includeRetweets(true).build();
		// //
		// final TweetTimelineListAdapter adapter = new
		// TweetTimelineListAdapter(
		// getActivity(), userTimeline);
		// // list.setAdapter(adapter);
		// list.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// Tweet tweet = (Tweet) parent.getItemAtPosition(position);
		// long c = tweet.id;
		// }
		// });
		//
		// swipeLayout
		// .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		// @Override
		// public void onRefresh() {
		// swipeLayout.setRefreshing(true);
		// adapter.refresh(new Callback<TimelineResult<Tweet>>() {
		//
		// @Override
		// public void failure(TwitterException arg0) {
		//
		// Log.e("refresh", "can't refresh");
		// }
		//
		// @Override
		// public void success(
		// Result<TimelineResult<Tweet>> arg0) {
		// // TODO Auto-generated method stub
		// swipeLayout.setRefreshing(false);
		// }
		// });

		return rootView;
	}

	public void showTweets() {
		TwitterCore
				.getInstance()
				.getApiClient(TwitterLoginActivity.session)
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
															getActivity(),
															tweet,
															R.style.tw__TweetLightWithActionsStyle);
													compact.setTweetActionsEnabled(true);

													myLayout.addView(compact);
													x = 1;
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

	class getTweets extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			showTweets();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			text.setVisibility(View.GONE);
			myLayout.setVisibility(View.VISIBLE);
		}

	}
}
