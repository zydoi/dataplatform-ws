package com.bimoku.dataplatform.resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.bimoku.dataplatform.entity.dto.BookDTO;
import com.bimoku.dataplatform.util.DataGenerator;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-spring-context.xml")
@Transactional
public class BookResourceTest extends JerseyTest {

	@Autowired
	private DataGenerator dataGenerator;
	
	protected Application configure() { 
		// Enable logging.
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        
		return new DataPlatformApplication().property("contextConfigLocation","classpath:test-spring-context.xml");
	}
	
	@Before
	public void setup() {
		dataGenerator.cleanUp();
		dataGenerator.generateTestData(5);
	}
	
	@Test
	public void shouldGetABookByISBN() {
		BookDTO book = target("book/2").request().get(BookDTO.class);
		assertEquals("Book 2",book.getName());
	}
	
	@Test
	public void shouldGetABookPage() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/page").queryParam("start", 0).queryParam("size", 2)
				.queryParam("direction", "DESC").queryParam("orderBy", "PubPrice").request().get(listType);
		assertEquals(15, books.get(0).getPubPrice(), 0);
	}
	
	@Test
	public void shouldGetNewBooks() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/new").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(0, books.size());
	}
	
	@Test
	public void shouldGetLikedBooks() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/liked").queryParam("user", "User 1").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(1, books.size());
	}
	
	@Test
	public void shouldGetCollectedBooks() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/collected").queryParam("user", "User 1").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(1, books.size());
	}
	
	@Test
	public void shouldGetBooksByName() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book").queryParam("name", "Book 1").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals("Book 1", books.get(0).getName());
	}
	
	@Test
	public void shouldGetBooksByTag() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/tag").queryParam("tag", "Tag1").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(2, books.size());
	}
	
	@Test
	public void shouldGetBooksViaUniversalSearch() {
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/search").queryParam("input", "Press").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(2, books.size());
		books = (List<BookDTO>) target("book/search").queryParam("input", "oo").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(2, books.size());
	}
	
	@After
	public void tearUp() {
		dataGenerator.cleanUp();
	}
}
