package com.jyalla.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import com.jyalla.demo.BaseClass;
import com.jyalla.demo.exception.ArticleNotFoundException;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.BlogService;
import com.jyalla.demo.service.UserService;


@TestMethodOrder(OrderAnnotation.class)
class BlogControllerMockTest extends BaseClass {


    public static Logger logger = LoggerFactory.getLogger(BlogControllerMockTest.class);

    @Mock
    UserService userService;

    @Mock
    BlogService blogService;

    @InjectMocks
    BlogController blogController;

    private static User savedUser;

    @Test
    @Order(1)
    void getBlogs() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(blogService.getAllArticles())
                .thenReturn(List.of(new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin")));
        blogController.getArticles();

        logger.info("getBlogs {}", blogController.getArticles());
        assertEquals(1, blogController.getArticles()
                .getBody()
                .size());
    }

    @Test
    @Order(3)
    void getSingleBlog() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        when(blogService.getSingleArticle(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47")))
                .thenReturn(new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin"));
        blogController.getOneArticle(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"));
        logger.info("articleId is {}", blogController.getOneArticle(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47")));
        assertEquals(true, blogController.getOneArticle(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"))
                .getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(2)
    void postBlog() {

        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        Blog blog = new Blog();
        when(blogService.save(blog))
                .thenReturn(new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin"));
        when(userService.getSingleUser(user.getId())).thenReturn(user);
        ResponseEntity<Object> saveArticle = blogController.saveArticle(blog, user.getId());
        assertEquals(true, saveArticle.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(4)
    void putBlog() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        Blog blog = new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin");
        when(blogService.save(blog))
                .thenReturn(new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin"));
        when(blogService.getSingleArticle(any(UUID.class))).thenReturn(blog);
        ResponseEntity<Blog> updateArticle = blogController.updateArticle(blog, user.getId());
        assertEquals(true, updateArticle.getStatusCode()
                .is2xxSuccessful());
    }

    @Test
    @Order(5)
    void deleteBlog() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        Blog blog = new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin");
        doNothing().when(blogService)
                .deleteArticle(blog);
        when(blogService.getSingleArticle(any(UUID.class))).thenReturn(blog);
        ResponseEntity<Blog> updateArticle = blogController.deleteArticle(blog.getId());
        assertEquals(true, updateArticle.getStatusCode()
                .is2xxSuccessful());
    }

    @Test()
    @Order(6)
    void articleNotFound() {
        User user = new User(UUID.fromString("db47ce58-6f03-4d6d-9902-3837c925406d"), "dummyUser", "dummy@email.com", "1234", "", true, "Admin", new Date(),
                "$2a$04$8T6i2fjNU54gI8LgArCLEOP8XMMSw/.bq/iRhuL6Y.ha46NyKAMaq", 2);
        Blog blog = new Blog(UUID.fromString("14d5fb10-e63f-4cb9-8c05-4717555cfd47"), "DummyTitle1", "DumDescription1", "google.com", user, new Date(), "Admin");
        when(blogService.getSingleArticle(any(UUID.class))).thenReturn(null);
        assertThrows(ArticleNotFoundException.class, () -> blogController.deleteArticle(blog.getId()));
    }
}
