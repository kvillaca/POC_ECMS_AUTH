package com.pq.ideas.pojos;

import java.io.Serializable;

public class CacheTokenPojo implements Serializable {

	
	private static final long serialVersionUID = 4832060541665768483L;
	
	// Time as milliseconds, that's when the request has happened
	private int timeKey;
	
	// REST URL that the user is calling
	private String urlCalled;
	
	// Token as base64 
	private String tokenStr;
	
	// Value received from the header with the user local environment value, basically the user id.
	private String userEnvironmentValue;
	
	
	public CacheTokenPojo() {
		super();
	}
	
	public CacheTokenPojo(int timeKey, String urlCalled, String tokenStr) {
		super();
		this.timeKey = timeKey;
		this.urlCalled = urlCalled;
		this.tokenStr = tokenStr;
	}
	
	public CacheTokenPojo(int timeKey, String urlCalled, String tokenStr, String userEnvironmentValue) {
		super();
		this.timeKey = timeKey;
		this.urlCalled = urlCalled;
		this.tokenStr = tokenStr;
		this.userEnvironmentValue = userEnvironmentValue;
	}

	public int getTimeKey() {
		return timeKey;
	}

	public void setTimeKey(int timeKey) {
		this.timeKey = timeKey;
	}

	public String getUrlCalled() {
		return urlCalled;
	}

	public void setUrlCalled(String urlCalled) {
		this.urlCalled = urlCalled;
	}

	public String getTokenStr() {
		return tokenStr;
	}

	public void setTokenStr(String tokenStr) {
		this.tokenStr = tokenStr;
	}

	public String getUserEnvironmentValue() {
		return userEnvironmentValue;
	}

	public void setUserEnvironmentValue(String userEnvironmentValue) {
		this.userEnvironmentValue = userEnvironmentValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (timeKey ^ (timeKey >>> 32));
		result = prime * result + ((tokenStr == null) ? 0 : tokenStr.hashCode());
		result = prime * result + ((urlCalled == null) ? 0 : urlCalled.hashCode());
		result = prime * result + ((userEnvironmentValue == null) ? 0 : userEnvironmentValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheTokenPojo other = (CacheTokenPojo) obj;
		if (timeKey != other.timeKey)
			return false;
		if (tokenStr == null) {
			if (other.tokenStr != null)
				return false;
		} else if (!tokenStr.equals(other.tokenStr))
			return false;
		if (urlCalled == null) {
			if (other.urlCalled != null)
				return false;
		} else if (!urlCalled.equals(other.urlCalled))
			return false;
		if (userEnvironmentValue == null) {
			if (other.userEnvironmentValue != null)
				return false;
		} else if (!userEnvironmentValue.equals(other.userEnvironmentValue))
			return false;
		return true;
	}
	

}
