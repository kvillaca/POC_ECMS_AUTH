package com.pq.tests.exceptions;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.pq.ideas.enums.ErrorMessagesKeys;
import com.pq.ideas.pojos.ExceptionPojo;
import com.pq.ideas.utils.PropertyReader;

public class TestExceptionPojo {

	private ExceptionPojo destination;
	private com.pq.tests.classes.helpers.ExceptionPojo toConvert; 
	
	@Before
	public void setUp() throws Exception {
		toConvert = new com.pq.tests.classes.helpers.ExceptionPojo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() + "", 
															  PropertyReader.getPropertyValue(ErrorMessagesKeys.INTERNAL_SERVER_ERROR.toString()));
	}


	@After
	public void tearDown() throws Exception {
		toConvert = null;
	}


	@Test
	public void testReflexionConvertion() {
		destination = new ExceptionPojo(toConvert);
		assertNotNull(destination.getStatusCode());
		assertNotNull(destination.getMessage());
	}

}
