package com.pq.ideas.pojos;

import java.io.Serializable;
import java.util.List;

import com.pq.ideas.enums.StatusUser;

public class TokenPojo implements Serializable {

	private static final long serialVersionUID = -3602864815532675948L;
	private String userId;
	private String firstName;
	private String lastName;
	private String userEmail;
	private long dateTime;
	private List<String> roles;
	private List<String> groups;
	private String samUser;
	private StatusUser userStatus;
	

	public TokenPojo() {
		super();
	}


	public TokenPojo(String userId, String firstName, String lastName, String userEmail, long dateTime,
			List<String> roles, List<String> groups, String samUser, StatusUser userStatus) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.dateTime = dateTime;
		this.roles = roles;
		this.groups = groups;
		this.samUser = samUser;
		this.userStatus = userStatus;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public long getDateTime() {
		return dateTime;
	}


	public void setDateTime(long dateTime) {
		this.dateTime = dateTime;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public List<String> getGroups() {
		return groups;
	}


	public void setGroups(List<String> groups) {
		this.groups = groups;
	}


	public String getSamUser() {
		return samUser;
	}


	public void setSamUser(String samUser) {
		this.samUser = samUser;
	}


	public StatusUser getUserStatus() {
		return userStatus;
	}


	public void setUserStatus(StatusUser userStatus) {
		this.userStatus = userStatus;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dateTime ^ (dateTime >>> 32));
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((samUser == null) ? 0 : samUser.hashCode());
		result = prime * result + ((userEmail == null) ? 0 : userEmail.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userStatus == null) ? 0 : userStatus.hashCode());
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
		TokenPojo other = (TokenPojo) obj;
		if (dateTime != other.dateTime)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (groups == null) {
			if (other.groups != null)
				return false;
		} else if (!groups.equals(other.groups))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		if (samUser == null) {
			if (other.samUser != null)
				return false;
		} else if (!samUser.equals(other.samUser))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userStatus != other.userStatus)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "TokenPojo [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userEmail=" + userEmail + ", dateTime=" + dateTime + ", roles=" + roles + ", groups=" + groups + ", samUser="
				+ samUser + ", userStatus=" + userStatus + "]";
	}

}
