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

import com.bimoku.dataplatform.entity.dto.MessageDTO;
import com.bimoku.dataplatform.util.DataGenerator;
import com.bimoku.dataplatform.util.EntityGenerator;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-spring-context.xml")
@Transactional
public class MessageResourceTest extends JerseyTest {
	
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
	public void shouldGetMessagesByBook() {
		GenericType<List<MessageDTO>> listType = new GenericType<List<MessageDTO>>() {};
		List<MessageDTO> messages = (List<MessageDTO>) target("user/User 1/messages").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals("User 1", messages.get(0).getUser().getName());
	}
	
	@Test
	public void shouldGetMessagesByUser() {
		GenericType<List<MessageDTO>> listType = new GenericType<List<MessageDTO>>() {};
		List<MessageDTO> messages = (List<MessageDTO>) target("book/1/messages").queryParam("start", 0).queryParam("size", 2).request().get(listType);
		assertEquals("Book 1", messages.get(0).getBookName());
	}
	
	@Test
	public void shouldCreateAMessage() {
		MessageDTO dto = EntityGenerator.generateMessageDTOs(1).get(0);
		MessageDTO m = target("message").request().post(Entity.entity(dto, MediaType.APPLICATION_JSON), MessageDTO.class);
		assertEquals(dto.getContent(), m.getContent());
	}
	
	@After
	public void tearUp() {
		dataGenerator.cleanUp();
	}
}
