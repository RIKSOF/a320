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
import com.google.gson.reflect.TypeToken;

/**
 * This is the wrapper over the TypeToken class of Gson library.
 * User can use this class to create the type of object
 *  
 * @author rizwan
 *
 * @param <T>
 */
public class CollectionType<T> extends TypeToken<T>{

	// Returns the Collection type
	public Type getCollectionType(){
		return super.getType();
	}

}
