package com.example.final_project.repositories;

import com.example.final_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.Entity;

import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUsersByUsername (String username);
    User getUsersById(Long id);
}
