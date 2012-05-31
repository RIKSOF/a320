package com.riksof.a320.sample;

import java.util.ArrayList;

import com.riksof.a320.json.CollectionType;
import com.riksof.a320.remote.RemoteObjectCollection;
import com.riksof.a320.remote.RemoteObjectDelegate;

public class Applications extends RemoteObjectCollection<Application> {
	
	public Applications(RemoteObjectDelegate delegate) {
		super(delegate);
	}

	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("http://192.168.0.115:8080/application/list",
				new CollectionType<ArrayList<Application>>() {}.getCollectionType());	
	}

//for(T application : applications){
//	
//		Log.i(TAG, "getApplicationBuild: " + application.getApplicationBuild());
//		Log.i(TAG, "getApplicationKey: " + application.getApplicationKey());
//		Log.i(TAG, "getApplicationNumber: " + application.getApplicationNumber());
//		Log.i(TAG, "getApplicationRegistrationDate: " + application.getApplicationRegistrationDate());
//		Log.i(TAG, "getApplicationTitle: " + application.getApplicationTitle());
//		Log.i(TAG, "getPasswordValidationRegex: " + application.getPasswordValidationRegex());
//		Log.i(TAG, "getUserId: " + application.getUserId());
//	
//}

}
