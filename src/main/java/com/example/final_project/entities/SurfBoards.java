package com.example.final_project.entities;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class SurfBoards {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String content;
    @Lob()
    private byte[] image;
    private Long userId;
    private String userUsername;
    private String imgEncoded;
}
