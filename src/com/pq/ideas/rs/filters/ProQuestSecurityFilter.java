package com.pq.ideas.rs.filters;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.pq.ideas.pojos.DummyPojoForTests;
import com.pq.ideas.token.ValidateToken;
import com.sun.xml.internal.ws.developer.SerializationFeature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.token.TokenAuthenticator;
import com.pq.ideas.utils.PropertyReader;


/**
 * Security Filter, that can run in any Application server that full supports Servlet 3.0 or 3.1
 * 
 * @author Klaus Villaca
 *
 */
@Provider
@PreMatching
public class ProQuestSecurityFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private String header;
	private String uri;


	/**
	 * Response starts here
	 */
	@Override
	public void filter(ContainerRequestContext containerRequest, ContainerResponseContext containerResponse) throws IOException {


		// Get the URI called
		uri = containerRequest.getUriInfo().getAbsolutePath().toString();
//
		// Get the header from request
		header = containerRequest.getHeaderString(TokenDetailsKeys.HEADER.toString().toLowerCase());

		if (header != null) {
			final ValidateToken validateToken = new ValidateToken();
			try {
				containerResponse = validateToken.validateTokenResponse(containerResponse, uri, header);
			} catch (Exception e) {
				containerResponse.setStatus(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			}
		}
	}


	/**
	 * Request starts here
	 */
	@Override
	public void filter(ContainerRequestContext containerRequest) {

		final ValidateToken validateToken = new ValidateToken();
		try {
			containerRequest = validateToken.validateTokenRequest(containerRequest);
		} catch (Exception e) {
			containerRequest.abortWith(Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage()).build());
		}
	}
}
