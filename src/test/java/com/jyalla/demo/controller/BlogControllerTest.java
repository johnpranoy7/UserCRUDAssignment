package com.jyalla.demo.controller;

import static org.junit.Assert.assertEquals;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.modal.JwtRequest;
import com.jyalla.demo.modal.JwtResponse;
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
    static String token;

    @Test
    @Order(1)
    void getToken() {

        JwtRequest req = new JwtRequest("john", "john");
        ResponseEntity<JwtResponse> entity = template.postForEntity("http://localhost:8080/authenticate", req, JwtResponse.class);
        token = entity.getBody()
                .getToken();
        logger.info(token);

        assertTrue(entity.getStatusCode()
                .is2xxSuccessful());

    }

    @Test
    @Order(5)
    public void getBlogs() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG, HttpMethod.GET, entity, String.class);

        // ResponseEntity<String> entity = template.getForEntity(HTTP_LOCALHOST_8080_REST_BLOG,
        // String.class);
        // template.getRestTemplate() // .setMessageConverters(List.of(new
        // MappingJackson2HttpMessageConverter()));
        assertTrue(exchange.getStatusCode()
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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(blog, headers);

        ResponseEntity<Blog> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), HttpMethod.GET, entity, Blog.class);

        assertTrue(exchange.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    public void postBlog() throws RestClientException, URISyntaxException {
        User user = new User();
        user.setEmail("servicetest@test.com");
        user.setUsername("servicetest");
        user.setStatus(false);
        user.setProfilePic("");
        user.setPhoneNo("6752398751");
        user.setPassword("servicetest");
        user.setRole(2);
        logger.info("Inside postBlog()");
        User savedUser = userService.save(user);
        logger.info("user is " + savedUser);
        userId = savedUser.getId();
        logger.info("userId " + userId);

        Blog blog = new Blog();
        blog.setTitle("DBMS");
        blog.setDescription("Database Management");
        blog.setUrl("www.dbms.com");

        // template.getRestTemplate() // .setMessageConverters(List.of(new
        // MappingJackson2HttpMessageConverter()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(blog, headers);

        ResponseEntity<String> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG + userId.toString(), HttpMethod.POST, entity, String.class);

        // ResponseEntity<String> entity = this.template.postForEntity(new
        // URI(HTTP_LOCALHOST_8080_REST_BLOG + userId.toString()), blog, String.class);
        logger.info(exchange.toString());
        assertTrue(exchange.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    public void putBlog() {
        Blog blog = new Blog();
        blog.setTitle("DBMS");
        blog.setDescription("Database Management-II");
        blog.setUrl("www.dbms.com");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(blog, headers);

        ResponseEntity<String> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), HttpMethod.PUT, entity, String.class);
        logger.info(exchange.toString());
        assertTrue(exchange.getStatusCode()
                .is2xxSuccessful());

        // template.put(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), blog);
    }

    @Test
    @Order(6)
    public void deleteBlog() throws RestClientException, URISyntaxException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<String> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString(), HttpMethod.DELETE, entity, String.class);

        // template.delete(new URI(HTTP_LOCALHOST_8080_REST_BLOG + articleId.toString()));
    }

    @Test
    @Order(7)
    public void deleteUser() throws RestClientException, URISyntaxException {
        logger.info("inside deleteUser() " + userId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);
        ResponseEntity<String> entityRes = template.exchange(HTTP_LOCALHOST_8080_REST_USER + userId.toString(), HttpMethod.DELETE, entity, String.class);

        template.delete(new URI(HTTP_LOCALHOST_8080_REST_USER + userId.toString()));
    }

    @Test
    @Order(8)
    void getBlogNegative() {
        UUID dummyUUID = UUID.fromString("0095e3a3-eef9-4bd2-9744-da52d6dc3e68");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity(headers);

        ResponseEntity<Blog> exchange = template.exchange(HTTP_LOCALHOST_8080_REST_BLOG + dummyUUID.toString(), HttpMethod.GET, entity, Blog.class);

        assertEquals(exchange.getStatusCode()
                .value(), 404);
    }

}
