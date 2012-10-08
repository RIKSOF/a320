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

import java.util.HashMap;

/**
 * The Cache class. Maps keys to values. Strings can be used as a key and/or
 * value to store responses against URL. This is very similar to the Hashtable
 * class, except that after putting an object into the Cache, it is not
 * guaranteed that a subsequent get will return it. The Cache will automatically
 * remove entries.
 * 
 * @author rizwan
 */
public class Cache {

	// The hash map that will contain all cache data.
	private static HashMap<String, String> cacheMap = new HashMap<String, String>();

	// Cache class Instance
	private static Cache INSTANCE = null;

	// Constructs a new, empty Cache
	private Cache() {
	}

	/**
	 * Get Cache instance
	 * 
	 * @return
	 */
	public static Cache getInstance() {
		if (INSTANCE == null)
			createInstance();

		return INSTANCE;
	}

	/**
	 * Create Cache instance
	 */
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Cache();
		}
	}

	/**
	 * Gets the object associated with the specified key in the Cache.
	 * 
	 * @param url
	 *            the key in the hash table
	 * @return the element for the key or null if the key is not defined in the
	 *         hash table.
	 */
	public String get(String url) {
		return cacheMap.get(url);
	}

	/**
	 * Puts the specified element into the Cache, using the specified key. The
	 * element may be retrieved by doing a get() with the same key. The key and
	 * the element cannot be null
	 * 
	 * @param url
	 *            the specified hashtable key
	 * @param response
	 *            the specified element
	 * @return
	 */
	public String put(String url, String response) {
		return cacheMap.put(url, response);
	}

	/**
	 * Removes the element corresponding to the key. Does nothing if the key is
	 * not present
	 * 
	 * @param url
	 *            the key that needs to be removed
	 */
	public void remove(String url) {
		cacheMap.remove(url);
	}
}
