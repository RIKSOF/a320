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

package com.riksof.a320.model;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riksof.a320.remote.RemoteObject;

/**
 * This class is the parent for all objects that are retrieved from the server.
 * 
 * @author rizwan
 */
public abstract class ObjectModel<T> extends RemoteObject {

	/**
	 * Build a json string from this object.
	 * 
	 * @param list
	 *            is the list of ObjectModel
	 * @param listType
	 *            is the type of Object Model
	 * @return the json string of an object
	 */
	public String encode(List<T> list, Type listType) {

		// Initialize a Gson object
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.excludeFieldsWithoutExposeAnnotation().create();

		// Create json string
		String content = gson.toJson(list, listType);

		return content;
	}

	/**
	 * Initialize the object from a json string.
	 * 
	 * @param json
	 *            is the json string
	 * @param listType
	 *            is the type to which the json string has to be decoded
	 * @return the list of object
	 */
	public List<T> decode(String json, Type listType) {

		// Initialize a Gson object
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		// return object
		return gson.fromJson(json, listType);
	}

	public abstract void serializeToParameters();

	public abstract Object toDocumentWithRoot(String node);
}
