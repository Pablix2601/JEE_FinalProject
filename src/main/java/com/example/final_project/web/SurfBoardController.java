package com.example.final_project.web;

import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.LikesRepository;
import com.example.final_project.repositories.SurfBoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller @AllArgsConstructor
public class SurfBoardController {

    private final SurfBoardRepository surfBoardRepository;
    private final LikesRepository likesRepository;

    @GetMapping("/create_surfBoard")
    public String ajouterSurfBoard(Model model, HttpSession session){
            if(session.getAttribute("user") == null){
                return("redirect:connexion");
            }
            model.addAttribute ("user", session.getAttribute("user"));
            User currentUser = (User) session.getAttribute("user");
            model.addAttribute("errors", session.getAttribute("errors"));
            session.removeAttribute("errors");
            SurfBoards sb = new SurfBoards();
            model.addAttribute("surfboard", sb);
            return "surfboard";
        }
        @PostMapping("/store_surfBoard")
        public String saveSurfBoard(@ModelAttribute SurfBoards surfBoards, HttpSession session){

            if (surfBoards.getNom().trim().equals("")) {
                session.setAttribute("errors", Collections.singletonList("Veuillez ajouter un titre a votre recette"));
                return "redirect:/create_surfBoard";
            }
            if (surfBoards.getRef().trim().equals("")) {
                session.setAttribute("errors", Collections.singletonList("Veuillez ajouter un contenu a votre recette"));
                return "redirect:/create_surfBoard";
            }
            User user = (User) session.getAttribute("user");
            surfBoards.setUserId(user.getId());
            surfBoards.setUserPseudo(user.getUsername());
            surfBoardRepository.save(surfBoards);
            return("redirect:/app");
        }


        @GetMapping ("/app")
        public String GetHome (ModelMap map, Model model , HttpSession session) {
            if(session.getAttribute("user") == null){
                return("redirect:connexion");
            }
            model.addAttribute ("user", session.getAttribute("user"));
            map.addAttribute("surfBoard",  surfBoardRepository.findAll());
            return "app";
        }

        @GetMapping(value = "/delete_surfBoard/{id}")
        public String DeleteSurfBoard(@PathVariable String id){
            likesRepository.deleteBySurfboard_id(Long.parseLong(id));
            surfBoardRepository.deleteById(Long.parseLong(id));
            return "redirect:/profil";
        }

        @PostMapping("/modifiersurfBoard/{id}")
        public String modifierSurfBoard(@RequestParam("ref") String content, @PathVariable String id){
            SurfBoards sb = (SurfBoards) surfBoardRepository.findAllById(Collections.singleton(Long.parseLong(id)));
            sb.setRef(content);
            surfBoardRepository.save(sb);
            return("redirect:/profil");
        }
}
