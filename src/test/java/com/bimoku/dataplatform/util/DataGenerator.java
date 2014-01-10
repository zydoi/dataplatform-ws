package com.bimoku.dataplatform.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bimoku.dataplatform.dao.AssociatedTagDao;
import com.bimoku.dataplatform.dao.BookDao;
import com.bimoku.dataplatform.dao.CollectedBookDao;
import com.bimoku.dataplatform.dao.MessageDao;
import com.bimoku.dataplatform.dao.PressDao;
import com.bimoku.dataplatform.dao.TagDao;
import com.bimoku.dataplatform.dao.UserDao;
import com.bimoku.dataplatform.entity.Book;
import com.bimoku.dataplatform.entity.CollectedBook;
import com.bimoku.dataplatform.entity.Message;
import com.bimoku.dataplatform.entity.Press;
import com.bimoku.dataplatform.entity.Tag;
import com.bimoku.dataplatform.entity.User;
import com.bimoku.dataplatform.entity.type.CollectionStatus;

@Service
public class DataGenerator {
	
	@Autowired
	private BookDao bookDao;
	
	@Autowired
	private PressDao pressDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private AssociatedTagDao aTagDao;
	
	@Autowired
	private CollectedBookDao cBookDao;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void cleanUp() {
		aTagDao.deleteAll();
		cBookDao.deleteAll();
		messageDao.deleteAll();
		bookDao.deleteAll();
		userDao.deleteAll();
		tagDao.deleteAll();
		pressDao.deleteAll();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void generateTestData(int n) {
		User user = EntityGenerator.generateUser("用户2");
		userDao.save(user);
		user = EntityGenerator.generateUser("User 1");
		userDao.save(user);
		Press press = EntityGenerator.generatePress("Press");
		pressDao.save(press);
		Book book1 = EntityGenerator.generateBook("1");
		book1.setPubPrice(10);
		Book book2 = EntityGenerator.generateBook("2");
		book2.setPubPrice(15);
		book1.setPress(press);
		book2.setPress(press);
		bookDao.save(book1);
		bookDao.save(book2);
		
		user.getLikeBooks().add(book1);
		user.getSearchBooks().add(book1);
		CollectedBook cBook = new CollectedBook(user, book1, CollectionStatus.READ);
		user.getCollectedBooks().add(cBook);
		
		List<Message> messages = EntityGenerator.generateMessages(n);
		for (Message message : messages) {
			message.setUser(user);
			message.setBook(book1);
		}
		messageDao.save(messages);
		Tag tag1 = new Tag("Tag1");
		Tag tag2 = new Tag("Tag2");
		tagDao.save(tag1);
		tagDao.save(tag2);
		aTagDao.save(EntityGenerator.generateAssociatedTag(book1, tag1));
		aTagDao.save(EntityGenerator.generateAssociatedTag(book1, tag2));
		aTagDao.save(EntityGenerator.generateAssociatedTag(book2, tag1));
	}
	
}
