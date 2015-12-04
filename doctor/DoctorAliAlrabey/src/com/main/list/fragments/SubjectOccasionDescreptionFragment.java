package com.main.list.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.activities.R;
import com.squareup.picasso.Picasso;

public class SubjectOccasionDescreptionFragment extends Fragment {

	ImageView imageDesc;
	TextView titleDesc, pageDesc;

	public static final String ARG_DESC_PAGE = "page_number";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		int desc_data = getArguments().getInt(ARG_DESC_PAGE);
		String img = getArguments().getString("img");
		String title = getArguments().getString("title");
		String about = getArguments().getString("about");
		View row = inflater.inflate(R.layout.subject_occasion_descreption,
				container, false);
		imageDesc = (ImageView) row.findViewById(R.id.image_desc);
		titleDesc = (TextView) row.findViewById(R.id.title_desc);
		pageDesc = (TextView) row.findViewById(R.id.page_desc);

		/********************************************************/
		Picasso.with(getActivity()).load(img).fit()
				.error(R.drawable.error_image)
				.placeholder(R.drawable.place_holder2).noFade().fit()
				.centerCrop().into(imageDesc);
		// titleDesc.setText(title);
		titleDesc.setText(title);
		pageDesc.setText(about);
		/*
		 * do some code to give data to titleDesc,pageDesc and imageDesc
		 * according to desc_data
		 */
		return row;
	}

}
