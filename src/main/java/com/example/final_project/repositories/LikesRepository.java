package com.example.final_project.repositories;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    void deleteAllBySurfboard(Long id);
    Likes getByClientAndSurfboard(Long clientId, Long surfBoardId);

    List<Likes> findAllByClientAndSurfboard(Long clientId, Long surfBoardId);
    Boolean existsByClientAndSurfboard(Long clientId, Long surfBoardId);
    Likes getById(Long id);
}
