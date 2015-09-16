package com.pq.ideas.enums;

/**
 * 
 * @author kvillaca
 * 
 * Dummy enum to have some user status.
 * 
 * The right thing to do is get it from LDAP, SSO or anyother Identity manager
 *
 */
public enum StatusUser {
	
	ACTIVE("active"), OK("ok"), REVOKED("revoked"), SUSPENDED("suspended"), CANCELED("canceled"), DISABLED("disabled");
	
	private String status;
	
	private StatusUser(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

}
