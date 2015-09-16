package com.pq.ideas.utils;

import org.codehaus.jettison.json.JSONObject;

import com.pq.ideas.enums.TokenDetailsKeys;

public class UserUtils {
	
	private static final String VALUES_TO_COMPLETE = "9Z9X9C9V9B9N9M90";
	private static final int MAX_PASSWORD_LENGTH = 16;
	
	/**
	 * Check password length, that is used to encrypt and decrypt token.
	 * This 'password' is the user id from the PC.
	 * 
	 * This code restrict the password to 16 digits that is 128bits (8bits each char).
	 * This is the minimum acceptable today for HTTP/HTTPS encryption, if need a more
	 * powerful cipher then it's needed to download and use 
	 * the JCEKS - Java Cryptography Extension KeyStore 
	 * 
	 * @param oldPassword
	 * @return
	 */
	public static String checkPassword(final String oldPassword) {
		String passwordToReturn = null;
		if (oldPassword != null) {
			try {
				String password = null;
				// This check is necessary due the login isn't an jsonArray.
				if (oldPassword.contains(TokenDetailsKeys.LOCAL_USER.toString())) {
					final JSONObject jsonPasswordObject = new JSONObject(oldPassword);
					password = jsonPasswordObject.getString(TokenDetailsKeys.LOCAL_USER.toString());
				} else {
					password = oldPassword;
				}
				if (password.trim().length() > MAX_PASSWORD_LENGTH) {
					// Need to make the password with 16digits only.
					passwordToReturn = password.substring(0, MAX_PASSWORD_LENGTH);
				} else if (password.trim().length() < MAX_PASSWORD_LENGTH) {
					passwordToReturn = password
							+ VALUES_TO_COMPLETE.substring(password.length(), VALUES_TO_COMPLETE.length());
				} else if (password.trim().length() == MAX_PASSWORD_LENGTH) {
					passwordToReturn = password;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return passwordToReturn;
	}
	
	
}
