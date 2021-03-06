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

package com.riksof.a320.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * This is a utility class that contains utility functions in it
 * 
 * @author rizwan
 *
 */
public class Utils {

	/**
	 * Reads content from input stream and write it to output stream
	 * 
	 * @param is is input stream
	 * @param os is output stream
	 */
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public static String getUniqueId() {  
		  return UUID.randomUUID().toString();   
	}  
	
}