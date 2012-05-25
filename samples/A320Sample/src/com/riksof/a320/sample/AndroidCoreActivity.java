package com.riksof.a320.sample;

import java.util.List;

import android.app.Activity;
import android.core.remote.RemoteObjectDelegate;
import android.framework.sample.R;
import android.os.Bundle;
import android.widget.ListView;

public class AndroidCoreActivity extends Activity implements RemoteObjectDelegate {

	private ListView listView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.mainListView);
		
		Applications applications = new Applications();
		applications.load(this);
		
		Users users = new Users();
		users.load(this);
	}

	@Override
	public void update(Object results) {

		if(results instanceof List){
			List<Application> list = (List<Application>) results;

			if(list.get(0) instanceof Application){
				
				RemoteAdapter<Application> listAdapter = new RemoteAdapter<Application>(
							this, R.layout.simplerow, list);
					
					listView.setAdapter(listAdapter);
			}
		} else {
			
			
			if (results instanceof User) {

				User user = (User) results;

				RemoteAdapter<User> userAdapter = new RemoteAdapter<User>(
						this, R.layout.simplerow, user);

				listView.setAdapter(userAdapter);
					
			}
		}
	}	

}