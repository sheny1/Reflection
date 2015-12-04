package com.main.list.fragments;

import java.io.UnsupportedEncodingException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.activities.MainActivity;
import com.main.activities.R;
import com.perfect.json.HandleJson;
import com.perfect.json.XMLParser;

public class About extends Fragment {

	public static final String ARG_PLANET_NUMBER = "planet_number";

	TextView about;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int i = getArguments().getInt(ARG_PLANET_NUMBER);
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.about_doctor, container,
				false);
		about = (TextView) rootView.findViewById(R.id.about_text);
		new getAbout().execute(MainActivity.aboutUrl);
		return rootView;
	}

	public class getAbout extends AsyncTask<String, Void, String> {

		String text = "";

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				XMLParser parser = new XMLParser();
				String xml = parser.getXmlFromUrl(params[0]); // getting XML
				Document doc = parser.getDomElement(xml); // getting DOM element
				NodeList post = doc.getElementsByTagName("post");
				Element post1 = (Element) post.item(0);

				text = new String(parser.getValue(post1, "arAbout").getBytes(
						"ISO_8859_1"), "UTF-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				text = "";

			}

			return text;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != "") {
				about.setText(result);
			}
		}

	}

}
