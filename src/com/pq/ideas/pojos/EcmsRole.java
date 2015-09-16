package com.pq.ideas.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a user role in ECMS
 * 
 * @author fperez
 * 
 */
// @JsonDeserialize(builder = EcmsRole.Builder.class)
public class EcmsRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private String roleName;
	private String assignerId;
	private Date dateAssigned;

	public EcmsRole() {
	}

	public String getRoleName() {
		return roleName;
	}

	public String getAssignerId() {
		return assignerId;
	}

	public Date getDateAssigned() {
		return dateAssigned;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setAssignerId(String assignerId) {
		this.assignerId = assignerId;
	}

	public void setDateAssigned(Date dateAssigned) {
		this.dateAssigned = dateAssigned;
	}

	@Override
	public String toString() {
		return "EcmsRole [roleName=" + roleName + ", assignerId=" + assignerId + ", dateAssigned=" + dateAssigned + "]";
	}

}
