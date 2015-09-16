package com.pq.ideas.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pq.ideas.enums.StatusUser;


/**
 * This is a container for information regarding a user in ECMS. It supports information gathered from Active Directory (AD) as well as ECMS-specific
 * information.
 * 
 * @author fperez
 * 
 */
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CURRENT_VERSION = "v1.0";

	private String version;
	private String ecmsUserId;
	private String activeDirectoryId;
	private String username;
	private String firstName;
	private String lastName;
	private StatusUser status;
	private ActiveDirectoryMetadata activeDirectoryMetadata;
	private List<EcmsRole> ecmsRoles;
	private Date dateUserCreated;
	private List<UserLoginData> mostRecentLogins;

	public UserInfo() {
		version = CURRENT_VERSION;
		ecmsRoles = new ArrayList<EcmsRole>();
		mostRecentLogins = new ArrayList<UserLoginData>();
		status = StatusUser.ACTIVE;
	}

	/**
	 * Gets the version of the UserInfo structure
	 * 
	 * @return the version number
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Gets the unique ECMS user ID value
	 * 
	 * @return the ecms user id
	 */
	public String getEcmsUserId() {
		return ecmsUserId;
	}

	/**
	 * Gets the active directory ID
	 * 
	 * @return the active directory ID
	 */
	public String getActiveDirectoryId() {
		return activeDirectoryId;
	}

	/**
	 * Gets the username
	 * 
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the firstName
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the lastName
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the user status within the ECMS system
	 * 
	 * @return the user status
	 */
	public StatusUser getStatus() {
		return status;
	}

	/**
	 * Gets the active directory metadata
	 * 
	 * @return the active directory metadata.
	 */
	public ActiveDirectoryMetadata getActiveDirectoryMetadata() {
		return activeDirectoryMetadata;
	}

	/**
	 * Gets the list of ECMS roles the user has been assigned.
	 * 
	 * @return list of ECMS roles.
	 */
	public List<EcmsRole> getEcmsRoles() {
		return ecmsRoles;
	}

	/**
	 * Gets the date the user was created.
	 * 
	 * @return
	 */
	public Date getDateUserCreated() {
		return dateUserCreated;
	}

	/**
	 * Gets the most recent logins of the user into ECMS.
	 * 
	 * @return
	 */
	public List<UserLoginData> getMostRecentLogins() {
		return mostRecentLogins;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setEcmsUserId(String ecmsUserId) {
		this.ecmsUserId = ecmsUserId;
	}

	public void setActiveDirectoryId(String activeDirectoryId) {
		this.activeDirectoryId = activeDirectoryId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setStatus(StatusUser status) {
		this.status = status;
	}

	public void setActiveDirectoryMetadata(ActiveDirectoryMetadata activeDirectoryMetadata) {
		this.activeDirectoryMetadata = activeDirectoryMetadata;
	}

	public void setEcmsRoles(List<EcmsRole> ecmsRoles) {
		if (null != ecmsRoles)
			this.ecmsRoles.addAll(ecmsRoles);
	}

	public void setDateUserCreated(Date dateUserCreated) {
		this.dateUserCreated = dateUserCreated;
	}

	public void setMostRecentLogins(List<UserLoginData> mostRecentLogins) {
		if (null != mostRecentLogins)
			this.mostRecentLogins.addAll(mostRecentLogins);
	}

	public void addEcmsRole(EcmsRole role) {
		ecmsRoles.add(role);
	}

	public void addMostRecentLogin(UserLoginData ulData) {
		mostRecentLogins.add(ulData);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInfo [version=").append(version).append(", ecmsUserId=").append(ecmsUserId).append(", activeDirectoryId=").append(activeDirectoryId).append(", username=").append(username).append(", status=").append(status).append(", activeDirectoryMetadata=").append(activeDirectoryMetadata).append(", ecmsRoles=").append(ecmsRoles).append(", dateUserCreated=").append(dateUserCreated).append(", mostRecentLogins=").append(mostRecentLogins).append("]");
		return builder.toString();
	}

}
