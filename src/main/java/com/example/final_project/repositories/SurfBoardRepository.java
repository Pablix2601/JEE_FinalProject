package com.example.final_project.repositories;

import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface SurfBoardRepository extends JpaRepository<SurfBoards, Long> {
    List<SurfBoards> findAll();

    @Query("Select sb from Likes l, SurfBoards sb where l.client = ?1 and l.surfboard = sb.id")
    List<SurfBoards> findLikedSurfBoardByUserId(Long userId);

    List<SurfBoards> findAllByUserId(Long userId);

    SurfBoards getById(Long id);

}
