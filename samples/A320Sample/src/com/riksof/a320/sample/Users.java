package com.riksof.a320.sample;

import com.riksof.a320.remote.RemoteObjectCollection;
import com.riksof.a320.remote.RemoteObjectDelegate;

public class Users extends RemoteObjectCollection<User> {
	
	public Users(RemoteObjectDelegate delegate) {
		super(delegate);
	}

	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("https://graph.facebook.com/riz.ahmed.52", User.class);
	}
	
//	Log.i(TAG, "getFirst_name: " + user.getFirst_name());
//	Log.i(TAG, "getId: " + user.getId());
//	Log.i(TAG, "getLast_name: " + user.getLast_name());
//	Log.i(TAG, "getLink: " + user.getLink());
//	Log.i(TAG, "getName: " + user.getName());
//	Log.i(TAG, "getUsername: " + user.getUsername());
}

