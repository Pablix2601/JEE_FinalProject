package com.example.final_project.web;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.LikesRepository;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/like")
    public String testLike(){
        return "like";
    }

    @PostMapping("/like")
    @ResponseBody
    public String like (@RequestBody String body, HttpSession session){
        User user = (User) session.getAttribute("user");
        Likes like = new Likes(user.getId(),Long.parseLong(body));
        String res = "none";
        List<Likes > likeExists = likesRepository.findAllByClientAndSurfBoards(like.getClient(), like.getSurfboard());
        System.out.println(likeExists.isEmpty());
        if( ! likeExists.isEmpty()){
            Likes l = likeExists.get(0);
            likesRepository.deleteById(l.getId());
            res = "unliked";
        }
        else{
            likesRepository.save(like);
            res = "liked";

        }

        return res;
    }

}
