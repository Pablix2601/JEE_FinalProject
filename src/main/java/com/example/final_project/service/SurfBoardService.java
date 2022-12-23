package com.example.final_project.service;

import com.example.final_project.repositories.SurfBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SurfBoardService {
    private final SurfBoardRepository surfBoardRepository;
}
