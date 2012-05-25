package android.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

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

			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);

			// Execute HTTP Get Request
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			// Create and convert stream into string
			InputStream inStream = entity.getContent();
			responseString = convertStreamToString(inStream);

		} catch (ClientProtocolException e) {
			// throw custom server exception in case of Exception
			throw new ServerException("ClientProtocolException");
		} catch (ConnectTimeoutException e) {
			// throw custom server exception in case of Exception
			throw new ServerException("ConnectTimeoutException");
		} catch (IOException e) {
			// throw custom server exception in case of Exception
			throw new ServerException("IOException");
		} catch (Exception e) {
			// throw custom server exception in case of Exception
			throw new ServerException("Exception");
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
