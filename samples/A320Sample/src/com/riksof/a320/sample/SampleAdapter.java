package com.riksof.a320.sample;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.riksof.a320.remote.RemoteAdapter;
import com.riksof.a320.remote.image.ImageLoader;

public class SampleAdapter<RemoteObject> extends RemoteAdapter {

	//
	private List<Application> sampleList;
	//
	private User sampleUser;
	//
    private ImageLoader imageLoader; 

	/**
	 * 
	 * @param a
	 * @param simplerow
	 * @param objects
	 */
	@SuppressWarnings("unchecked")
	public SampleAdapter(Activity a, int simplerow, Object objects) {

		super(a, simplerow, objects);

        imageLoader=new ImageLoader(activity.getApplicationContext());

        if (objects != null) {

			if (objects instanceof User) {

				sampleUser = (User) objects;

			} else if (objects instanceof List) {

				sampleList = (List<Application>) objects;

			}
		}
	}

	/**
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		if (vi == null)
			vi = inflater.inflate(position, null);

		TextView text = (TextView) vi.findViewById(R.id.rowTextView);
		ImageView image = (ImageView) vi.findViewById(R.id.image);

		if (sampleList != null){
			text.setText(sampleList.get(position).getApplicationTitle());
	        imageLoader.DisplayImage(sampleList.get(position).getApplicationURL(), image);
		}
		
		if (sampleUser != null){
			text.setText(sampleUser.getFirst_name());
		}
		
		return vi;
	}

	/**
	 * 
	 */
	public int getCount() {

		if (sampleList != null)
			return sampleList.size();
		else
			return 1;
	}
}