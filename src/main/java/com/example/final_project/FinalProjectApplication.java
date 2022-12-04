package com.example.final_project;

import com.example.final_project.entities.SurfBoards;
import com.example.final_project.repositories.SurfBoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(SurfBoardRepository surfBoardRepository){
        return args -> {
        };
    }
}
