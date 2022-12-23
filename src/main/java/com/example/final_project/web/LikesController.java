package com.example.final_project.web;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.LikesRepository;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class LikesController {
    private final LikesRepository likesRepository;



}
