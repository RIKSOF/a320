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
	 * 
	 */
	protected List<RemoteObjectDelegate> delegates_ = new ArrayList<RemoteObjectDelegate>();
	
	public void load(RemoteObjectDelegate delegate) {
		
		delegates_.add(delegate);
		this.execute();		
	}
	
	@Override
	protected void onPostExecute(Object results) {
		
		for(RemoteObjectDelegate delegate : delegates_){
			delegate.update(results);
		}
	}
	
	protected Object getRemoteObject(String url, Type type){
		CoreHttpClient http = new CoreHttpClient();

		List<T> remoteObject = null;
		
		String response = null;
		try {

			response = http
					.executeGet(url);

			remoteObject = JsonController.Deserialize(
					response, type);
			
		} catch (ServerException e) {
			e.printStackTrace();
		}
		
		return remoteObject;
	}

	protected Object getRemoteObject(String url,  Class<T> type){
		CoreHttpClient http = new CoreHttpClient();

		RemoteObject remoteObject = null;
		
		String response = null;
		try {

			response = http
					.executeGet(url);

			remoteObject = JsonController.Deserialize(
					response, type);
			
		} catch (ServerException e) {
			e.printStackTrace();
		}
		
		return remoteObject;
	}
}