package com.pq.ideas.pojos;

import java.io.Serializable;

public class ExternalLoginData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String password;
	private String uri;
	
	public ExternalLoginData() {
		super();
	}
	
	
	public ExternalLoginData(String userId, String password, String uri) {
		super();
		this.userId = userId;
		this.password = password;
		this.uri = uri;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}

}
