package com.pq.ideas.rs.endpoints.v1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pq.ideas.dummy.CreateDummyHeaderLogin;
import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.pojos.ExternalLoginData;

@Path("/login")
public class LoginService {

	final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private static final int TOKEN = 0;
	private static final int ROLES = 1;
	private static final int USER = 2;


	public LoginService() {
	}


	@POST
	@Path("/validate/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validate(String value) {
		Response resp = null;

		if (value != null) {
			ExternalLoginData logExt = null;
			final ObjectMapper mapper = new ObjectMapper();
			try {
				logExt = mapper.readValue(value, ExternalLoginData.class);
				if (logExt != null) {
					CreateDummyHeaderLogin dummyLogin = new CreateDummyHeaderLogin();
					final List<String> responseAsList = dummyLogin.getDummyLoginData(logExt);
					dummyLogin = null;

					if (responseAsList != null && responseAsList.size() > 0) {
						final JSONObject json = getHeaderToken(responseAsList);

						/*
						 * On the line bellow I have plans, to have a helper class to check the responses and add the type of response as
						 * needed. If ok, producing like the line bellow, a warning/error to return a response with appropriate code plus
						 * message
						 */
						resp = Response.ok().header(TokenDetailsKeys.HEADER.toString(), json).build();
					} else
						resp = Response.status(Response.Status.UNAUTHORIZED).build();
				} else {
					resp = Response.status(Response.Status.BAD_REQUEST).build();
				}
			} catch (Exception e) {
				final String message = "Test"; // SystemErrorPrepareMessage.getSystemErrorMessage(e);
				resp = Response.serverError().entity(message).type(MediaType.APPLICATION_JSON_TYPE).build();
				LOG.error(this.getClass().getName() + ":" + e.getMessage());
				// e.printStackTrace();
			}
		} else {
			resp = Response.status(Response.Status.UNAUTHORIZED).build();
		}
		return resp;
	}


	/**
	 * Produce the header with details for the security.
	 * 
	 * @param responseAsList
	 * @return
	 */
	private JSONObject getHeaderToken(List<String> responseAsList) {
		List<String> roles = parseRolesToList(responseAsList.get(ROLES));
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONObject json = new JSONObject();
		try {
			json.put(TokenDetailsKeys.TOKEN.toString(), responseAsList.get(TOKEN));
			jsonObjects.add(json);
			json = null;

			json = new JSONObject();
			json.put(TokenDetailsKeys.HEADER_USER_ROLES.toString(), roles.toString());
			jsonObjects.add(json);
			roles.clear();
			roles = null;
			json = null;

			json = new JSONObject();
			json.put(TokenDetailsKeys.LOCAL_USER.toString(), responseAsList.get(USER).toString());
			jsonObjects.add(json);
			json = null;

			json = new JSONObject();
			json.put(TokenDetailsKeys.HEADER_VALUES.toString(), jsonObjects);
			jsonObjects = null;
		} catch (Exception e) {
			json = null;
		}
		return json;
	}


	private List<String> parseRolesToList(final String rolesStr) {
		List<String> roles = null;
		if (rolesStr != null) {
			try {
				roles = new ArrayList<String>();
				final JSONArray jsonArray = new JSONArray(rolesStr);
				for (int i = 0; i < jsonArray.length(); i++) {
					roles.add(jsonArray.getString(i));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				LOG.error(this.getClass().getName() + ":" + e.getMessage());
				// e.printStackTrace();
			}
		}
		return roles;
	}

}
