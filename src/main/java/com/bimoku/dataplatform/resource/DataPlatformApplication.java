package com.bimoku.dataplatform.resource;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.bimoku.dataplatform.resource.filter.CharsetRequestFilter;
import com.bimoku.dataplatform.resource.filter.CharsetResponseFilter;

@ApplicationPath(value = "/")
public class DataPlatformApplication extends ResourceConfig {
	
	public DataPlatformApplication() {
		packages("com.bimoku.dataplatform.resource");
		register(CharsetResponseFilter.class);
		register(CharsetRequestFilter.class);
	}
	
}
