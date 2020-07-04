package com.main.packme.repository;

import com.main.packme.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends  JpaRepository<User,Long> {

    User findByUsername(String username);
}
