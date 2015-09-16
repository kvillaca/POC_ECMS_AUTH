package com.pq.ideas.cache;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.pojos.CacheTokenPojo;
import com.pq.ideas.rs.servletListeners.CacheListener;
import com.pq.ideas.token.HashGenerator;
import com.pq.ideas.utils.PropertyReader;

public class CacheUtils {

	final static Logger LOG = LoggerFactory.getLogger(CacheUtils.class.getClass());
	private static final int MINUTE = 60;

	
	/**
	 * Set the token in the cache
	 * 
	 * @param tokenStr
	 * @param cachePojo
	 * @return
	 */
	public static boolean setCachePojoObject(final String tokenStr, final CacheTokenPojo cachePojo) {
		final String timeOutString = PropertyReader.getPropertyValue(TokenDetailsKeys.TIMEOUT_IN_MINUTES.toString());
		final int timeOutDefault = Integer.parseInt(timeOutString);

		final String maxTryForCacheString = PropertyReader.getPropertyValue(TokenDetailsKeys.CACHE_MAX_NUMBER_OF_TRY.toString());
		final int maxTryForCache = Integer.parseInt(maxTryForCacheString);

		boolean isSuccess = false;
		boolean alreadyExistInCache = false;
		
		int tryCounter = 0;
		int timeAsSeconds = timeOutDefault * MINUTE;
		while (!isSuccess || tryCounter > maxTryForCache) {
			try {
				// As memcached just accept 250 chars and the token has more than 250 chars, I'm generating a 64bits hash as the key!
				final String hash64bits = HashGenerator.getHash64(tokenStr);
				alreadyExistInCache = addIfNotExist(hash64bits, timeAsSeconds, cachePojo);
				
				// Just to keep looping up to get a response from the cache
				isSuccess = true;
			} catch (Exception e) {
				LOG.info("Cache canceled, " + e.getLocalizedMessage() + " | " + e.getMessage());
			}
			tryCounter++;
		}
		return alreadyExistInCache;
	}


	/**
	 * Add token data in the cache if it doesn't exist, other else return true that will invalidate the token!
	 * 
	 * @param key
	 * @param timeInSeconds
	 * @param bean
	 * @return
	 * @throws IOException
	 */
	public static boolean addIfNotExist(final String key, final int timeInSeconds, final Object bean) throws IOException {
		boolean existInCache = false;
		final Object objectToReturn = CacheListener.getMemClient().get(key);
		if (objectToReturn == null) {
			CacheListener.getMemClient().add(key, 3000, bean);
			System.out.println(" Test " + ((CacheTokenPojo)CacheListener.getMemClient().get(key)).getUserEnvironmentValue());
		} else {
			existInCache = true;
		}
		return existInCache;
	}

}
