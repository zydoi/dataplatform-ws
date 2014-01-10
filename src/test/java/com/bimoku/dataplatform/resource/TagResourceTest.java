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

import com.bimoku.dataplatform.entity.dto.TagDTO;
import com.bimoku.dataplatform.util.DataGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-spring-context.xml")
@Transactional
public class TagResourceTest extends JerseyTest{

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
	public void shouldGetPopularTags() {
		GenericType<List<TagDTO>> listType = new GenericType<List<TagDTO>>() {};
		assertEquals(2, target("/tag/top").request().get(listType).size());
	}
	
	@Test
	public void shouldAddANewTag() {
		GenericType<List<TagDTO>> listType = new GenericType<List<TagDTO>>() {};
		assertEquals(2, target("/book/1/tags").request().get(listType).size());
		target("/book/1/tags").request().put(Entity.entity("Tag3", MediaType.TEXT_PLAIN));
		assertEquals(3, target("/book/1/tags").request().get(listType).size());
		target("/book/1/tags").request().put(Entity.entity("Tag3", MediaType.TEXT_PLAIN));
		assertEquals(3, target("/book/1/tags").request().get(listType).size());
	}
	
	@After
	public void tearUp() {
		dataGenerator.cleanUp();
	}
}
