package com.pq.ideas.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.pq.ideas.enums.StatusUser;
import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.exceptions.POCException;
import com.pq.ideas.pojos.ActiveDirectoryMetadata;
import com.pq.ideas.pojos.ExternalLoginData;
import com.pq.ideas.pojos.TokenPojo;
import com.pq.ideas.pojos.UserInfo;
import com.pq.ideas.token.EncodeDecodeAES;
import com.pq.ideas.token.TokenAuthenticator;
import com.pq.ideas.utils.PropertyReader;
import com.pq.ideas.utils.UserUtils;

public class CreateDummyHeaderLogin {

	
	/**
	 * Create a dummy login, with the login data
	 * 
	 * @param externalLogin
	 * @return
	 */
	public List<String> getDummyLoginData(final ExternalLoginData externalLogin) {
		List<String> toReturn = null;
		try {
			toReturn = validateUser(externalLogin);
		} catch (POCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}


	/**
	 * Validate user, create a LDAP dummy, check for dummy groups and roles to produce token object
	 *  
	 * @param externalLogin
	 * @return
	 * @throws POCException
	 */
	private List<String> validateUser(final ExternalLoginData externalLogin) throws POCException {
		List<String> returnValue = null;
		// I have implemented this on this way to prevent have to deploy the application again if the minimum passwod has changed
		int minPasswrodLength = Integer.parseInt(PropertyReader.getPropertyValue(TokenDetailsKeys.MINIMUM_PASSWORD_SIZE.toString()));
		if (externalLogin.getPassword().trim().length() >= minPasswrodLength) {
			UserInfo loginToReturn = queryLDAPDummy(externalLogin);

			if (loginToReturn != null) {
				// if we are just doing a Who Am I
				final String localUser = externalLogin.getUserId();
				final String samUserName = loginToReturn.getActiveDirectoryMetadata().getSamAccountName().toLowerCase();
				if (samUserName != null) {
					TokenAuthenticator tokenAuth = new TokenAuthenticator(externalLogin.getUri(), localUser);

					final TokenPojo tokenPojo = tokenAuth.getToken(null, loginToReturn);
					if (tokenPojo != null) {
						final ObjectMapper mapper = new ObjectMapper();
						try {
							returnValue = new ArrayList<String>();
							String tokenValue = mapper.writeValueAsString(tokenPojo);
							final String passwordChecked = UserUtils.checkPassword(localUser);
							tokenValue = EncodeDecodeAES.encrypt(tokenValue, passwordChecked);
							// Add token
							returnValue.add(tokenValue);

							final JSONArray jsonRoles = new JSONArray(tokenPojo.getRoles());
							// Add roles
							returnValue.add(jsonRoles.toString());
							returnValue.add(tokenPojo.getUserId());
						} catch (Exception e) {
							// TODO Throw a POC Exception to be defined
							e.printStackTrace();
						}
					}
					tokenAuth = null;
				}
			}
		}
		return returnValue;
	}
	
	
	/*
	 * Dummy UserInfo object!!!
	 */
	private UserInfo queryLDAPDummy(final ExternalLoginData externalLogin) {
		UserInfo ui = new UserInfo();
		final String user = externalLogin.getUserId();
		ui.setUsername(user);
		ui.setFirstName(user + " First Name");
		ui.setLastName(user + " Last Name");
		ui.setStatus(StatusUser.ACTIVE);
		ui.setVersion("1.0");
		ui.setDateUserCreated(new Date());
		
		ActiveDirectoryMetadata acm = new ActiveDirectoryMetadata();
		acm.setFirstName(ui.getFirstName());
		acm.setLastName(ui.getLastName());
		acm.setDepartment("IT");
		acm.setManagerName("Manager");
		acm.setPrimaryEmailAddress(user + "@proguqest.com");
		acm.setUserPrincipalName(user + " Principal");
		acm.setSamAccountName(user + " SamAccountName");
		
		ui.setActiveDirectoryMetadata(acm);
		return ui;
	}
}
