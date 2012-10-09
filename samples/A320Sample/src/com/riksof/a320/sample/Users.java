package com.riksof.a320.sample;

import com.riksof.a320.remote.RemoteObjectCollection;

public class Users extends RemoteObjectCollection<User> {

	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("https://graph.facebook.com/riz.ahmed.52", User.class);
	}
}

