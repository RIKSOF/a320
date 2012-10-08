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

package com.riksof.a320.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This is the main class for Serializing and Deserializing a json and object respectively.
 * JsonController is typically used by invoking {@link #Serialize(Object)} or {@link #Deserialize(String, Class)} 
 * or {@link #Deserialize(String, Type)} methods on it statically.
 *  
 * @author rizwan
 *
 */
public class JsonController {

	/**
	 * This method serializes the specified object into its equivalent Json
	 * representation. This method should be used when the specified object is
	 * not a generic type.
	 * 
	 * @param o
	 * @return
	 */
	public static String Serialize(Object o) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
				//.excludeFieldsWithoutExposeAnnotation().create();
		String content = gson.toJson(o);

		return content;
	}

	/**
	 * This method deserializes the specified Json into an object of the
	 * specified class. It is not suitable to use if the specified class is a
	 * generic type since it will not have the generic type information because
	 * of the Type Erasure feature of Java. Therefore, this method should not be
	 * used if the desired type is a generic type. Note that this method works
	 * fine if the any of the fields of the specified object are generics, just
	 * the object itself should not be a generic type.
	 * 
	 * @param <T>
	 * @param str
	 * @param classOfT
	 * @return
	 */
	public static <T> T Deserialize(String str, Class<T> classOfT) {
		Gson gson = new GsonBuilder().create();
					//.excludeFieldsWithoutExposeAnnotation().create();
		return gson.fromJson(str, classOfT);
	}

	/**
	 * This method deserializes the specified Json into an object of the
	 * specified type. This method is useful if the specified object is a
	 * generic type. 
	 * 
	 * @param <T> the type of the desired object
	 * @param json the string from which the object is to be deserialized
	 * @param listType The specific genericized type of src.
	 *
	 * @return
	 */
	public static <T> T Deserialize(String json, Type listType) {
		try {
			Gson gson = new GsonBuilder().create();
					//.excludeFieldsWithoutExposeAnnotation().create();
			return gson.fromJson(json, listType);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
