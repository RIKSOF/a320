/***
	Copyright (c) 2010 CommonsWare, LLC
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

package com.riksof.a320.c2dm.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.riksof.a320.c2dm.C2DMBaseReceiver;
import com.riksof.a320.http.Cache;

public class C2DMReceiver extends C2DMBaseReceiver {
	public C2DMReceiver() {
		super("this.is.not@real.biz");
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		HttpClient httpclient = new DefaultHttpClient();
		Log.w("C2DMReceiver-onRegistered", "Sending request to Server");

		HttpPost httppost = new HttpPost(getResources().getString(
				R.string.server_url));
        
		// Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("deviceToken", registrationId));
        nameValuePairs.add(new BasicNameValuePair("sdkType", "Android"));
        nameValuePairs.add(new BasicNameValuePair("openUDID", getOpenUDID(this.getBaseContext())));
        nameValuePairs.add(new BasicNameValuePair("deviceId", getAndroidId(this.getBaseContext())));
        try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		try {
			HttpResponse response = httpclient.execute(httppost);
			System.out.println(response);
		} catch (Exception e) {
			Log.e("C2DMReceiver-onRegistered", e.getMessage());
		}
		Log.w("C2DMReceiver-onRegistered", registrationId);
	}

	@Override
	public void onUnregistered(Context context) {
		Log.w("C2DMReceiver-onUnregistered", "got here!");
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.w("C2DMReceiver-onError", errorId);
	}

	@Override
	protected void onMessage(Context contxt, Intent intent) {
		
		String unValidatedURL = intent.getStringExtra("payload");		
		Log.w("C2DMReceiver", unValidatedURL);
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher; // icon from resources
		
		CharSequence tickerText = "You got message !!!"; // ticker-text
		long when = System.currentTimeMillis(); // notification time
		Context context = getApplicationContext(); // application Context

		Intent notificationIntent = new Intent(this, PushEndpointDemo.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);

		CharSequence contentTitle = "Norification Received !";

		// the next two lines initialize the Notification, using the configurations above
		Notification notification = new Notification(icon, tickerText, when);
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.setLatestEventInfo(context, contentTitle, unValidatedURL,
				contentIntent);
		notificationManager.notify(10001, notification);

		Cache.getInstance().remove(unValidatedURL);
		
		Log.w("C2DMReceiver", "finish");
	}

	/**
	 * Returns device id.
	 * 
	 * @param context
	 *            The context used to access the settings resolver
	 * @return Device id or null if failed to fetch
	 */
	public String getAndroidId(Context context) {
		final String androidId = android.provider.Settings.Secure.getString(
				context.getContentResolver(),
				android.provider.Settings.Secure.ANDROID_ID);
		return androidId;
	}

	/**
	 * Returns open UDID.
	 * 
	 * @param context
	 *            The context used to access the settings resolver
	 * @return UDID or null if failed to fetch
	 */
	public String getOpenUDID(Context context) {
		String strUDID = "";
		try {
			// To Initialize the Open UDID
			OpenUDID_manager.sync(context);

			// To check if the initialization is over (it's asynchronous)
			// Let it give 5 seconds for initialization. At every a second,
			// it'll check for the status.
			for (int i = 0; i < 5; i++) {
				if (OpenUDID_manager.isInitialized()) {
					break;
				}
				Thread.sleep(1000);
			}

			strUDID = OpenUDID_manager.getOpenUDID();
			if (strUDID == null/*
								 * ||
								 * androidId.toLowerCase().equals(INVALID_ANDROID_ID
								 * )
								 */) {
				return "";
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strUDID;
	}
}
