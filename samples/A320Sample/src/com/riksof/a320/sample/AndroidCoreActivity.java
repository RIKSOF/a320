package com.riksof.a320.sample;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.riksof.a320.c2dm.common.R;
import com.riksof.a320.remote.RemoteObjectDelegate;

public class AndroidCoreActivity extends Activity implements RemoteObjectDelegate {

	private ListView listView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.mainListView);

		load();
		
		Button refreshButton = (Button) findViewById(R.id.buttonId);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			
				load();
			}
		});
	}

	private void load() {
//		new Applications(this);
//		applications.load(this);
		
		new Users(this);
		//users.load(this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void update(Object results) {

		if(results != null){
			
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
	
	public void registerDeviceToken(View view){
		
		Log.i("Activity", "Inside registerDeviceToken");
		
//		Intent intent = new Intent(this, PushEndpointDemo.class);
//		startActivity(intent);
	}

}