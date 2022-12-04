package com.example.final_project.repositories;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("select t from Likes t, User u, SurfBoards  sb where u.id = :id1 and sb.id = :id2")
    List<Likes> findAllByClientAndSurfBoards(Long id1, Long id2) ;

    @Query("Select r from SurfBoards r where r.id in (Select r.id from User u where u.id = :id3)")
    List<SurfBoards> findAllByIdInUser(Long id3);

    @Modifying
    @Transactional
    @Query("Delete from Likes r where r.id in (SELECT r.id from SurfBoards sb where sb.id= :id4)")
    void deleteBySurfboard_id(Long id4);
}
