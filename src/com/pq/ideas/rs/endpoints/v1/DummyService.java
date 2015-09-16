package com.pq.ideas.rs.endpoints.v1;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pq.ideas.pojos.DummyPojoForTests;

/**
 * Dummy services, one using GET and other using POST
 * 
 * @author kvillaca
 *
 */

/*
 * We can add /v1 as prefix for dummy (e.g.: /v1/dummy).
 */
@Path("/v1/dummy")
public class DummyService {

	final Logger LOG = LoggerFactory.getLogger(this.getClass());
	

	@POST
	@Path("/serviceOnePost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response serviceOnePost(String value) {
		Response response;
		try {
			final DummyPojoForTests dummyTest = parseToPojo(value);
			final ObjectMapper mapper = new ObjectMapper();
			if (dummyTest != null) {
				final String responseString = mapper.writeValueAsString(dummyTest);
				LOG.info(responseString);
				System.out.println(responseString);
				response = Response.ok(responseString, MediaType.APPLICATION_JSON_TYPE).build();
			} else {
				response = Response.noContent().build();
			}
		} catch (Exception e) {
			response = Response.status(Response.Status.BAD_REQUEST).build();
		}
		return response;
	}

	
	
	/**
	 * Private method to extract the from the JSON payload the desired JSON object
	 * that in this case is the DummyPojoForTests object.
	 * 
	 * @param dummyPojoStr
	 * @return
	 * @throws Exception
	 */
	private DummyPojoForTests parseToPojo(String dummyPojoStr) throws Exception {
		final String POJO_KEY = "DummyPojoForTests";
		final ObjectMapper mapper = new ObjectMapper();
		final JSONObject object = new JSONObject(dummyPojoStr);
		if (object.has(POJO_KEY))
			dummyPojoStr = object.getString(POJO_KEY);
		final DummyPojoForTests dummyTest = mapper.readValue(dummyPojoStr, DummyPojoForTests.class);
		if (dummyTest != null) {
			dummyTest.setMessage("IT'S A RESPONSE NOW");
		}
		dummyPojoStr = null;
		return dummyTest;
	}

}
