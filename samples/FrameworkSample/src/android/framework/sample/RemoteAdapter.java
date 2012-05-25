package android.framework.sample;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RemoteAdapter<RemoteObject> extends BaseAdapter {

	//
	private Activity activity;
	//
	private List<Application> remoteList;
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
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(layout, null);

		TextView text = (TextView) vi.findViewById(R.id.rowTextView);
		text.setText(remoteList.get(position).getApplicationTitle());

		return vi;
	}

	/**
	 * 
	 */
	public int getCount() {
		return remoteList.size();
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