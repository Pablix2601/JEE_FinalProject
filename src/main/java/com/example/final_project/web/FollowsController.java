package com.example.final_project.web;

import com.example.final_project.entities.Follows;
import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.FollowsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@AllArgsConstructor
public class FollowsController {
    private  final FollowsRepository followsRepository;

    @GetMapping("/profil/{userId}/follow/{followedUserId}")
    public String GetFollow(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "followedUserId") Long follUserId, @PathVariable(value = "userId") Long userId) {
        User user = (User) request.getSession().getAttribute("user");
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (user.getId().equals(userId)) {
            rAttribute.addAttribute("followImpossible", true);
            return "redirect:/profil/"+userId;
        }

        followsRepository.save(new Follows(null,userId,user.getId()));
        return "redirect:/profil/"+userId;
    }

    @GetMapping("/profil/{userId}/unfollow/{followedUserId}")
    public String GetUnfollow(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "followedUserId") Long fId, @PathVariable(value = "userId") Long userId) {
        User user = (User) request.getSession().getAttribute("user");
        Follows follows = followsRepository.getByIdUserAndIdUserFollowed(user.getId(),userId);
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        }
        rAttribute.addAttribute("Connected", true);
        if (!follows.getIdUser().equals(user.getId())) {
            rAttribute.addAttribute("unfollowImpossible", true);
            return "redirect:/profil/"+userId;
        }
        followsRepository.deleteById(follows.getId());
        return "redirect:/profil/"+userId;
    }
}
