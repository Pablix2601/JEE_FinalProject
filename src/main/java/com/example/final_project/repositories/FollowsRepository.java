package com.example.final_project.repositories;

import com.example.final_project.entities.Follows;
import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowsRepository extends JpaRepository<Follows, Long> {
    List<Follows> findAllByIdUser(Long id);
    Follows getById(Long id);
    Follows getByIdUserAndIdUserFollowed(Long idUser, Long idUserFollowed);
    Boolean existsByIdUserAndIdUserFollowed(Long idUser, Long idUserFollowed);
    @Query("SELECT u FROM User as u INNER JOIN Follows as f ON f.idUser=?1 where u.id = f.idUserFollowed")
    List<User> findAllFollowedUserByUserId(Long userId);
}
