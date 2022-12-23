package com.example.final_project.repositories;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteAllBySurfboard(Long id);
}
