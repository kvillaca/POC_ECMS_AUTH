package com.pq.ideas.pojos;

import java.io.Serializable;

public class DummyPojoForTests implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String xmlString;
	private String message;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getXmlString() {
		return xmlString;
	}


	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


//	@Override
//	public String toString() {
//		return "{\"DummyPojoForTests\": {{\"userId\" : \"" + userId + "\"}, {\"xmlString\" : \"" + xmlString + "\"}, {\"message\" : \"" + message + "\"}}}";
//	}

}
