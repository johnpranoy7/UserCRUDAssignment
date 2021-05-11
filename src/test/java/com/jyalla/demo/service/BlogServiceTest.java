package com.jyalla.demo.service;

import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Ignore;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.modal.User;


@TestMethodOrder(OrderAnnotation.class)
public class BlogServiceTest extends BaseClass {


    public static Logger logger = LoggerFactory.getLogger(BlogServiceTest.class);

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    static User savedUser;
    static UUID articleId;

    @Test

    @Order(1)
    public void getAllArticles() {
        assertTrue(blogService.getAllArticles()
                .size() > 0);
    }

    @Test

    @Order(2)
    public void saveArticle() {
        User user = new User();
        user.setEmail("servicetest@test.com");
        user.setUsername("hello");
        user.setPhoneNo("6752398751");
        savedUser = userService.save(user);
        assertTrue(savedUser != null);

        Blog blog = new Blog();
        blog.setTitle("DBMS");
        blog.setDescription("Database Management");
        blog.setUrl("www.dbms.com");
        blog.setAuthorId(savedUser);

        Blog savedBlog = blogService.save(blog);
        assertTrue(savedBlog != null);
        articleId = savedBlog.getId();

    }

    @Test

    @Order(3)
    public void getArticleById() {
        assertTrue(blogService.getSingleArticle(articleId) != null);
    }

    @Ignore

    @Order(6)
    public void saveArticle_MissingTitle() {
        Blog blog = new Blog(); //
        blog.setTitle("DBMS");
        blog.setDescription("Database Management");
        blog.setUrl("www.dbms.com");
        blog.setAuthorId(savedUser);
        Blog savedBlog = blogService.save(blog);
        assertTrue(savedBlog != null);
    }

    @Ignore

    @Order(7)
    public void saveArticle_MissingDescription() {
        Blog blog = new Blog();
        blog.setTitle("DBMS"); // blog.setDescription("Database Management");
        blog.setUrl("www.dbms.com");
        blog.setAuthorId(savedUser);
        Blog savedBlog = blogService.save(blog);
        assertTrue(savedBlog != null);
    }

    @Test

    @Order(4)
    public void modifyArticle() {
        Blog singleArticle = blogService.getSingleArticle(articleId);
        singleArticle.setDescription("Database");
        Blog savedBlog = blogService.save(singleArticle);
        assertTrue(savedBlog.getDescription()
                .equals("Database"));
    }

    @Test

    @Order(5)
    public void deleteUser() {
        Blog singleArticle = blogService.getSingleArticle(articleId);
        blogService.deleteArticle(singleArticle);
        userService.delete(savedUser);
        assertTrue(blogService.getSingleArticle(articleId) == null);

    }

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Order(8)
    public void findByTitle() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Blog> title = blogService.findByTitle("DS");
        logger.info("Inside findByTitle() TestCase " + title.toArray());
        logger.info("title.get(0) " + title.get(0));

        session.getTransaction()
                .commit();
        session.close();


    }

}
