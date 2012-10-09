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

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

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

		try {

			CacheConfig cacheConfig = new CacheConfig();
			cacheConfig.setMaxCacheEntries(1000);
			cacheConfig.setMaxObjectSizeBytes(1024 * 1024);

			DefaultHttpClient realClient = new DefaultHttpClient();
			realClient.addResponseInterceptor(MakeCacheable.INSTANCE, 0);
			CachingHttpClient httpclient = new CachingHttpClient(realClient,
					cacheConfig);

			HttpContext localContext = new BasicHttpContext();

			if (Cache.getInstance().get(url) == null) {

				// Execute HTTP Get Request
				HttpGet httpget = new HttpGet(url);

				HttpResponse response = httpclient.execute(httpget,
						localContext);

				HttpEntity entity = response.getEntity();

				// Create and convert stream into string
				InputStream inStream = entity.getContent();
				responseString = convertStreamToString(inStream);

				Cache.getInstance().put(url, responseString);

			} else {
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
			throw new ServerException("IOException");
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
	public String executePost(String url, String postMessage) {

		HttpClient httpclient = new DefaultHttpClient();
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

		} catch (IOException e) {
			// throw custom server exception in case of Exception
			e.printStackTrace();
		}

		return responseString;
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

}
