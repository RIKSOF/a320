package com.riksof.a320.sample;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.riksof.a320.c2dm.common.R;

public class RemoteAdapter<RemoteObject> extends BaseAdapter {

	//
	private Activity activity;
	//
	private List<Application> remoteList;
	//
	private User user;
	//
	int layout;
	//
	private static LayoutInflater inflater = null;

	/**
	 * 
	 * @param a
	 * @param simplerow
	 * @param list
	 */
	public RemoteAdapter(Activity a, int simplerow, List<Application> list) {
		activity = a;
		remoteList = list;
		layout = simplerow;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 
	 * @param a
	 * @param simplerow
	 * @param usr
	 */
	public RemoteAdapter(Activity a, int simplerow, User usr) {
		activity = a;
		user = usr;
		layout = simplerow;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(layout, null);

		TextView text = (TextView) vi.findViewById(R.id.rowTextView);
		
		if(remoteList != null)
			text.setText(remoteList.get(position).getApplicationTitle());
		if(user != null)
			text.setText(user.getFirst_name());

		return vi;
	}

	/**
	 * 
	 */
	public int getCount() {
		if(remoteList != null)
			return remoteList.size();
		else
			return 1;
	}

	/**
	 * 
	 */
	public Object getItem(int position) {
		return position;
	}

	/**
	 * 
	 */
	public long getItemId(int position) {
		return position;
	}
}