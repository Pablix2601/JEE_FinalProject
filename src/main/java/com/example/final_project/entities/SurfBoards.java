package com.example.final_project.entities;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class SurfBoards {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String ref;
    private double prix;
    private Long userId;
    private String userPseudo;

}
