package com.pq.ideas.token;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pq.ideas.cache.CacheUtils;
import com.pq.ideas.dummy.CreateDummyGroupsRoles;
import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.exceptions.POCException;
import com.pq.ideas.pojos.CacheTokenPojo;
import com.pq.ideas.pojos.TokenPojo;
import com.pq.ideas.pojos.UserInfo;
import com.pq.ideas.utils.PropertyReader;
import com.pq.ideas.utils.UserUtils;

public class TokenAuthenticator {
	
	final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	private static final long DEFAULT_MINUTES_TOKEN = 15;
	private static final long MINUTE = 60 * 1000;
	private long sessionMinutesToken;
	private String uri;
	private String localUser;


	/**
	 * Constructor for the Token Authenticator, receiving the local user and the URI been called
	 */
	public TokenAuthenticator(final String uri, final String localUser) {
		super();
		this.uri = uri;
		this.localUser = localUser;
		setTokenSessionTimeOut();
	}

	
	/**
	 * Create a new token if login or validate and replace the token received if necessary
	 *
	 */
	public TokenPojo getToken(final String token, final UserInfo userInfo) throws POCException {
		TokenPojo tokenToReturn;

		if (token == null || token.trim().length() == 0 && userInfo != null) {
			tokenToReturn = createNewToken(userInfo);
		} else {
			tokenToReturn = replaceToken(token);
		}
		return tokenToReturn;
	}
	
	
	/**
	 * Set the token time out time im milliseconds, creating a default one if none present
	 * 
	 */
	private void setTokenSessionTimeOut() {
		final String minutesFromPropertiesStr = PropertyReader.getPropertyValue(TokenDetailsKeys.TIMEOUT_IN_MINUTES.toString());
		if (minutesFromPropertiesStr != null && minutesFromPropertiesStr.trim().length() > 0) {
			sessionMinutesToken = MINUTE * Integer.parseInt(minutesFromPropertiesStr);
		} else {
			// Time Default
			sessionMinutesToken = MINUTE * DEFAULT_MINUTES_TOKEN;
		}
	}


	/**
	 * Create a new token if the session is new, mean the user has logged with success It's not enough the user be validated agains LDAP, it
	 * also need be validate against POC UserRoles DB.
	 *
	 */
	private TokenPojo createNewToken(final UserInfo userInfo) throws POCException {
		TokenPojo tokenToBe = null;
		final List<String> roles = getUserRoles(userInfo);
		if (roles != null && roles.size() > 0) {
			final List<String> groups = getUserGroups(userInfo);
			final long actualDateTime = new Date().getTime();
			tokenToBe = new TokenPojo(localUser,
									  userInfo.getActiveDirectoryMetadata().getFirstName(), 
									  userInfo.getActiveDirectoryMetadata().getLastName(), 
									  userInfo.getActiveDirectoryMetadata().getPrimaryEmailAddress(), 
									  actualDateTime, 
									  roles, 
									  groups,
									  userInfo.getActiveDirectoryMetadata().getSamAccountName(), 
									  userInfo.getStatus());
		}
		return tokenToBe;
	}


	/**
	 * Check the token and update it to return to the user, 'keeping the session alive'
	 *
	 */
	public TokenPojo checkExistingToken(final String tokenStr) {
		TokenPojo token = parseToken(tokenStr);
		if (token != null) {
			final long actualDateTime = new Date().getTime();
			long calcTimeFromLastReqWithActualRequest = actualDateTime - token.getDateTime();
			if (calcTimeFromLastReqWithActualRequest > sessionMinutesToken) {
				// Means that the session has expired
				token = null;
			}
		}
		return token;
	}


	/**
	 * Replace the old token for a new one with new previous and actual times
	 *
	 */
	public TokenPojo replaceToken(final String tokenStr) {
		TokenPojo token = parseToken(tokenStr);
		if (token != null) {
			final long actualDateTime = new Date().getTime();
			final long calcTimeFromLastReqWithActualRequest = actualDateTime - token.getDateTime();
			token.setDateTime(actualDateTime);
			if (calcTimeFromLastReqWithActualRequest > sessionMinutesToken) {
				// Means that the session has expired
				token = null;
			}
		}
		return token;
	}


	/**
	 * Parse the token from JSON to TokenPojo object
	 *
	 */
	private TokenPojo parseToken(final String tokenStr) {
		final ObjectMapper mapper = new ObjectMapper();
		TokenPojo objFromToken = null;
		try {
			if (tokenStr != null && tokenStr.trim().length() > 0)
				objFromToken = mapper.readValue(tokenStr, TokenPojo.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return objFromToken;
	}


	/**
	 * ValidateToken from filter
	 *
	 */
	public String validateToken(String tokenStr) {
		String tokenStrToReturn = null;
		try {
			final String passwordChecked = UserUtils.checkPassword(localUser);
			final JSONObject oneObject = new JSONObject(tokenStr);
			if (oneObject.has(TokenDetailsKeys.TOKEN.toString())) {
				tokenStr = oneObject.getString(TokenDetailsKeys.TOKEN.toString());
				final String tokenDecrypted = EncodeDecodeAES.decrypt(tokenStr, passwordChecked);
				final TokenPojo authToken = checkExistingToken(tokenDecrypted);
				if (authToken != null) {
					authToken.setUserId(localUser);
//					if (addTokenToMapTokenList(authToken, tokenStr)) {
						final ObjectMapper mapper = new ObjectMapper();
						tokenStrToReturn = mapper.writeValueAsString(authToken);
						tokenStrToReturn = EncodeDecodeAES.encrypt(tokenStrToReturn, passwordChecked);
						oneObject.put(TokenDetailsKeys.TOKEN.toString(), tokenStrToReturn);
						tokenStrToReturn = oneObject.toString();
//					} else {
//						LOG.info("Duplicated token on " + (new Date()));
//					}
				}
			}
		} catch (Exception e) {
			// TODO re throw an POC exception as may be because the token or user is invalid.
			e.printStackTrace();
		}
		return tokenStrToReturn;
	}


	/**
	 * Check if against the cache to know if the token is valid or not
	 *
	 */
	private boolean addTokenToMapTokenList(final TokenPojo tokenAsPojo, final String tokenStr) {
		boolean isValidToken = true;

		// It has to always get the latest cache.
	    // Using the synchronous cache
		final int timeInSeconds = (int) (tokenAsPojo.getDateTime() / 1000);
		CacheTokenPojo cacheTokenPojo = new CacheTokenPojo(timeInSeconds, uri, tokenStr, localUser);
		if (CacheUtils.setCachePojoObject(tokenStr, cacheTokenPojo)) {
			isValidToken = false;
		} 

		return isValidToken;
	}


	/**
	 * Get a new encrypted token for the filter response.
	 *
	 */
	public String getNewToken(String token) {
		String newTokenToReturn = null;
		try {
			final String passwordChecked = UserUtils.checkPassword(localUser);
			JSONObject oneObject = new JSONObject();
			final String tokenDecrypted = EncodeDecodeAES.decrypt(token, passwordChecked);
			TokenPojo authToken = replaceToken(tokenDecrypted);
			if (authToken != null) {
				final ObjectMapper mapper = new ObjectMapper();
				newTokenToReturn = mapper.writeValueAsString(authToken);
				newTokenToReturn = EncodeDecodeAES.encrypt(newTokenToReturn, passwordChecked);
				oneObject.put(TokenDetailsKeys.TOKEN.toString(), newTokenToReturn);
				newTokenToReturn = oneObject.toString();// mapper.writeValueAsString(oneObject);
			}
		} catch (Exception e) {
			// TODO re throw an POC exception as the token my be empty or the user is invalid.
			e.printStackTrace();
		}
		return newTokenToReturn;
	}


	private List<String> getUserGroups(final UserInfo userLogged) {
		final List<String> groups = CreateDummyGroupsRoles.getGroupsForTheUser(userLogged.getActiveDirectoryMetadata().getSamAccountName());
		return groups;
	}


	/**
	 * Get User roles from the DB from the LDAP UserInfo object.
	 *
	 */
	private List<String> getUserRoles(final UserInfo userLogged) throws POCException {
		final List<String> rolesToReturn = CreateDummyGroupsRoles.getRolesForTheUser(userLogged.getActiveDirectoryMetadata().getSamAccountName());
		return rolesToReturn;
	}


	public String getUri() {
		return uri;
	}


	public String getLocalUser() {
		return localUser;
	}
}
