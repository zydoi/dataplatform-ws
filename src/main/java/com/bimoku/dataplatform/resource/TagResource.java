package com.bimoku.dataplatform.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bimoku.dataplatform.entity.dto.TagDTO;
import com.bimoku.dataplatform.service.TagService;

@Path(value = "/")
public class TagResource {

	private static final Logger logger = Logger.getLogger(TagResource.class);
	
	@Autowired
	private TagService service;
	
	@Path("/tag/top")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TagDTO> getPopularTags() {
		logger.info("Get Popular Tags.");
		return service.getPopularTags();
	}
	
	@Path("/book/{isbn}/tags")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TagDTO> findTagsByBook(@PathParam("isbn") String isbn) {
		logger.info("Get tags from book: " + isbn);
		return service.findTagsByBook(isbn);
	}

	@Path("/book/{isbn}/tags")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void addTag(@PathParam("isbn") String isbn, String tagName) {
		logger.info("Add tag: " + tagName + " to book: " + isbn);
		service.addTag(isbn, tagName);
	}
}
