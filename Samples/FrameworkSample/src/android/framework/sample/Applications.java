package android.framework.sample;

import java.util.ArrayList;

import android.core.json.CollectionType;
import android.core.remote.RemoteObjectCollection;

public class Applications extends RemoteObjectCollection<Application> {

	@Override
	protected Object doInBackground(Object... object) {
		
		return getRemoteObject("http://23.23.181.66:8080/application/list",
				new CollectionType<ArrayList<Application>>() {}.getCollectionType());	
	} 
}
