package android.framework.sample;
//package android.core.test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.core.http.CoreHttpClient;
//import android.core.http.ServerException;
//import android.core.json.JsonController;
//import android.util.Log;
//
//public class Users extends RemoteObjectCollection<User> {
//	
//	//
//	private String TAG = this.getClass().getName();
//	
//	@Override
//	protected User[] doInBackground(Object... object) {
//
//		return showUsers();
//	}
//	
//	public User[] showUsers() {
//
//		CoreHttpClient http = new CoreHttpClient();
//
//		List<User> users = null;
//		
//		String response;
//		try {
//
//			response = http
//					.executeGet("https://graph.facebook.com/rizzz86");
//
//			Log.i("AndroidCoreActivity: ", response);
//
//			User user = JsonController.Deserialize(response, User.class);
//
//			Log.i(TAG, "getFirst_name: " + user.getFirst_name());
//			Log.i(TAG, "getId: " + user.getId());
//			Log.i(TAG, "getLast_name: " + user.getLast_name());
//			Log.i(TAG, "getLink: " + user.getLink());
//			Log.i(TAG, "getName: " + user.getName());
//			Log.i(TAG, "getUsername: " + user.getUsername());
//			
//			users = new ArrayList<User>();
//			users.add(user);
//			
//		} catch (ServerException e) {
//			e.printStackTrace();
//		}
//		
//		return users.toArray(new User[users.size()]);
//	}
//	
//}

