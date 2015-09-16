package com.pq.ideas.dummy;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

public class CreateDummyGroupsRoles {
	
	/**
	 * Dummy groups, the user is passed just to satisfy the method signature
	 * that will be needed later on for query the DB
	 * 
	 * @param user
	 * @return
	 */
	public static List<String> getGroupsForTheUser(final String user) {
		List<String> groupsToReturn = new ArrayList<String>();
		groupsToReturn.add("main");
		groupsToReturn.add("supervisor");
		return groupsToReturn;
	}
	
	public static String getGroupsForTheUserToString(final String user) throws Exception {
		final List<String> groups = getGroupsForTheUser(user);
		final ObjectMapper mapper = new ObjectMapper();
		final String groupsStr = mapper.writeValueAsString(groups);
		return groupsStr;
	}
	
	
	/**
	 * Dummy roles, the user is passed to satisfy the method signature
	 * that will be needed later on for query the DB
	 * 
	 * @param user
	 * @return
	 */
	public static List<String> getRolesForTheUser(final String user) {
		List<String> rolesToReturn = new ArrayList<String>();
		rolesToReturn.add("supervisor");
		return rolesToReturn;
	}
	
	public static String getRolesForTheUserToString(final String user) throws Exception {
		final List<String> roles = getRolesForTheUser(user);
		final ObjectMapper mapper = new ObjectMapper();
		final String rolesStr = mapper.writeValueAsString(roles);
		return rolesStr;
	}
	
}
