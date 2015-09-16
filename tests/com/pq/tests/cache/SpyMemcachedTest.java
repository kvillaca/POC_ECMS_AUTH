package com.pq.tests.cache;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class SpyMemcachedTest {

	public static void main(String[] args) throws IOException {
		MemcachedClient c = new MemcachedClient(new InetSocketAddress("localhost", 11211));

	    c.set("someKey", 3600, "Test");

	    String cachedUser = (String) c.get("someKey");
	    
	    System.out.println(cachedUser);

	}

}
