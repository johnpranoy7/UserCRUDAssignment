package com.jyalla.demo.service;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import com.jyalla.demo.modal.Blog;

public interface BlogService {

    List<Blog> getAllArticles();

    Blog getSingleArticle(UUID id);

    Blog save(@Valid Blog article);

    void deleteArticle(Blog delArticle);

}
