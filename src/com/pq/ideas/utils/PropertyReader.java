package com.pq.ideas.utils;

import java.util.ResourceBundle;

/**
 * Retrieve the value from the properties file.
 * 
 * @author kvillaca
 *
 */
public class PropertyReader {
	
	/*
	 * If the app end having other properties file, then we can create a file just with file names
	 * and using the same method, retrieve the file name and then retrieve the value from the right
	 * propValueToReturn
	 */ 
	private static final String MESSAGES = "messages";
	
	/**
	 * Get properties value from an int value, if exist a properties file that has integers numbers
	 * as keys
	 * @param codeInteger
	 * @return
	 */
	public static String getPropertyValue(int codeInteger) {
		String propValueToReturn = null;
		if (codeInteger > 0) {
			final String integerAsStr = Integer.toString(codeInteger);
			if (integerAsStr != null && integerAsStr.trim().length() > 0) {
				propValueToReturn = getPropertyValue(integerAsStr);
			}
		}
		return propValueToReturn;
	}
	
	
	/**
	 * Get properties value from a String
	 * 
	 * @param keyString
	 * @return
	 */
	public static String getPropertyValue(final String keyString) {
		String propValueToReturn = null;
		ResourceBundle rb = ResourceBundle.getBundle(MESSAGES);
		
		if (rb == null) {
			propValueToReturn = null;
		} else {
			propValueToReturn = rb.getString(keyString);
		}
		return propValueToReturn;
	}

}
