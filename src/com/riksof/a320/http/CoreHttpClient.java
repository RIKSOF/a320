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

package com.riksof.a320.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * This is a custom http client class that is used to simplify the calling of
 * GET and POST requests
 * 
 * For GET user just need to pass the URL of the Restful API and the response
 * will be returned to user For POST user will also provide the message with URL
 * 
 * @author rizwan
 * 
 */
public class CoreHttpClient {

	public Map<String, String> headers;
	
	// By default time out is 100 seconds
	public static int connectionTimeOut= 100000;
	public static int socketTimeOut = 100000;
	
	/**
	 * This method is used to execute Get Http Request
	 * 
	 * @param url
	 * @return
	 * @throws ServerException
	 */
	public String executeGet(String url) throws ServerException {

		// Response String
		String responseString = null;

		headers = new HashMap<String,String>();
		
		try {

			CacheConfig cacheConfig = new CacheConfig();
			cacheConfig.setMaxCacheEntries(1000);
			cacheConfig.setMaxObjectSizeBytes(1024 * 1024);

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
			HttpConnectionParams.setSoTimeout(httpParameters, socketTimeOut);
			
			// Create http client object
			DefaultHttpClient realClient = new DefaultHttpClient(httpParameters);
			realClient.addResponseInterceptor(MakeCacheable.INSTANCE, 0);
			CachingHttpClient httpclient = new CachingHttpClient(realClient,
					cacheConfig);

			// Create http context object
			HttpContext localContext = new BasicHttpContext();

			// Check for cached data against URL
			if (Cache.getInstance().get(url) == null) {

				// Execute HTTP Get Request
				HttpGet httpget = new HttpGet(url);

				// Get http response
				HttpResponse response = httpclient.execute(httpget,
						localContext);

				for(Header h : response.getAllHeaders()){
					headers.put(h.getName(), h.getValue());
				}
				
				
				// Create http entity object
				HttpEntity entity = response.getEntity();

				// Create and convert stream into string
				InputStream inStream = entity.getContent();
				responseString = convertStreamToString(inStream);

				// Cache url data
				Cache.getInstance().put(url, responseString);

			} else {
				
				// Returned cached data
				responseString = Cache.getInstance().get(url);
			}

		} catch (ClientProtocolException e) {
			// throw custom server exception in case of Exception
			e.printStackTrace();
			throw new ServerException("ClientProtocolException");
		} catch (ConnectTimeoutException e) {
			// throw custom server exception in case of Exception
			e.printStackTrace();
			throw new ServerException("ConnectTimeoutException");
		} catch (IOException e) {
			// throw custom server exception in case of Exception
			e.printStackTrace();
			throw new ServerException("Request Timeout");
		} catch (Exception e) {
			// throw custom server exception in case of Exception
			e.printStackTrace();
			throw new ServerException("Exception");
		} finally {
		}
		return responseString;
	}

	static class MakeCacheable implements HttpResponseInterceptor {
		public static MakeCacheable INSTANCE = new MakeCacheable();

		public void process(HttpResponse resp, HttpContext ctx)
				throws HttpException, IOException {
			resp.removeHeaders("Expires");
			resp.removeHeaders("Pragma");
			resp.removeHeaders("Cache-Control");
		}
	}

	/**
	 * This method is used to execute Post Http Request
	 * 
	 * @param url
	 * @param postMessage
	 * @return
	 */
	public String executePost(String url, String postMessage) throws ServerException {

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeOut);

		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader( "Content-Type", "application/json" );
		
		// Response String
		String responseString = null;

		try {
			
			httppost.setEntity(new ByteArrayEntity(postMessage.toString()
					.getBytes("UTF8")));

			// Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			// Create and convert stream into string
			InputStream inStream = entity.getContent();
			responseString = convertStreamToString(inStream);
			
		} catch (SocketTimeoutException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "Timeout Exception");
			throw new ServerException("Request Timeout");
			
		} catch (IOException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "IO Exception");
			throw new ServerException("Request Timeout");
		}

		return responseString;
	}

	/**
	 * This method is used to execute Post Http Request
	 * 
	 * @param url
	 * @param postMessage
	 * @return
	 */
	public String executePostWithResponseHandler(String url, String postMessage) {

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeOut);

		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		ResponseHandler<String> res = new BasicResponseHandler();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("Content-Type", "application/json");

		// Response String
		String responseString = null;

		try {
			// Execute HTTP Post Request
			httppost.setEntity(new ByteArrayEntity(postMessage.toString()
					.getBytes("UTF8")));

			responseString = httpclient.execute(httppost, res);

		} catch (SocketTimeoutException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "Timeout Exception");
			
		} catch (IOException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "IO Exception");
		}

		return responseString;
	}

	/**
	 * This method is used to execute Post Http Request
	 * 
	 * @param url
	 * @param postMessage
	 * @return
	 */
	public String executeDelete(String url) throws ServerException {

		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeOut);
		HttpConnectionParams.setSoTimeout(httpParameters, socketTimeOut);

		DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpDelete httpdelete = new HttpDelete(url);
		httpdelete.setHeader( "Content-Type", "application/json" );
		
		// Response String
		String responseString = null;

		try {

			// Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httpdelete);
			HttpEntity entity = response.getEntity();

			// Create and convert stream into string
			InputStream inStream = entity.getContent();
			responseString = convertStreamToString(inStream);
			
		} catch (SocketTimeoutException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "Timeout Exception");
			throw new ServerException("Request Timeout");
			
		} catch (IOException e) {
			// throw custom server exception in case of Exception
			Log.e("HttpClient", "IO Exception");
			throw new ServerException("Request Timeout");
		}

		return responseString;
	}

	/**
	 * This will check for network availability
	 * 
	 * @param context
	 * @return the boolean for network availability
	 */
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    
	    return false;
	}
	
	/**
	 * Converts input stream into String
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static final int POST_TYPE   = 1;
	public static final int GET_TYPE    = 2;
	public static final int PUT_TYPE    = 3;
	public static final int DELETE_TYPE = 4;

}
