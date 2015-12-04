package com.main.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.list.fragments.About;
import com.main.list.fragments.SubjectOccasionListFagment;
import com.main.list.fragments.TwitterFragment;

public class MainActivity extends Activity implements OnClickListener {

	public static String urlJsonArryNewsOcc; // json array response url

	// temporary string to show the parsed response
	private String jsonResponse;

	private static String TAG = MainActivity.class.getSimpleName();

	int layoutNum;
	ListView mainList;

	ArrayList<String> listText;

	// //////////////////////////////
	// ///////////////////////////////
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mPlanetTitles;

	public static String aboutUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.navagation_drawer_layout);
		setContentView(R.layout.navagation_drawer_layout);

		/*********************************/
		/***************** bottom bar ********************************/
		findViewById(R.id.face).setOnClickListener(this);
		findViewById(R.id.twitter).setOnClickListener(this);
		findViewById(R.id.youtube).setOnClickListener(this);
		findViewById(R.id.linkedIn).setOnClickListener(this);
		findViewById(R.id.gplus).setOnClickListener(this);
		findViewById(R.id.insta).setOnClickListener(this);
		findViewById(R.id.pinterest).setOnClickListener(this);
		findViewById(R.id.vimeo).setOnClickListener(this);

		// ///////////////////////////////action bar
		// customization///////////////////////

		ActionBar mActionBar = getActionBar();

		mActionBar.setBackgroundDrawable(new ColorDrawable(
				android.R.color.transparent));

		mActionBar.setDisplayShowHomeEnabled(false);// icon
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(20, 0, 20, 0);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#FFFEFF")));

		LayoutInflater mInflater = LayoutInflater.from(this);

		Typeface custom_font = Typeface.createFromAsset(this.getAssets(),
				"alarabiya.ttf");

		View mCustomView = mInflater.inflate(R.layout.action_bar_layout, null);
		TextView title = (TextView) mCustomView.findViewById(R.id.title_text);
		title.setTypeface(custom_font);
		title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TwitterFragment fragment = new TwitterFragment();
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();
			}
		});
		ImageView doctor = (ImageView) mCustomView
				.findViewById(R.id.imageView_action);
		doctor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				TwitterFragment fragment = new TwitterFragment();
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();

			}
		});
		mActionBar.setCustomView(mCustomView);

		mActionBar.setDisplayShowCustomEnabled(true);

		mTitle = mDrawerTitle = getTitle();
		// navagation drawer code

		mPlanetTitles = getResources().getStringArray(R.array.list_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// add header to listview
		ViewGroup header = (ViewGroup) getLayoutInflater().inflate(
				R.layout.header_listview, mDrawerList, false);

		mDrawerList.addHeaderView(header, null, false);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mPlanetTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		/*********************************************************************************/
		/********************************************************************************/
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_menu_black_18dp6, /*
										 * nav drawer image to replace 'Up'
										 * caret
										 */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(1);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		return super.onOptionsItemSelected(item);
	}

	/* The click listener for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			selectItem(position - 1);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		Bundle args = new Bundle();
		try {
			switch (position) {

			case 0:
				fragment = new About();
				args.putInt(About.ARG_PLANET_NUMBER, position);
				aboutUrl = "http://perfect-workgroup.com/test/alakleat/webservices/webServicesaAbout.php?user=1";
				goToFragment(fragment, args);
				break;
			case 1:
				fragment = new TwitterFragment();
				// if (TwitterLoginActivity.flag == 1) {
				// Intent i = new Intent(this, TwitterLoginActivity.class);
				// startActivity(i);
				// }
				args.putInt(TwitterFragment.ARG_PLANET_NUMBER, position);
				goToFragment(fragment, args);

				break;
			case 2:
				// urlJsonArryNewsOcc =
				// "http://192.168.1.7:8080/AwebService/rest/res/news";
				urlJsonArryNewsOcc = "http://perfect-workgroup.com/test/alakleat/webservices/webServicesaNews.php?user=1";
				SubjectOccasionListFagment.newsText = "yes";
				fragment = new SubjectOccasionListFagment();
				args.putInt(SubjectOccasionListFagment.ARG_PLANET_NUMBER,
						position);
				goToFragment(fragment, args);

				break;
			case 3:
				urlJsonArryNewsOcc = "http://perfect-workgroup.com/test/alakleat/webservices/webServicesaEvents.php?user=1";
				SubjectOccasionListFagment.newsText = "no";

				fragment = new SubjectOccasionListFagment();
				args.putInt(SubjectOccasionListFagment.ARG_PLANET_NUMBER,
						position);
				goToFragment(fragment, args);

				break;
			case 4:
				startActivity(new Intent(this, TwitterLoginActivity.class));

				break;
			}

		} catch (Exception e) {
			Log.e("taaaaaaaaaaag", e.getMessage());
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		// setTitle(mPlanetTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	public void goToFragment(Fragment fragment, Bundle args) {
		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).addToBackStack(null)
				.commit();
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the fragment, which will
		// then pass the result to the login button.
		// Fragment fragment = (Fragment) getFragmentManager().findFragmentById(
		// R.id.content_frame);
		Fragment fragment = new TwitterFragment();
		if (fragment != null) {
			fragment.onActivityResult(requestCode, resultCode, data);
			Toast.makeText(this, "to activity", Toast.LENGTH_SHORT).show();
			// startActivity(new Intent(this, TwitterLoginActivity.class));

		}
	}

	public void gotoWeb(String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.face:
			gotoWeb("https://www.facebook.com/pages/%D8%A7%D9%84%D8%B4%D9%8A%D8%AE-%D8%AF-%D8%B9%D9%84%D9%8A-"
					+ "%D8%A8%D9%86-%D8%B3%D8%B9%D9%8A%D8%AF-%D8%A7%D9%84%D8%B1%D8%A8%D9%8A%D8%B9%D9%8"
					+ "A-Dr-Sheikh-Ali-bin-Saeed-al-Rabieei/191325564289079?fref=ts");
			break;
		case R.id.twitter:
			gotoWeb("https://twitter.com/DrAliAlrabieei");

			break;
		case R.id.youtube:
			gotoWeb("https://www.youtube.com/user/aqleeat");

			break;
		case R.id.linkedIn:
			gotoWeb("https://www.linkedin.com/in/aqleeat");

			break;
		case R.id.insta:
			gotoWeb("https://instagram.com/aqleeat/");

			break;
		case R.id.vimeo:
			gotoWeb("https://vimeo.com/aqleeat");

			break;

		case R.id.gplus:
			gotoWeb("https://plus.google.com/101484765249807187807/posts");

			break;
		case R.id.pinterest:
			gotoWeb("https://www.pinterest.com/aqleeat/");

			break;

		default:
			break;
		}

	}

}
