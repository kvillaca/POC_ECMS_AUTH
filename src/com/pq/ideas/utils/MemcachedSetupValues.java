package com.pq.ideas.utils;

public class MemcachedSetupValues {
	
	private static final String DEFAULT_LOCAL_HOST = "localhost";
	private static final int DEFAULT_PORT = 11211;
	
	public static String getDefaultHost() {
		return DEFAULT_LOCAL_HOST;
	}

	
	public static int getDefaultPort() {
		return DEFAULT_PORT;
	}
}
