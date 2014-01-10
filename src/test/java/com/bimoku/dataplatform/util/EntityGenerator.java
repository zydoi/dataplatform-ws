package com.bimoku.dataplatform.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bimoku.dataplatform.entity.AssociatedTag;
import com.bimoku.dataplatform.entity.Author;
import com.bimoku.dataplatform.entity.Book;
import com.bimoku.dataplatform.entity.Message;
import com.bimoku.dataplatform.entity.Press;
import com.bimoku.dataplatform.entity.Tag;
import com.bimoku.dataplatform.entity.User;
import com.bimoku.dataplatform.entity.Website;
import com.bimoku.dataplatform.entity.dto.AuthorDTO;
import com.bimoku.dataplatform.entity.dto.BookDTO;
import com.bimoku.dataplatform.entity.dto.BookDetailsDTO;
import com.bimoku.dataplatform.entity.dto.MessageDTO;
import com.bimoku.dataplatform.entity.dto.TagDTO;
import com.bimoku.dataplatform.entity.dto.TranslatorDTO;
import com.bimoku.dataplatform.entity.dto.UserDTO;
import com.bimoku.dataplatform.entity.type.UserType;

public class EntityGenerator {
	
	public static final int N = 5;
	
	public static Book generateBook(String isbn) {
		Book book = new Book();
		book.setName("Book " + isbn);
		book.setIsbn(isbn);
		book.setUuid(isbn);
		book.setCoverPic("http://img33.ddimg.cn/91/6/20740393-1_w.jpg");
		book.setPubPrice(10.0);
		book.setOutline("Outline");
		book.setSaleRank(true);
		return book;
	}

	
	public static List<Book> generateBooks(int n) {
		List<Book> books = new ArrayList<Book>();
		for(int i = 0; i < n; i++) {
			Book book = generateBook("" + i);
			book.setPubPrice(10 + i);
			books.add(book);
		}
		return books;
	}
	
	public static Press generatePress(String name) {
		Press press = new Press();
		press.setName(name);
		press.setPhone("88888888");
		return press;
	}
	
	public static Tag generateTag(String name) {
		Tag tag = new Tag();
		tag.setName(name);
		return tag;
	}
	
	public static Message generateMessage(String content) {
		Message message = new Message();
		message.setContent(content);
		message.setCreateAt(new Date());
		return message;
	}
	
	public static List<Message> generateMessages(int n) {
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < n; i++) {
			messages.add(generateMessage("Message " + n));
		}
		return messages;
	}
	
	public static User generateUser(String name) {
		User u = new User();
		u.setName(name);
		u.setUserType(UserType.BMK);
		return u;
	}
	
	public static Website generateWebsite(String name) {
		Website website = new Website();
		website.setName(name);
		website.setBookStore(true);
		return website;
	}
	
	public static Author generateAuthor(String name) {
		Author author = new Author();
		author.setName(name);
		author.setIntro("Intro " + name);
		return author;
	}
	
	public static AssociatedTag generateAssociatedTag(Book book, Tag tag) {
		AssociatedTag aTag = new AssociatedTag(book, tag);
		book.getAssociatedTags().add(aTag);
		return aTag;
	}
	
	public static List<BookDTO> generateBookDTOs(int n) {
		List<BookDTO> books = new ArrayList<BookDTO>();
		for(int i = 0; i < n; i++) {
			BookDTO book = new BookDTO();
			book.setIsbn("ISBN " + i);
			book.setName("Book " + i);
			book.setCoverPic("http://img33.ddimg.cn/91/6/20740393-1_w.jpg");
			book.setMessages(generateMessageDTOs(5));
			books.add(book);
		}
		return books;
	}
	
	public static List<BookDetailsDTO> generateBookDetailsDTOs(int n) {
		List<BookDetailsDTO> books = new ArrayList<BookDetailsDTO>();
		for(int i = 0; i < n; i++) {
			BookDetailsDTO book = new BookDetailsDTO();
			book.setIsbn("2000" + i);
			book.setName("Book " + i);
			book.setCoverPic("http://img33.ddimg.cn/91/6/20740393-1_w.jpg");
			book.setMessages(generateMessageDTOs(5));
			books.add(book);
		}
		return books;
	}
	
	public static List<AuthorDTO> generateAuthorDTOs(int n) {
		List<AuthorDTO> authors = new ArrayList<AuthorDTO>();
		for(int i = 0; i < n; i++) {
			AuthorDTO author = new AuthorDTO();
			author.setIntro("Intro " + i);
			author.setName("Author " + i);
			authors.add(author);
		}
		return authors;
	}
	
	public static List<TranslatorDTO> generateTranslatorDTOs(int n) {
		List<TranslatorDTO> translators = new ArrayList<TranslatorDTO>();
		for(int i = 0; i < n; i++) {
			TranslatorDTO translator = new TranslatorDTO();
			translator.setIntro("Intro " + i);
			translator.setName("Translator " + i);
			translators.add(translator);
		}
		return translators;
	}
	
	public static List<UserDTO> generateUserDTOs(int n) {
		List<UserDTO> users = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			UserDTO user = new UserDTO();
			user.setId(i);
			user.setName("User" + i);
			user.setUserImage("http://img33.dangdang.com/imghead/0/36/3101266678653-1_g.png");
			users.add(user);
		}
		return users;
	}
	
	public static List<TagDTO> generateTag(int n) {
		List<TagDTO> tags = new ArrayList<TagDTO>();
		for (int i = 0; i < n; i++) {
			TagDTO tag = new TagDTO();
			tag.setName("Tag " + i);
			tags.add(tag);
		}
		return tags;
	}

	public static List<MessageDTO> generateMessageDTOs(int n) {
		List<MessageDTO> comments = new ArrayList<MessageDTO>();
		for (int i = 0; i < n; i++) {
			MessageDTO comment = new MessageDTO();
			comment.setContent("Test Comment " + i);
			comment.setCreateAt(new Date());
			comment.setSite("douban");
			//comment.setUser(generateUserDTOs(1).get(0));
			comments.add(comment);
		}
		return comments;
	}
}
