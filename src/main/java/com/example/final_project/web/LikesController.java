package com.example.final_project.web;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.LikesRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.final_project.repositories.SurfBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class LikesController {
    private final LikesRepository likesRepository;
    private final SurfBoardRepository surfBoardRepository;

    @GetMapping("/profil/{userId}/dislike/{lId}")
    public String GetDislike(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "lId") Long lId, @PathVariable(value = "userId") Long userId){
        User user = (User) request.getSession().getAttribute("user");
        Likes like = likesRepository.getById(lId);
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (!like.getClient().equals(user.getId())) {
            rAttribute.addAttribute("dislikeImpossible", true);
            return "redirect:/profil/"+userId;
        }
        likesRepository.deleteById(lId);
        return "redirect:/profil/"+userId;
    }

    @GetMapping("/index/{userId}/dislike/{lId}")
    public String GetIndexDislike(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "lId") Long lId, @PathVariable(value = "userId") Long userId){
        User user = (User) request.getSession().getAttribute("user");
        Likes like = likesRepository.getById(lId);
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (!like.getClient().equals(user.getId())) {
            rAttribute.addAttribute("dislikeImpossible", true);
            return "redirect:/index/"+userId;
        }
        likesRepository.deleteById(lId);
        return "redirect:/index/"+userId;
    }


    @GetMapping("/profil/{userId}/like/{sbId}")
    public String GetInProfilLike(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "sbId") Long sbId, @PathVariable(value = "userId") Long userId) {
        User user = (User) request.getSession().getAttribute("user");
        SurfBoards sb = surfBoardRepository.getById(sbId);
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (sb.getUserId().equals(user.getId())) {
            rAttribute.addAttribute("likeImpossible", true);
            return "redirect:/profil/"+userId;
        }
        Likes like = new Likes(null, user.getId(), sbId);
        likesRepository.save(like);
        return "redirect:/profil/"+userId;
    }

    @GetMapping("/index/{userId}/like/{sbId}")
    public String GetInIndexLike(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "userId") Long userId,@PathVariable(value = "sbId") Long sbId) {
        User user = (User) request.getSession().getAttribute("user");
        SurfBoards sb = surfBoardRepository.getById(sbId);
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (user.getId().equals(sb.getUserId())) {
            rAttribute.addAttribute("likeImpossible", true);
            return "redirect:/index/"+userId;
        }
        Likes like = new Likes(null, user.getId(), sbId);
        likesRepository.save(like);
        return "redirect:/index/"+userId ;
    }
}
