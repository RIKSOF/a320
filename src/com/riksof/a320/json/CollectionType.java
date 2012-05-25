package com.riksof.a320.json;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public class CollectionType<T> extends TypeToken<T>{

	public Type getCollectionType(){
		return super.getType();
	}

}
