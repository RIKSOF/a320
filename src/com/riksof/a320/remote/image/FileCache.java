package com.riksof.a320.remote.image;

import java.io.File;
import android.content.Context;
import android.util.Log;

public class FileCache {
    
	private String TAG = "FileCache";
	
    private File cacheDir;
    
    public FileCache(Context context){

    	Log.i(TAG, "FileCache Constructor");

        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getFile(String url){

    	Log.i(TAG, "Get File");

        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
        
    }
    
    public void clear(){

    	Log.i(TAG, "Clear");

        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}