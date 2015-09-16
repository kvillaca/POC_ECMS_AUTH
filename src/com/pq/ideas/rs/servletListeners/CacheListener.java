package com.pq.ideas.rs.servletListeners;

import java.net.InetSocketAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pq.ideas.enums.ErrorMessagesKeys;
import com.pq.ideas.utils.MemcachedSetupValues;
import com.pq.ideas.utils.PropertyReader;


/** 
 * Cache initializer
 * 
 * @author kvillaca
 *
 */
public class CacheListener implements ServletContextListener {

	final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private static MemcachedClient memClient;


	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		// Clean and close all caches
		memClient.shutdown();
		memClient = null;
	}


	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			memClient = new MemcachedClient(new InetSocketAddress(MemcachedSetupValues.getDefaultHost(), MemcachedSetupValues.getDefaultPort()));
		} catch (Exception e) {
			// Service down, something went really bad or doesn't exist the memcached installed...
			LOG.info(PropertyReader.getPropertyValue(ErrorMessagesKeys.CACHE_FAIL_TO_CREATE.toString()));
			LOG.info("Localized message: " + e.getLocalizedMessage() + " | message" + e.getMessage());
		}
	}


	public static MemcachedClient getMemClient() {
		return memClient;
	}


	public static void setMemClient(MemcachedClient memClient) {
		CacheListener.memClient = memClient;
	}
}
