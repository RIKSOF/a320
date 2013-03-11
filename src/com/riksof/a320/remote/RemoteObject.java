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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

import com.riksof.a320.datasource.DataSourceException;
import com.riksof.a320.datasource.IDataSource;
import com.riksof.a320.http.CoreHttpClient;
import com.riksof.a320.http.ServerException;
import com.riksof.a320.json.JsonController;

/**
 * The Object Model class will extend this class
 * 
 * @author rizwan
 *
 */
public abstract class RemoteObject<T> extends AsyncTask<Object, Void, Object> {

	/**
	 * This start the process of executing URL and get the response
	 * 
	 * @param delegate
	 */
	public void load(RemoteObjectDelegate delegate, Object type) {
		
		// Setting the request type
		requestType = CoreHttpClient.GET_TYPE;
		
		// Set delegate =
		delegateToAdd = delegate;
		
		// Start async task
		this.execute(type, uri);
	}

	/**
	 * This start the process of executing URL and get the response
	 * 
	 * @param delegate
	 */
	public void registerDelegate(RemoteObjectDelegate delegate) {

		delegates_.put(delegate.getClass().getSimpleName(), delegate);
	}

	/**
	 * This start the process of executing URL and get the response
	 * 
	 * @param delegate
	 */
	public void add(RemoteObjectDelegate delegate, RemoteObject remoteObject) {
		
		// Set request type
		requestType = CoreHttpClient.POST_TYPE;

		// Set delegate
		delegateToAdd = delegate;

		// start sync task
		this.execute(remoteObject);
	}

	@Override
	protected void onPreExecute() {
		// Iterate over all registered delegates and call there modelLoading() method
		for (RemoteObjectDelegate delegate : delegates_.values()) {
			delegate.modelLoading();
		}
	}
	
	/**
	 * This method will be called after the response is received from URL It
	 * will update all the registered delegates
	 */
	@Override
	protected void onPostExecute(Object results) {

		// Iterate over all registered delegates and call there modelLoaded() method
		for (RemoteObjectDelegate delegate : delegates_.values()) {
			
			if( requestType == CoreHttpClient.POST_TYPE ){
				delegate.didInsertObject(results);
			} else if( requestType == CoreHttpClient.GET_TYPE ){
				delegate.modelLoaded(results);
			}
		}
	}

	/**
	 * Implementation of background method
	 */
	@Override
	protected Object doInBackground(Object... params) {

		Object toRet = null;
		String id = null;
		
		try{
			
			// If type is POST
			if( requestType == CoreHttpClient.POST_TYPE ){
				
				Log.d("RemoteObject: Type ", "POST");
				
				try{

					// Add local object in cache
					RemoteObject newObject = (RemoteObject) params[0];

					// This is matched to identify
					// whether the object is locally created or coming from the server
					// Data from server already have the id
					if(uri.equals(collectionURL)){

						Log.d("RemoteObject: ", " Adding Local object ");

						// append local id
						uri = collectionURL+"/Local-"+(_dataSource.fetchRowCount() + 1);					

						// initialize delegate map
						newObject.delegates_ = new HashMap<String, RemoteObjectDelegate>();

						// put delegate in delegate map of new object
						newObject.delegates_.put(delegateToAdd.getClass().getSimpleName(), delegateToAdd);

						// register all delegates that are related to collection of same object type
						// This will be true if the list of this object is already registered
						if( objects_.get(collectionURL) != null){
							
							for(RemoteObjectDelegate aDelegate : ((Map<String, RemoteObjectDelegate>) objects_.get(collectionURL)).values()){
								newObject.delegates_.put(aDelegate.getClass().getSimpleName(), aDelegate);
							}
							
						}

						// Add new object in cache
						objects_.put(uri, newObject);

					} else {

						Log.d("RemoteObject: ", " Adding Remote object ");

						// loaded remotely
						// If object is loaded remotely and also exists in cache 
						if( objects_.get(uri) != null){
							
							Log.d("RemoteObject: ", " Updating Remote object in Cache");

							// update the loaded object in cache
							// load from cache - delegates already part of it
							RemoteObject oldObject = (RemoteObject) objects_.get(uri);

							// copy all delegates that are already part of this object
							newObject.delegates_.putAll(oldObject.delegates_);
							
							// put delegate in delegate map of new object
							newObject.delegates_.put(delegateToAdd.getClass().getSimpleName(), delegateToAdd);

							// Add new object in cache
							objects_.put(uri, newObject);
						
						} else if( objects_.get(collectionURL) != null ){
							
							Log.d("RemoteObject: ", " Adding Remote object in Cache because of List Registered ");

							Map<String, RemoteObjectDelegate> collectionDelegate = 
								(Map<String, RemoteObjectDelegate>) objects_.get(collectionURL);

							// copy all list delegates to this object
							newObject.delegates_.putAll(collectionDelegate);
							
							// put delegate in delegate map of new object
							newObject.delegates_.put(delegateToAdd.getClass().getSimpleName(), delegateToAdd);

							// Add new object in cache
							objects_.put(uri, newObject);
						
						}
					}
					
					// Save object in database
					_dataSource.saveOrUpdate(email, this);
										
					// return the list 
					//return _dataSource.fetch(this);
					return newObject;

				}catch(DataSourceException e){
					// If exception occurred while saving data then return the error
					return e.getMessage();
				}
				
				// If request type is GET
			} else if ( requestType == CoreHttpClient.GET_TYPE ){
			
				id = (String)params[1];

				// First try load data from cache
				toRet = getCacheObject(id);
				
				// if not found in cache
				// Check in local database
				if( toRet == null ){
					
					toRet = getLocalObject(id, (Class<T>) params[0]);					
				}
				
				// if not found in local database
				// Check in local database
				if( toRet == null ){
					
					toRet = getRemoteObject(id, (Class<T>) params[0]);					
				}
			}

		}catch(DataSourceException e){
			return e.getMessage();
		}

		return toRet;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private Object getCacheObject(String id){
	
		List<T> remoteObjectList = null;
		
		// If id is equal to Collection URL
		if(id.equals(collectionURL)){
			
			// Check if list entry in present in cache
			if( objects_.get(id) == null ){

				// If not exist then return null so that all object will be loaded in cache list from database
				return null;
				
			} else {
				
				// If list url exists in cache. It means all data in database is loaded in cache
				// initiate list
				remoteObjectList = new ArrayList<T>();
				
				// iterate over list of objects
				for (String key : objects_.keySet()) {
					
					// If id starts with the list id.
					// don't load the object that is mapped with collection url !key.equals(id)
					// Since each object of the list starts with list url
					if( key.startsWith(id) && !key.equals(id)){
						
						// Add object in list
						remoteObjectList.add((T)objects_.get(key));
			
					} else {
						
						// get delegate map - this map is for collection type
						delegates_ = (HashMap<String, RemoteObjectDelegate>) objects_.get(key);
						// add this delegate in the list
						delegates_.put(delegateToAdd.getClass().getSimpleName(),delegateToAdd);
					
					}
				}
				
				return remoteObjectList;
			}			
			
		} else {
			
			// Check in cache
			Object toRet = objects_.get(id);
			
			return toRet;
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws DataSourceException
	 */
	private Object getLocalObject(String id, Class<T> typeClass) throws DataSourceException{
		
		List<RemoteObject> remoteObjectList = null;
		
		// If id is equal to Collection URL
		if(id.equals(collectionURL)){
			
			String className = type;
			//String collectionClassName = className + "Collection";

			// Update class type name for list
			//paramMap.put("type", collectionClassName);
			type = className + "Collection";
			
			// Check if list entry in present in local db
			List<RemoteObject> listRemoteObject = _dataSource.fetch(this);
			
			if( listRemoteObject == null || listRemoteObject.size() <= 0){

				// If not exist then return null so that all object will be loaded from server an saved in to database
				return null;
				
			} else {
				
				// If list url exists in database. It means all data in database is loaded from server								
				//paramMap.put("type", className);
				type = className;
				remoteObjectList = (List<RemoteObject>) _dataSource.fetch(this);
				
				// fill up cache
				for(RemoteObject object : remoteObjectList){
					
					object.delegates_ = new HashMap<String, RemoteObjectDelegate>();
					object.delegates_.put(delegateToAdd.getClass().getSimpleName(),delegateToAdd);
					objects_.put(object.uri, object);
				}
				
				Map<String, RemoteObjectDelegate> collectionDelegate  = null;
				
				if( objects_.get(collectionURL) != null ){
					collectionDelegate = (Map<String, RemoteObjectDelegate>) objects_.get(collectionURL);
				} else {
					collectionDelegate = new HashMap<String, RemoteObjectDelegate>();
				}

				collectionDelegate.put(delegateToAdd.getClass().getSimpleName(),delegateToAdd);
				delegates_.put(delegateToAdd.getClass().getSimpleName(), delegateToAdd);
				objects_.put(collectionURL, collectionDelegate);

				return remoteObjectList;
			}
			
		} else {
			
			// Check in local database
			Object toRet = _dataSource.fetchById(this);
			
			return toRet;
		}
	}
	
	/**
	 * Client will call this method to get the object from remote location.
	 * 
	 * @param url
	 *            is the url from where the data will return
	 * @param type
	 *            is the List class type of object that will be returned by url
	 * @return the object from remote location
	 */
	protected Object getRemoteObject(String url, Type type) {

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
	 * @param url
	 *            is the url from where the data will return
	 * @param type
	 *            is the class type of object that will be returned by url
	 * @return the object from remote location
	 */
	protected Object getRemoteObject(String url, Class<T> type) {

		CoreHttpClient http = new CoreHttpClient();
		RemoteObject remoteObject = null;
		String response = null;

		try {

			// Get response string
			response = http.executeGet(url);

			// Deserialize the string into object of given type
			remoteObject = (RemoteObject) JsonController.Deserialize(response, type);

		} catch (ServerException e) {
			e.printStackTrace();
		}

		// Return remote object
		return remoteObject;
	}

	/**
	 * 
	 * @param url
	 * @param postMessage
	 * @return
	 */
	protected Object postRemoteObject(String url, RemoteObject remoteObject) {

		CoreHttpClient http = new CoreHttpClient();
		String response = null;

		try {

			// serialize the string into object of given type
			String postMessage = JsonController.Serialize(remoteObject);

			// Get response string
			response = http.executePost(url, postMessage);

		} catch (ServerException e) {
			e.printStackTrace();
		}

		// Return remote object
		return response;
	}
	
	/**
	 * local json
	 */
	public String json;
	
	/**
	 * base json
	 */
	public String baseJson;
	
	/**
	 * Uri that is used as key
	 */
	public String uri;
	
	/**
	 * Email that is set from session
	 */
	public String email;
	
	/**
	 * Type of the class
	 */
	public String type;
	
	/**
	 * last update string
	 */
	public String lastUpdated;
	
	/**
	 * last synced string
	 */
	public String lastSynced;
	
	/**
	 * collection url used to identify the collection request
	 */
	public String collectionURL;
	
	/**
	 * data source object
	 */
	public IDataSource _dataSource;

	/*
	 * request type GET or POST
	 */
	public int requestType;
	
	/**
	 * Map of Delegates to ignore duplicates
	 */
	protected HashMap<String, RemoteObjectDelegate> delegates_ = new HashMap<String, RemoteObjectDelegate>();

	/**
	 * List of Objects
	 */
	static protected Map<String, Object> objects_ = new HashMap<String, Object>();

	/**
	 * Delegate instance to be set here so that it can be added later
	 */
	private RemoteObjectDelegate delegateToAdd;
	
}