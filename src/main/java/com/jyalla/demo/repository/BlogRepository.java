package com.jyalla.demo.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jyalla.demo.modal.Blog;

@Repository
// @RepositoryRestResource(collectionResourceRel = "blogs", path = "blogs")
public interface BlogRepository extends JpaRepository<Blog, UUID> {

    List<Blog> findByTitle(String title);

}
