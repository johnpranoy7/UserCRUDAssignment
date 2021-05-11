package com.jyalla.demo.service;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jyalla.demo.modal.Blog;
import com.jyalla.demo.repository.BlogRepository;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Override
    public List<Blog> getAllArticles() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getSingleArticle(UUID id) {
        return blogRepository.findById(id)
                .orElse(null);
    }

    @Override
    @Transactional
    public Blog save(@Valid Blog article) {
        return blogRepository.save(article);
    }

    @Override
    public void deleteArticle(Blog delArticle) {
        blogRepository.delete(delArticle);
    }

    @Override
    @Transactional
    public List<Blog> findByTitle(String string) {
        return blogRepository.findByTitle(string);

    }

}
