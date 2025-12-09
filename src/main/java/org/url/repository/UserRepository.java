package org.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.url.entity.UrlRecord;
import org.url.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String username);

    boolean existsByEmail(String email);
}
