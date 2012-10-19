/**
 * Copyright 2012 RIKSOF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http: *www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.riksof.a320.remote;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.riksof.a320.remote.image.ImageLoader;

/**
 * This is a base Abstract Adapter class that contain common code of adapter class
 * 
 * @author rizwan
 * 
 */
public abstract class AbstractAdapter extends BaseAdapter {

	// Activity object
	protected Activity activity;

	// Inflater object
	protected static LayoutInflater inflater = null;

	// imageloader object
    public ImageLoader imageLoader;

    // data object
    public List<?> data;

	/**
	 * Custom constructor for remote adapter
	 * 
	 * @param activity
	 *            is an activity
	 */
	public AbstractAdapter(Activity activity) {
		this.activity = activity;
        this.imageLoader=new ImageLoader(activity.getApplicationContext());
        setInflater((LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
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
	
	/**
	 * 
	 * @return
	 */
	public static LayoutInflater getInflater() {
		return inflater;
	}

	/**
	 * 
	 * @param inflater
	 */
	public static void setInflater(LayoutInflater inflater) {
		AbstractAdapter.inflater = inflater;
	}
	
	/**
	 * 
	 */
	public int getCount() {
		return data.size();
	}

}
