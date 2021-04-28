package com.jyalla.demo.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.jyalla.demo.modal.Blog;

@Repository
@RepositoryRestResource(collectionResourceRel = "blogs", path = "blogs", excerptProjection = Blog.class)
public interface BlogRepository extends JpaRepository<Blog, UUID> {

}
