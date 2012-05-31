package com.riksof.a320.http;

import java.util.HashMap;

public class Cache {

	private static HashMap<String, String> cacheMap = new HashMap<String, String>();
    
    private static Cache INSTANCE = null;
    
	private Cache() {
	}
    
    public static Cache getInstance() {
        if(INSTANCE == null) createInstance();
        
        return INSTANCE;
    }
    
    private synchronized static void createInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Cache();
        }
    }
    public String get(String url) {
		return cacheMap.get(url);
	}

	public String put(String url, String response) {
		return cacheMap.put(url, response);
	}

	public void remove(String url) {
		cacheMap.remove(url);
	}
}
