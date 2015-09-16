package com.pq.ideas.token;

import com.pq.ideas.enums.TokenDetailsKeys;
import com.pq.ideas.pojos.DummyPojoForTests;
import com.pq.ideas.utils.PropertyReader;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvillaca on 9/16/2015.
 */
public class ValidateToken {

    final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private static final String LOGIN = "validate/";
    private static final String POC_REST_SERVLET = "/rest/";
    private static final String EMPTY = "";
    private static final String TOKEN_PROBLEM = "{\"errorMessage\":\"Token expired or not valid!\"}";

    private static final int TOKEN_INDEX = 0;

    private String token;
    private List<String> roles;
    private String rolesStr;
    private String uri;
    private String localUser;


    /**
     * Logic behind the filter for Validate Token
     *
     * This method need to be split in smaller reusable methods
     *
     * @param containerRequest
     * @return
     * @throws Exception
     */
    public ContainerRequestContext validateTokenRequest(final ContainerRequestContext containerRequest) throws Exception {
        uri = containerRequest.getUriInfo().getAbsolutePath().toString();

        // For the initial menu when not logged.
        if (uri.contains(POC_REST_SERVLET)) {
            final String headerVal = containerRequest.getHeaderString(TokenDetailsKeys.HEADER.toString().toLowerCase());
            if (headerVal != null && headerVal.trim().length() > 0) {
                parseHeader(headerVal);
                LOG.info("User: " + localUser + ", role(s): " + rolesStr + ", uri accessed: " + uri);
            }
            boolean hasToken = (token != null && token.length() > 0) ? true : false;
            boolean hasRsServlet = (uri != null && uri.contains(POC_REST_SERVLET)) ? true : false;
            boolean isLogin = (uri != null && uri.contains(POC_REST_SERVLET) && uri.contains(LOGIN)) ? true : false;

            if (!hasToken && !isLogin && hasRsServlet) {
                // Fail, trying to access resources without minimum security data needed
                try {
                    containerRequest.setRequestUri(containerRequest.getUriInfo().getAbsolutePath(), new URI(containerRequest.getRequest()
                            .toString().replace(POC_REST_SERVLET, EMPTY)));
                } catch (URISyntaxException e) {
                    // TODO re throw the exception using POCException class and/or return a server error
                    // At moment it will return 404!
                    containerRequest.abortWith(Response.status(Response.Status.NOT_FOUND).build());
                }
            } else if (hasToken && hasRsServlet) {
                // Is a valid call, token must be validated, so we call the TokenAuthenticator.
                final TokenAuthenticator authToken = new TokenAuthenticator(uri, localUser);
                token = authToken.validateToken(token);

                if (token == null || token.trim().equals(EMPTY)) {
                    // If the code get to this line, the token isn't valid anymore.
                    token = TokenDetailsKeys.TOKEN_EXPIRED.toString();

                    try {
                        final JSONObject objectoForTokenExpired = new JSONObject();
                        objectoForTokenExpired.put(TokenDetailsKeys.TOKEN.toString(), token);
                        token = objectoForTokenExpired.toString();

                        final JSONObject headerForReplace = replaceHeaderValues(headerVal);
                        containerRequest.removeProperty(TokenDetailsKeys.HEADER.toString());
                        containerRequest.getHeaders().putSingle(TokenDetailsKeys.HEADER.toString(), headerForReplace.toString());
                        containerRequest.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(TOKEN_PROBLEM).build());
                    } catch (Exception e) {
                        // If the jvm reaches here means that something went wrong with the container request,
                        // then we just return HTTP code 500, Server Error.
                        containerRequest.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
                    }
                }
            }
        }

        return containerRequest;
    }


    /**
     * If the Request has been succesful then we produce a new token to return to the user.
     *
     * This method also need to be split in smaller reusable methods
     *
     * @param containerResponse
     * @param uri
     * @param header
     * @return
     * @throws Exception
     */
    public ContainerResponseContext validateTokenResponse(final ContainerResponseContext containerResponse, final String uri, final String header) throws Exception {
        try {
            JSONObject headerAsJson = new JSONObject(header);
            final JSONArray headerValuesAsJson = headerAsJson.getJSONArray(TokenDetailsKeys.HEADER_VALUES.toString());
            final JSONObject tokenJsonObject = headerValuesAsJson.getJSONObject(TOKEN_INDEX);

            setBasicVariablesToCreateOrCheckToken(headerAsJson);

            token = tokenJsonObject.has(TokenDetailsKeys.TOKEN.toString()) ? tokenJsonObject.getString(TokenDetailsKeys.TOKEN
                    .toString()) : PropertyReader.getPropertyValue(TokenDetailsKeys.TOKEN_INEXISTENT.toString());

            if (!token.contains(TokenDetailsKeys.TOKEN_EXPIRED.toString())) {
                TokenAuthenticator authToken = new TokenAuthenticator(uri, localUser);
                token = authToken.getNewToken(token);
            }

            if (token.trim().length() > 0 && !uri.contains(LOGIN)) {
                if (header.contains(TokenDetailsKeys.TOKEN_EXPIRED.toString()))
                    headerAsJson = new JSONObject(header);
                else
                    headerAsJson = replaceHeaderValues(header);
                containerResponse.getHeaders().putSingle(TokenDetailsKeys.HEADER.toString(), headerAsJson);
            }
            headerAsJson = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return containerResponse;
    }



    /**
     * Replace the old token from the Header with a new token value, if not null.
     *
     * @param valuesJSON
     * @return
     */
    private JSONObject replaceHeaderValues(final String valuesJSON) {
        JSONObject fromJsonArrayToJsonObject = null;
        try {
            final JSONObject header = new JSONObject(valuesJSON);
            final JSONObject tokenJsonObject = new JSONObject(token);
            final JSONArray headerArray = header.getJSONArray(TokenDetailsKeys.HEADER_VALUES.toString());
            headerArray.put(TOKEN_INDEX, tokenJsonObject);

            fromJsonArrayToJsonObject = new JSONObject();
            fromJsonArrayToJsonObject.put(TokenDetailsKeys.HEADER_VALUES.toString(), headerArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fromJsonArrayToJsonObject;
    }


    /**
     * Parse the header to extract three JSON Objects, that are token, roles and localUser
     *
     * @param headerStr
     */
    private void parseHeader(String headerStr) {
        try {
            final JSONObject header = new JSONObject(headerStr);
            setBasicVariablesToCreateOrCheckToken(header);

            final JSONObject rolesJson = new JSONObject(rolesStr);
            if (rolesJson.has(TokenDetailsKeys.HEADER_USER_ROLES.toString())) {
                final Object check = rolesJson.get(TokenDetailsKeys.HEADER_USER_ROLES.toString());
                if (check instanceof JSONArray) {
                    final JSONArray rolesArray = (JSONArray) check;
                    if (rolesArray != null && rolesArray.length() > 0) {
                        roles = new ArrayList<String>();
                        for (int i = 0; i < rolesArray.length(); i++) {
                            roles.add(rolesArray.getString(i));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            // Clean the token and the information contained in the request as it shouldn't fail ever
            token = null;
            localUser = null;
            if (roles != null)
                roles.clear();
        }
    }


    /**
     * Set the basic variables that will be used to token creation, validation and update
     *
     * @param header
     */
    private void setBasicVariablesToCreateOrCheckToken(JSONObject header) {
        try {
            final JSONArray headerArray = header.getJSONArray(TokenDetailsKeys.HEADER_VALUES.toString());

            String tempJsonFromArray = null;
            for (int i = 0; i < headerArray.length(); i++) {
                tempJsonFromArray = headerArray.getString(i);
                if (tempJsonFromArray.contains(TokenDetailsKeys.TOKEN.toString())) {
                    token = headerArray.getString(i);
                } else if (tempJsonFromArray.contains(TokenDetailsKeys.HEADER_USER_ROLES.toString())) {
                    rolesStr = headerArray.getString(i);
                } else if (tempJsonFromArray.contains(TokenDetailsKeys.LOCAL_USER.toString())) {
                    final JSONObject tempUser = new JSONObject(headerArray.getString(i));
                    localUser = tempUser.getString(TokenDetailsKeys.LOCAL_USER.toString());
                }
                tempJsonFromArray = null;
            }
        } catch (Exception e) {
            token = null;
            localUser = null;
            rolesStr = null;
        }
    }


    /**
     * validate the uri with the user role. If we were using services validation then this service would be useful.
     *
     * @param rolesList
     * @param uri
     * @return
     */
    @SuppressWarnings("unused")
    private boolean isValidServiceRole(final List<String> rolesList, final String uri) {
        boolean isValid = false;
        if (rolesList != null && rolesList.size() > 0) {
            for (String role : rolesList) {
                if (uri.contains(role)) {
                    isValid = true;
                    break;
                }
            }
        }
        return isValid;
    }
}
