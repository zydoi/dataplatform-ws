package com.bimoku.dataplatform.resource.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;

/**
 * This filter is used to tell clients to decode JSON responses with charset
 * UTF-8.
 * 
 * @author Yiding Zhang
 * 
 */
public class CharsetResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		MediaType contentType = responseContext.getMediaType();
		if (contentType != null) {
			responseContext.getHeaders().putSingle("Content-Type", contentType.toString() + ";charset=utf-8");
		}
	}

}
