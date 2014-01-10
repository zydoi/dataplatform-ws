package com.bimoku.dataplatform.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bimoku.dataplatform.entity.dto.UserDTO;
import com.bimoku.dataplatform.entity.type.CollectionStatus;
import com.bimoku.dataplatform.service.UserService;

@Path(value = "/")
public class UserResource {

	private static final Logger logger = Logger.getLogger(UserResource.class);
	
	@Autowired
	private UserService service;
	
	@Path(value = "/user")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserDTO register(UserDTO userDTO) {
		logger.info("Register new User: " + userDTO.getName());
		return service.create(userDTO);
	}
	
	@Path(value = "/user/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserDTO getUser(@PathParam("name") String name) {
		logger.info("Get User with Name: " + name);
		return service.findByName(name);
	}
	
	@Path(value = "/user/{name}/followers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getFollowers(@PathParam("name")String name) {
		logger.info("Get Followers of User: " + name );
		return service.findFollowersByName(name);
	}

	@Path(value = "/user/{name}/followings")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserDTO> getFollowings(@PathParam("name") String name) {
		logger.info("Get Followings of User: " + name );
		return service.findFollowingsByName(name);
	}
	
	@Path(value = "/user/{user}/follows")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void follow(@PathParam("user") String user, String tofollow ) {
		logger.info("User: " + user + " follows " + "User: " + tofollow );
		service.follow(user, tofollow);
	}
	
	@Path(value = "/user/{user}/unfollows")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void unfollow(@PathParam("user") String user, String tofollow ) {
		logger.info("User: " + user + " unfollows " + "User: " + tofollow );
		service.unfollow(user, tofollow);
	}
	
	@Path(value = "/user/{user}/collects")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void collectBook(@PathParam("user") String user,  @QueryParam("status") @DefaultValue("WANTED") String status, String isbn) {
		logger.info("User: " + user + " collects " + "Book: " + isbn );
		service.collectBook(user, isbn, CollectionStatus.valueOf(status));
	}

	@Path(value = "/user/{user}/searches")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void searchedBook(@PathParam("user") String user, String isbn) {
		logger.info("User: " + user + " searched " + "Book: " + isbn );
		service.searchedBook(user, isbn);
	}
	
	@Path(value = "/user/{user}/likes")
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	public void likeBook(@PathParam("user") String user, String isbn) {
		logger.info("User: " + user + " likes " + "Book: " + isbn );
		service.likeBook(user, isbn);
	}
}
