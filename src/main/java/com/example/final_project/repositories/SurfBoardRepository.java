package com.example.final_project.repositories;

import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface SurfBoardRepository extends JpaRepository<SurfBoards, Long> {
    List<SurfBoards> findAllByUserId(Long id);
    List<SurfBoards> findAll();
}
