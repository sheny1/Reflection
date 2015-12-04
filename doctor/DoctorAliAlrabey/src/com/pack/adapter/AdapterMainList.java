package com.pack.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.main.activities.R;

/**
 * 
 * @author Ahmed Nabil
 * adapter for main activity list view
 *
 */
public class AdapterMainList extends ArrayAdapter<String> {
	Context context;
	ArrayList<String> list;

	public AdapterMainList(Context context, int resource, ArrayList<String> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=objects;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inf=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row=inf.inflate(R.layout.main_list_row, parent, false);
//		TextView tv=(TextView)row.findViewById(R.id.listrow);
//		tv.setText((CharSequence) list.get(position));
		return row;
		
	}
	
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
