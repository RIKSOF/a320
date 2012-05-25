package android.core.remote;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.core.http.CoreHttpClient;
import android.core.http.ServerException;
import android.core.json.JsonController;
import android.os.AsyncTask;

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
}


//for(T application : applications){
//	
//	Log.i(TAG, "getApplicationBuild: " + application.getApplicationBuild());
//	Log.i(TAG, "getApplicationKey: " + application.getApplicationKey());
//	Log.i(TAG, "getApplicationNumber: " + application.getApplicationNumber());
//	Log.i(TAG, "getApplicationRegistrationDate: " + application.getApplicationRegistrationDate());
//	Log.i(TAG, "getApplicationTitle: " + application.getApplicationTitle());
//	Log.i(TAG, "getPasswordValidationRegex: " + application.getPasswordValidationRegex());
//	Log.i(TAG, "getUserId: " + application.getUserId());
//	
//}

