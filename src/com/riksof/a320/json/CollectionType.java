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

	public Type getCollectionType(){
		return super.getType();
	}

}
