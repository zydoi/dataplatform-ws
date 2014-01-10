package com.bimoku.dataplatform.resource.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;

public class CharsetRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		MediaType contentType = requestContext.getMediaType();
		if (contentType != null) {
			requestContext.getHeaders().putSingle("Content-Type", contentType.toString() + ";charset=utf-8");
		}
	}

}
