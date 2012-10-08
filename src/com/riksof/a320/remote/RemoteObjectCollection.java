/**
 * Copyright 2012 RIKSOF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http: *www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.riksof.a320.remote;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.riksof.a320.http.CoreHttpClient;
import com.riksof.a320.http.ServerException;
import com.riksof.a320.json.JsonController;

/**
 * This is the class where asynchronous task are performed
 * User has to override the doInBackground method where user will call the getRemoteObject method
 *  
 * @author rizwan
 *
 * @param <T>
 */
public abstract class RemoteObjectCollection<T extends RemoteObject> extends AsyncTask<Object, Void, Object>{

	/**
	 * List of Delegates
	 */
	protected List<RemoteObjectDelegate> delegates_ = new ArrayList<RemoteObjectDelegate>();
	
	/**
	 * This start the process of executing URL and get the response
	 *  
	 * @param delegate
	 */
	public void load(RemoteObjectDelegate delegate) {
		
		delegates_.add(delegate);
		this.execute();		
	}
	
	/**
	 * This method will be called after the response is received from URL
	 * It will update all the registered delegates
	 */
	@Override
	protected void onPostExecute(Object results) {
		
		// Iterate over all registered delegates and call there update() method
		for(RemoteObjectDelegate delegate : delegates_){
			delegate.update(results);
		}
	}
	
	/**
	 * Client will call this method to get the object from remote location.
	 *  
	 * @param url is the url from where the data will return
	 * @param type is the List class type of object that will be returned by url
	 * @return the object from remote location
	 */
	protected Object getRemoteObject(String url, Type type){
		
		CoreHttpClient http = new CoreHttpClient();
		List<T> remoteObject = null;
		String response = null;
		
		try {
			// Get response string
			response = http.executeGet(url);
			
			// Deserialize the string into object of given type
			remoteObject = JsonController.Deserialize(response, type);

		} catch (ServerException e) {
			e.printStackTrace();
		}
		
		// Return remote object
		return remoteObject;
	}

	/**
	 * Client will call this method to get the object from remote location.
	 *  
	 * @param url is the url from where the data will return
	 * @param type is the class type of object that will be returned by url
	 * @return the object from remote location
	 */
	protected Object getRemoteObject(String url,  Class<T> type){

		CoreHttpClient http = new CoreHttpClient();
		RemoteObject remoteObject = null;
		String response = null;

		try {
			
			// Get response string
			response = http.executeGet(url);

			// Deserialize the string into object of given type
			remoteObject = JsonController.Deserialize(response, type);

		} catch (ServerException e) {
			e.printStackTrace();
		}
		
		// Return remote object
		return remoteObject;
	}
}