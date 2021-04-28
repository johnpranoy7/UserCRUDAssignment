package com.jyalla.demo;

import static org.junit.Assert.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.BlogService;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
public class BlogControllerTest extends BaseClass {

    private static final String HTTP_LOCALHOST_8080_REST_USER = "http://localhost:8080/rest/User/";

    private static final String HTTP_LOCALHOST_8080_REST_BLOG = "http://localhost:8080/rest/blog/";

    public static Logger logger = LoggerFactory.getLogger(BlogControllerTest.class);

    @Autowired
    TestRestTemplate template;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    private static User savedUser;

    static UUID articleId;

    static UUID userId;

    @Test
    @Order(1)
    public void getBlogs() {
        ResponseEntity<String> entity = template.getForEntity(HTTP_LOCALHOST_8080_REST_BLOG, String.class);
        // template.getRestTemplate()
        // .setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(3)
    public void getSingleBlog() {
        Blog blog = blogService.findByTitle("DBMS")
                .get(0);
        logger.info("Inside getSingleBlog(), Blog is" + blog);
        articleId = blog.getId();
        logger.info("articleId is" + articleId.toString());

        ResponseEntity<Blog> entity = template.getForEntity(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), Blog.class);
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void postBlog() throws RestClientException, URISyntaxException {
        User user = new User();
        user.setEmail("servicetest@test.com");
        user.setUsername("hello");
        user.setPhoneNo("6752398751");
        logger.info("Inside postBlog()");
        User savedUser = userService.save(user);
        logger.info("user is " + savedUser);
        userId = savedUser.getId();
        logger.info("userId " + userId);

        Blog blog = new Blog();
        blog.setTitle("DBMS");
        blog.setDescription("Database Management");
        blog.setUrl("www.dbms.com");

        // template.getRestTemplate()
        // .setMessageConverters(List.of(new MappingJackson2HttpMessageConverter()));

        ResponseEntity<String> entity = this.template.postForEntity(new URI(HTTP_LOCALHOST_8080_REST_BLOG + userId.toString()), blog, String.class);
        logger.info(entity.getStatusCode()
                .toString());
        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    public void putBlog() {
        Blog blog = new Blog();
        blog.setTitle("DBMS");
        blog.setDescription("Database Management-II");
        blog.setUrl("www.dbms.com");
        template.put(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), blog);
    }

    @Test
    @Order(5)
    public void deleteBlog() throws RestClientException, URISyntaxException {
        template.delete(new URI(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString()));
    }

    @Test
    @Order(6)
    public void deleteUser() throws RestClientException, URISyntaxException {
        logger.info("inside deleteUser() " + userId);
        template.delete(new URI(HTTP_LOCALHOST_8080_REST_USER + userId.toString()));
    }
}
