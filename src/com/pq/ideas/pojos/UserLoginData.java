package com.pq.ideas.pojos;

import java.io.Serializable;
import java.util.Date;


/**
 * Contains info on a successful login attempt by an ECMS user
 * 
 * @author fperez
 * 
 */
public class UserLoginData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Date loginTime;
	private String ipAddress;
	private String browserUserAgent;

	public UserLoginData() {
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getBrowserUserAgent() {
		return browserUserAgent;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLoginData [loginTime=").append(loginTime)
				.append(", ipAddress=").append(ipAddress)
				.append(", browserUserAgent=").append(browserUserAgent)
				.append("]");
		return builder.toString();
	}

}
