package com.bimoku.dataplatform.resource;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

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
import com.bimoku.dataplatform.entity.dto.UserDTO;
import com.bimoku.dataplatform.util.DataGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-spring-context.xml")
@Transactional
public class UserResourceTest extends JerseyTest {

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
		dataGenerator.generateTestData(1);
	}
	
	@Test
	public void shouldGetUserByName() {
		UserDTO user = target("user/User 1").request().get(UserDTO.class);
		assertEquals("User 1", user.getName());
	}
	
	@Test
	public void shouldGetFollowersByName() {
		GenericType<List<UserDTO>> listType = new GenericType<List<UserDTO>>() {};
		assertEquals(0, target("user/User 1/followers").request().get(listType).size());
		
		target("user/用户2/follows").request().put(Entity.entity("User 1", MediaType.TEXT_PLAIN));
		assertEquals(1, target("user/User 1/followers").request().get(listType).size());
		target("user/用户2/unfollows").request().put(Entity.entity("User 1", MediaType.TEXT_PLAIN));
		assertEquals(0, target("user/User 1/followers").request().get(listType).size());
	}
	
	@Test
	public void shouldCreateAUser() {
		UserDTO dto = new UserDTO();
		dto.setName("中文");
		UserDTO u = target("user").request().post(Entity.entity(dto, MediaType.APPLICATION_JSON), UserDTO.class);
		assertEquals("中文", u.getName());
	}
	
	@Test
	public void shouldCollectABook() {
		target("user/用户2/collects").queryParam("status", "READ").request().put(Entity.entity("1", MediaType.TEXT_PLAIN));
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/collected").queryParam("user", "用户2").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(1, books.size());	
	}
	
	@Test
	public void shouldLikeABook() {
		target("user/用户2/likes").request().put(Entity.entity("1", MediaType.TEXT_PLAIN));
		GenericType<List<BookDTO>> listType = new GenericType<List<BookDTO>>() {};
		List<BookDTO> books = (List<BookDTO>) target("book/liked").queryParam("user", "用户2").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals(1, books.size());	
	}
	
	@After
	public void tearUp() {
		dataGenerator.cleanUp();
	}	
}
