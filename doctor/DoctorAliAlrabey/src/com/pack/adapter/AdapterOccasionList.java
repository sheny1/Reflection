package com.pack.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.list.objects.SubjectPojo;
import com.main.activities.R;
import com.squareup.picasso.Picasso;

/**
 * adapter for subject activity list view, occasions activity list view
 * 
 */
public class AdapterOccasionList extends ArrayAdapter<SubjectPojo> {
	Context context;
	ArrayList<SubjectPojo> items;

	int resource;
	int click;// 1 for subject,2 for occasion

	/**
	 * add value to constructor to detect which layout was clicked
	 * 
	 * @param context
	 * @param resource
	 * @param items
	 */
	public AdapterOccasionList(Context context, int resource,
			ArrayList<SubjectPojo> items) {
		super(context, resource, items);
		this.context = context;
		this.items = items;
		this.resource = resource;
	}

	/**
	 * depend on the resource given,change text and images using if statments
	 */
	@Override
	public View getView(int position, View itemRow, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater inflater = null;
		TextView title;
		ImageView img;

		if (itemRow == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// itemRow=inflater.inflate(R.layout.subject_list_row, parent,
			// false);
			itemRow = inflater.inflate(resource, parent, false);

		}

		title = (TextView) itemRow.findViewById(R.id.subject_title);
		img = (ImageView) itemRow.findViewById(R.id.subject_pic);
		String url;

		url = items.get(position).getImage();

		Picasso.with(context)
				.load("http://perfect-workgroup.com/test/alakleat/" + url).fit()
				.error(R.drawable.error_image)
				.placeholder(R.drawable.place_holder2).noFade().fit()
				.centerCrop().into(img);
		;
		title.setText(items.get(position).getTitle());

		return itemRow;
	}

}
