package com.pq.ideas.pojos;

import java.io.Serializable;


/**
 * Container for general metadata to be captured for the user as extracted from Active Directory
 * 
 * @author fperez
 * 
 */
// @JsonDeserialize(builder = ActiveDirectoryMetadata.Builder.class)
public class ActiveDirectoryMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String samAccountName;
	private String userPrincipalName;
	private String primaryEmailAddress;
	private String phoneNumber;
	private String localeId;
	private String managerName;
	private String department;

	public ActiveDirectoryMetadata() {
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSamAccountName() {
		return samAccountName;
	}

	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getLocaleId() {
		return localeId;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getDepartment() {
		return department;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSamAccountName(String samAccountName) {
		this.samAccountName = samAccountName;
	}

	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ActiveDirectoryMetadata [firstName=").append(firstName)
				.append(", lastName=").append(lastName)
				.append(", samAccountName=").append(samAccountName)
				.append(", userPrincipalName=").append(userPrincipalName)
				.append(", primaryEmailAddress=").append(primaryEmailAddress)
				.append(", phoneNumber=").append(phoneNumber)
				.append(", localeId=").append(localeId)
				.append(", managerName=").append(managerName)
				.append(", department=").append(department).append("]");
		return builder.toString();
	}

}
