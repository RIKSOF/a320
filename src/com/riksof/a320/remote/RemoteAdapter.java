package com.riksof.a320.remote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class RemoteAdapter extends BaseAdapter {
	
	//
	protected Activity activity;
	//
	protected int layout;
	//
	protected static LayoutInflater inflater = null;
	
	/**
	 * 
	 * @param a
	 * @param simplerow
	 * @param list
	 */
	public RemoteAdapter(Activity a, int simplerow, Object objects) {
		activity = a;
		layout = simplerow;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
