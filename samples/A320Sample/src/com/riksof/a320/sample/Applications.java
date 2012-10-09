package com.riksof.a320.sample;

import java.util.ArrayList;

import com.riksof.a320.json.CollectionType;
import com.riksof.a320.remote.RemoteObjectCollection;

public class Applications extends RemoteObjectCollection<Application> {
	
	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("http://192.168.0.115:8080/application/list",
				new CollectionType<ArrayList<Application>>() {}.getCollectionType());	
	}
}
