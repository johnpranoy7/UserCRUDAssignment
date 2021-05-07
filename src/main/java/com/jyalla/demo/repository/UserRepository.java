package com.jyalla.demo.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jyalla.demo.modal.User;

@Repository
// @RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByEmail(String email);

    User findByUsername(String username);
}
