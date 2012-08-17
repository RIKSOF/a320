package com.riksof.a320.model;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ObjectModel<T> {

	public String encode(List<T> list, Type listType) {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.excludeFieldsWithoutExposeAnnotation().create();
		String content = gson.toJson(list, listType);

		return content;
	}

	public List<T> decode(String json, Type listType) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		return gson.fromJson(json, listType);
	}

	public abstract void serializeToParameters();

	public abstract Object toDocumentWithRoot(String node);
}
