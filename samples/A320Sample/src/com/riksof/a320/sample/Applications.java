package com.riksof.a320.sample;

import java.util.ArrayList;

import android.core.json.CollectionType;
import android.core.remote.RemoteObjectCollection;

public class Applications extends RemoteObjectCollection<Application> {

	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("http://23.23.181.66:8080/application/list",
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
