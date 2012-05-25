package com.riksof.a320.sample;

import android.core.remote.RemoteObjectCollection;

public class Users extends RemoteObjectCollection<User> {
	
	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("https://graph.facebook.com/rizzz86", User.class);
	}
	
//	Log.i(TAG, "getFirst_name: " + user.getFirst_name());
//	Log.i(TAG, "getId: " + user.getId());
//	Log.i(TAG, "getLast_name: " + user.getLast_name());
//	Log.i(TAG, "getLink: " + user.getLink());
//	Log.i(TAG, "getName: " + user.getName());
//	Log.i(TAG, "getUsername: " + user.getUsername());
}

