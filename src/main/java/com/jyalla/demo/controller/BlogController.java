package com.jyalla.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jyalla.demo.exception.ArticleNotFoundException;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.modal.User;
import com.jyalla.demo.service.BlogService;
import com.jyalla.demo.service.UserService;

@RestController
@RequestMapping(path = "/rest")
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/blog")
    public ResponseEntity<List<Blog>> getArticles() {
        logger.info("getArticles() is Executed");
        List<Blog> allArticles = blogService.getAllArticles();
        return new ResponseEntity<>(allArticles, HttpStatus.OK);
    }

    @GetMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> getOneArticle(@PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("getOneArticle() " + id + " is Executed");
        Blog singleArticle = blogService.getSingleArticle(id);
        if (singleArticle == null)
            throw new ArticleNotFoundException("Userid not Found " + id);
        return new ResponseEntity<>(singleArticle, HttpStatus.OK);
    }

    @PostMapping(path = "/blog/{authorId}")
    public ResponseEntity<Object> saveArticle(@RequestBody @Valid Blog article, @PathVariable("authorId") UUID authorId) throws ArticleNotFoundException {
        logger.info("saveArticle() " + article + " is Executed");
        article.setUpdatedBy("Admin");
        article.setUpdatedOn(new Date());
        article.setCreatedBy("Admin");
        article.setCreatedOn(new Date());
        User singleUser = userService.getSingleUser(authorId);
        if (singleUser == null)
            throw new ArticleNotFoundException("Article Not Found" + authorId);
        article.setAuthorId(singleUser);
        Blog savedArticle;
        try {
            savedArticle = blogService.save(article);
            logger.info("saved Article is " + savedArticle);
            return new ResponseEntity<>("Created Successfully" + savedArticle, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while saving Article" + e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> updateArticle(@RequestBody @Valid Blog article, @PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("updateArticle() is Executed" + article);
        Blog blog;
        Blog singleArticle = blogService.getSingleArticle(id);

        if (singleArticle == null)
            throw new ArticleNotFoundException("Article Not Found" + id);
        logger.info("Found existing Blog for PUT" + singleArticle);
        singleArticle.setTitle(article.getTitle());
        singleArticle.setDescription(article.getDescription());
        singleArticle.setUrl(article.getUrl());
        singleArticle.setUpdatedBy("Admin");
        singleArticle.setUpdatedOn(new Date());
        blog = blogService.save(singleArticle);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @DeleteMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> deleteArticle(@PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("deleteArticle() is Executed");
        Blog delArticle = blogService.getSingleArticle(id);
        logger.info("deleted article is" + delArticle);
        if (delArticle == null)
            throw new ArticleNotFoundException("Article Not Found" + id);
        try {
            blogService.deleteArticle(delArticle);
        } catch (Exception e) {
            logger.error("error Deleting the Article" + delArticle);
            return new ResponseEntity<Blog>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Blog>(HttpStatus.OK);

    }
}
