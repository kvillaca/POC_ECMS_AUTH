package com.pq.ideas.rs.application;

import com.pq.ideas.enums.LogMessagesKeys;
import com.pq.ideas.rs.filters.ProQuestSecurityFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.GZipEncoder;
import org.glassfish.jersey.message.filtering.EntityFilteringFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.EncodingFilter;

import javax.ws.rs.ApplicationPath;
import java.util.logging.Logger;


/**
 * Application class that define the packages, services and filters, that also can be made via web.xml file
 * 
 * @author kvillaca
 *
 */
@ApplicationPath("/rest/auth/")
public class Application extends ResourceConfig {


	@SuppressWarnings("unchecked")
	public Application() {
		packages("com.pq.ideas.rs.endpoints.v1", "com.pq.ideas.rs.filters");
		register(ProQuestSecurityFilter.class);
		register(EntityFilteringFeature.class);
		register(new LoggingFilter(Logger.getLogger(LogMessagesKeys.STANDARD_MESSAGE.toString()), true));
		EncodingFilter.enableFor(this, GZipEncoder.class);

		// Enable Tracing support. - Disable later as it logs even the token!
		property(ServerProperties.TRACING, "ALL");
	}
}
