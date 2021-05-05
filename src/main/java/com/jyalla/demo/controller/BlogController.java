package com.jyalla.demo.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.jyalla.demo.service.BlogService;
import com.jyalla.demo.service.UserService;

@RestController
@RequestMapping(path = "/rest")
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    private String articleErrorMessage = "Article Not Found";
    private String adminStr = "Admin";

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @GetMapping(path = "/blog")
    public ResponseEntity<List<Blog>> getArticles() {
        logger.info("getArticles() is Executed");
        List<Blog> allArticles = blogService.getAllArticles();
        return new ResponseEntity<>(allArticles, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> getOneArticle(@PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("getOneArticle() {} is Executed", id);
        var singleArticle = blogService.getSingleArticle(id);
        if (singleArticle == null)
            throw new ArticleNotFoundException(articleErrorMessage + id);
        return new ResponseEntity<>(singleArticle, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping(path = "/blog/{authorId}")
    public ResponseEntity<Object> saveArticle(@RequestBody @Valid Blog article, @PathVariable("authorId") UUID authorId) throws ArticleNotFoundException {
        logger.info("saveArticle() {} is Executed", article);
        article.setUpdatedBy(adminStr);
        article.setUpdatedOn(new Date());
        article.setCreatedBy(adminStr);
        article.setCreatedOn(new Date());
        var singleUser = userService.getSingleUser(authorId);
        if (singleUser == null)
            throw new ArticleNotFoundException(articleErrorMessage + authorId);
        article.setAuthorId(singleUser);
        Blog savedArticle;
        try {
            savedArticle = blogService.save(article);
            logger.info("saved Article is {}", savedArticle);
            return new ResponseEntity<>("Created Successfully" + savedArticle, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while saving Article {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> updateArticle(@RequestBody @Valid Blog article, @PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("updateArticle() is Executed {}", article);
        var singleArticle = blogService.getSingleArticle(id);

        if (singleArticle == null)
            throw new ArticleNotFoundException(articleErrorMessage + id);
        logger.info("Found existing Blog for PUT {}", singleArticle);
        singleArticle.setTitle(article.getTitle());
        singleArticle.setDescription(article.getDescription());
        singleArticle.setUrl(article.getUrl());
        singleArticle.setUpdatedBy(adminStr);
        singleArticle.setUpdatedOn(new Date());
        blogService.save(singleArticle);
        return new ResponseEntity<>(singleArticle, new HttpHeaders(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/blog/{id}")
    public ResponseEntity<Blog> deleteArticle(@PathVariable("id") UUID id) throws ArticleNotFoundException {
        logger.info("deleteArticle() is Executed");
        var delArticle = blogService.getSingleArticle(id);
        logger.info("deleted article is {}", delArticle);
        if (delArticle == null)
            throw new ArticleNotFoundException(articleErrorMessage + id);
        try {
            blogService.deleteArticle(delArticle);
        } catch (Exception e) {
            logger.error("error Deleting the Article {}", delArticle);
            return new ResponseEntity<>(delArticle, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(delArticle, new HttpHeaders(), HttpStatus.OK);

    }
}
