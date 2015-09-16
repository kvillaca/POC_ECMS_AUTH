package com.pq.tests.parameter;

import static org.junit.Assert.*;

import java.util.MissingResourceException;

import org.junit.Test;

import com.pq.ideas.enums.SuccessMessagesKeys;
import com.pq.ideas.enums.WarningMessagesKeys;
import com.pq.ideas.utils.PropertyReader;


public class TestGetProperties {

	@Test
	public void testGetProperties() {
		final String warningFromProperties = PropertyReader.getPropertyValue(WarningMessagesKeys.WARNING.toString());
		assertNotNull(warningFromProperties);

		final String successFromProperties = PropertyReader.getPropertyValue(SuccessMessagesKeys.SUCCESS.toString());
		assertNotNull(successFromProperties);
	}


	@SuppressWarnings("unused")
	@Test
	public void testFailGetProperties() {
		try {
			final String warningFromProperties = PropertyReader.getPropertyValue("NO_EXISTING_PROPERTY");

		} catch (MissingResourceException e) {
			assertNotNull(e);
		} catch (Exception e) {
			fail("Exception: " + e.getMessage());
		}
	}

}
