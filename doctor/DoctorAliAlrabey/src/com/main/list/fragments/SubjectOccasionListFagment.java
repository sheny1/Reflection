package com.main.list.fragments;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.list.objects.NewsObject;
import com.list.objects.SubjectPojo;
import com.main.activities.MainActivity;
import com.main.activities.R;
import com.pack.adapter.AdapterNewsList;
import com.pack.adapter.AdapterOccasionList;
import com.perfect.json.XMLParser;

/**
 * Fragment that appears in the "content_frame", shows a layout depends on the
 * click
 */
public class SubjectOccasionListFagment extends Fragment implements
		OnItemClickListener {

	ListView subjectOccasionList;
	AdapterOccasionList adapter;
	private WeakReference<AsyncDoctor> asyncTaskWeakRef;

	ArrayList<SubjectPojo> list;
	private ProgressDialog pDialog;
	public AdapterNewsList adapter2;
	public static String newsText;
	
	public static int x;

	public static final String ARG_PLANET_NUMBER = "planet_number";

	public SubjectOccasionListFagment() {
		// Empty constructor required for fragment subclasses
		list = new ArrayList<SubjectPojo>();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

	}

	private void startNewAsyncTask() {
		AsyncDoctor asyncTask = new AsyncDoctor(this);
		this.asyncTaskWeakRef = new WeakReference<AsyncDoctor>(asyncTask);
		asyncTask.execute(MainActivity.urlJsonArryNewsOcc);

	}

	private boolean isAsyncTaskPendingOrRunning() {
		return this.asyncTaskWeakRef != null
				&& this.asyncTaskWeakRef.get() != null
				&& !this.asyncTaskWeakRef.get().getStatus()
						.equals(Status.FINISHED);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		int i = getArguments().getInt(ARG_PLANET_NUMBER);

		rootView = inflater.inflate(R.layout.subjects_occasion_layout,
				container, false);

		startNewAsyncTask();

		// progress dialog
		pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		// volley

		final SwipeRefreshLayout swipeTwo = (SwipeRefreshLayout) rootView
				.findViewById(R.id.swipe_layouttwo);

		swipeTwo.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		swipeTwo.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if(x==0){
				adapter.notifyDataSetChanged();
				}else if(x==1){
					adapter2.notifyDataSetChanged();

				}
				swipeTwo.setRefreshing(true);
				if (swipeTwo.isRefreshing())
					swipeTwo.setRefreshing(false);
			}

		});

		String planet = getResources().getStringArray(R.array.list_main)[i];

		getActivity().setTitle(planet);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String title, img, about;
		if (newsText == "yes") {
			NewsObject news = (NewsObject) parent.getItemAtPosition(position);
			title = news.getNewsName();
			img = "http://perfect-workgroup.com/test/alakleat/" + news.getImg();
			about = news.getNewsAbout();
		} else {
			SubjectPojo sub = (SubjectPojo) parent.getItemAtPosition(position);
			title = sub.getTitle();
			img = "http://perfect-workgroup.com/test/alakleat/"
					+ sub.getImage();
			about = sub.getAbout();
		}

		whichDescriptionPage(position, img, title, about);
	}

	/**
	 * description layout for both subjects and occasions are the same but
	 * depending on positions determine the data to be sent in the future from
	 * database or web service
	 * 
	 * @param position
	 */
	private void whichDescriptionPage(int position, String img, String title,
			String about) {

		try {
			Fragment fragment = new SubjectOccasionDescreptionFragment();
			Bundle args = new Bundle();
			args.putInt(SubjectOccasionDescreptionFragment.ARG_DESC_PAGE,
					position);
			args.putString("img", img);
			args.putString("title", title);
			args.putString("about", about);

			fragment.setArguments(args);

			getFragmentManager().beginTransaction()
					.replace(R.id.content_frame, fragment).addToBackStack(null)
					.commit();
			// trans.addToBackStack("trans");
		} catch (Exception e) {
			Log.e("taaaaaaaaaag", e.getMessage());
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		subjectOccasionList = (ListView) getView().findViewById(
				R.id.subject_list);
		subjectOccasionList.setOnItemClickListener(this);

	}

	class AsyncDoctor extends AsyncTask<String, Void, String> {

		private WeakReference<SubjectOccasionListFagment> fragmentWeakRef;

		ArrayList<NewsObject> arrayl = new ArrayList<NewsObject>();
		ArrayList<SubjectPojo> subject = new ArrayList<SubjectPojo>();

		private ProgressDialog dialog;

		private AsyncDoctor(SubjectOccasionListFagment fragment) {
			this.fragmentWeakRef = new WeakReference<SubjectOccasionListFagment>(
					fragment);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage("loading, please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			arrayl.clear();
			try {
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(params[0]); // getting XML
				Document doc = parser.getDomElement(xml); // getting DOM element

				NodeList news = null;

				news = doc.getElementsByTagName("post");

				for (int i = 0; i < news.getLength(); i++) {
					Element post = (Element) news.item(i);
					SubjectPojo pojo = new SubjectPojo();
					NewsObject newsObj = new NewsObject();
					if (params[0] == "http://perfect-workgroup.com/test/alakleat/webservices/webServicesaEvents.php?user=1") {
						pojo.setTitle(new String(parser
								.getValue(post, "arName")
								.getBytes("ISO_8859_1"), "UTF-8"));
						pojo.setAbout(new String(parser.getValue(post,
								"arAbout").getBytes("ISO_8859_1"), "UTF-8"));
						pojo.setImage(parser.getValue(post, "img"));

						subject.add(pojo);

					} else {

						newsObj.setNewsName(new String(parser.getValue(post,
								"arName").getBytes("ISO_8859_1"), "UTF-8"));
						newsObj.setNewsAbout(new String(parser.getValue(post,
								"arAbout").getBytes("ISO_8859_1"), "UTF-8"));
						newsObj.setImg(parser.getValue(post, "img"));

						arrayl.add(newsObj);

					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			if (this.fragmentWeakRef.get() != null) {

				x=1;
				subjectOccasionList
						.setOnItemClickListener(SubjectOccasionListFagment.this);
				if (SubjectOccasionListFagment.newsText == "yes") {
					adapter2 = new AdapterNewsList(getActivity(),
							R.layout.subject_list_occasion_row, arrayl);
					subjectOccasionList.setAdapter(adapter2);

				} else {x=0;
					
					adapter = new AdapterOccasionList(getActivity(),
							R.layout.subject_list_occasion_row, subject);
					subjectOccasionList.setAdapter(adapter);
				}

			}
		}

	}

}
