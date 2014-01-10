package com.bimoku.dataplatform.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bimoku.dataplatform.entity.dto.MessageDTO;
import com.bimoku.dataplatform.service.MessageService;

@Path(value = "/")
public class MessageResource {

	private static final Logger logger = Logger.getLogger(MessageResource.class);
	
	@Autowired
	private MessageService service;
	
	@Path(value ="/message")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public MessageDTO createMessage(MessageDTO messageDTO) {
		return service.create(messageDTO);
	}
	
	@Path(value = "/user/{user}/messages")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MessageDTO> getMessagesByUserName(@PathParam(value = "user")String userName,  @QueryParam(value = "start")int start,  @QueryParam(value = "size")int size) {
		logger.info("Get messages by User: " + userName);
		return service.findByUserName(userName, start, size);
	}

	@Path(value = "/book/{isbn}/messages")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MessageDTO> getMessagesByBookISBN(@PathParam(value = "isbn")String isbn, @QueryParam(value = "start")int start, @QueryParam(value = "size")int size) {
		logger.info("Get messages by bookName: " + isbn);
		return service.findByBookISBN(isbn, start, size);
	}
}
