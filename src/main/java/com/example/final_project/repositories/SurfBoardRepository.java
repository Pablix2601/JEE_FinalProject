package com.example.final_project.repositories;

import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurfBoardRepository extends JpaRepository<SurfBoards, Long> {
    List<SurfBoards> findAll();

    @Query("SELECT sb FROM SurfBoards as sb INNER JOIN Likes as l ON l.client=?1 where sb.id = l.surfboard")
    List<SurfBoards> findAllLikedSurfBoardByUserId(Long userId);

    List<SurfBoards> findAllByUserId(Long userId);

    SurfBoards getById(Long id);

}
