package com.bimoku.dataplatform.resource;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bimoku.dataplatform.entity.dto.BookDTO;
import com.bimoku.dataplatform.entity.dto.BookDetailsDTO;
import com.bimoku.dataplatform.service.BookService;


@Path(value = "/")
public class BookResource {
	
	private Logger logger = Logger.getLogger(BookResource.class);
	
	@Autowired
	private BookService bookService;
	
	@Path(value = "/book/{ISBN}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public BookDetailsDTO getBook(@PathParam("ISBN") String isbn) {
		logger.info("Get Book with ISBN: " + isbn);
		return bookService.findByIsbn(isbn);
	}
	
	@Path(value = "/book")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getBooksByName(@QueryParam("name") String name, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("ASC") @QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders) {
		logger.info("Get Books start:" + start + " Size:" + size);
		return bookService.findBooksByName(name, start, size, direction, orders.toArray(new String[orders.size()]));
	}
	
	@Path(value = "/book/tag")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getBooksByTag(@QueryParam("tag") String tag, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("ASC") @QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders) {
		logger.info("Get books by Tag: " + tag);
		return bookService.findByTagName(tag, start, size, direction, orders.toArray(new String[orders.size()]));
	}
	
	@Path(value = "/book/page")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getBooks(@DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size, 
			@DefaultValue("ASC") @QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders) {
		logger.info("Get Books start:" + start + " Size:" + size);
		return bookService.findAll(start, size, direction, orders.toArray(new String[orders.size()]));
	}

	@Path(value = "/book/new")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getNewBooks( @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size,
			@DefaultValue("ASC") @QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders){
		logger.info("Get New Books start:" + start + " Size:" + size);
		return bookService.findNewBooks(start, size, direction, orders.toArray(new String[orders.size()]));
	}
	
	@Path(value = "/book/sale")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getSaleBooks(@DefaultValue("0") @QueryParam("start")int start, @DefaultValue("10") @QueryParam("size") int size, 
			@QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders){
		logger.info("Get top-sale Books start:" + start + " Size:" + size);
		return bookService.findSaleBooks(start, size, direction, orders.toArray(new String[orders.size()]));
	}
	
	@Path(value = "/book/promotion")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getPromotionBooks(@DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size, 
			@DefaultValue("ASC") @QueryParam("direction") String direction,  @QueryParam("orderBy") List<String> orders){
		logger.info("Get Promotion Books start:" + start + " Size:" + size);
		return bookService.findPromotionBooks(start, size, direction, orders.toArray(new String[orders.size()]));
	}
	
	@Path(value = "/book/searched")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getSearchedBooks(@QueryParam("user") String user, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size) {
		logger.info("Get User: " + user + " searched Books start:" + start + " Size:" + size);
		return bookService.findSearchedBooks(user, start, size);
	}	
	
	@Path(value = "/book/liked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getlikedBooks(@QueryParam("user") String user, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size) {
		logger.info("Get User: " + user + " liked Books start:" + start + " Size:" + size);
		return bookService.findLikedBooks(user, start, size);
	}	
	
	@Path(value = "/book/collected")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> getCollectedBooks(@QueryParam("user") String user, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size) {
		logger.info("Get User: " + user + " collected Books start:" + start + " Size:" + size);
		return bookService.findCollectedBooks(user, start, size);
	}	
	
	@Path(value = "/book/search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BookDTO> universalSearch(@QueryParam("input")String input, @DefaultValue("0") @QueryParam("start")int start,  @DefaultValue("10") @QueryParam("size") int size) {
		logger.info("Universal search, input: " + input);
		return bookService.universalSearch(input, start, size);
	}
}
