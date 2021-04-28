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
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.service.BlogService;

@RestController
@RequestMapping(path = "/rest")
public class BlogController {

    private static Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    BlogService blogService;

    @GetMapping(path = "/blog")
    public List<Blog> getArticles() {
        logger.info("getArticles() is Executed");
        return blogService.getAllArticles();
    }

    @GetMapping(path = "/blog/{id}")
    public Blog getOneArticle(@PathVariable("id") UUID id) {
        logger.info("getOneArticle() " + id + " is Executed");
        return blogService.getSingleArticle(id);
    }

    @PostMapping(path = "/blog")
    public ResponseEntity<Object> saveArticle(@RequestBody @Valid Blog article) {
        logger.info("saveArticle() " + article + " is Executed");
        article.setUpdatedBy("Admin");
        article.setUpdatedOn(new Date());
        article.setCreatedBy("Admin");
        article.setCreatedOn(new Date());
        Blog savedArticle;
        try {
            savedArticle = blogService.save(article);
            logger.info("saved Article is " + savedArticle);
            return new ResponseEntity<Object>("Created Successfully" + savedArticle, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while saving Article" + e);
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/blog/{id}")
    public ResponseEntity<Object> updateArticle(@RequestBody @Valid Blog article, @PathVariable("id") UUID id) {
        logger.info("updateArticle() is Executed" + article);
        Blog blog;
        Blog singleArticle = blogService.getSingleArticle(id);
        if (singleArticle != null) {
            logger.info("Found existing Blog for PUT" + singleArticle);
            singleArticle.setTitle(article.getTitle());
            singleArticle.setDescription(article.getDescription());
            singleArticle.setUrl(article.getUrl());
            singleArticle.setUpdatedBy("Admin");
            singleArticle.setUpdatedOn(new Date());
            blog = blogService.save(singleArticle);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .build();
        } else {
            // logger.info("Creating new Blog in PUT");
            // article.setUpdatedBy("Admin");
            // article.setUpdatedOn(new Date());
            // article.setCreatedBy("Admin");
            // article.setCreatedOn(new Date());
            // blog = blogService.save(singleArticle);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping(path = "/blog/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable("id") UUID id) {
        logger.info("deleteArticle() is Executed");
        Blog delArticle = blogService.getSingleArticle(id);
        logger.info("deleted article is" + delArticle);
        try {
            blogService.deleteArticle(delArticle);
        } catch (Exception e) {
            logger.error("error Deleting the Article" + delArticle);
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);

    }
}
