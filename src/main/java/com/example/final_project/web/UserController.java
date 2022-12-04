package com.example.final_project.web;

import com.example.final_project.entities.User;
import com.example.final_project.repositories.SurfBoardRepository;
import com.example.final_project.repositories.UserRepository;
import com.example.final_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.mindrot.jbcrypt.BCrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

@Controller @AllArgsConstructor
public class UserController{
    private final SurfBoardRepository surfBoardRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping ("/register")
    public String getRegister (Model model, HttpSession session) {
        session.removeAttribute("user");
        model.addAttribute ("user", new User());
        model.addAttribute ("errors", session.getAttribute("errors"));
        session.removeAttribute("errors");
        return "register";
    }
    /* test */
    @PostMapping("/register")
    public String PostRegister (@ModelAttribute User user, HttpSession session) throws NoSuchAlgorithmException {
        String passHash = getMd5(user.getPassword());
        User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), passHash);
        if (newUser == null) {
            session.setAttribute ("errors", Collections.singletonList("Nom d'utilisateur ou mot de passe inconnu"));
            return "redirect:/register";
        }
        session.setAttribute("user", newUser);
        return "redirect:/app";
    }

    @GetMapping("/connexion")
    public String getConnexion (HttpServletRequest request) {
        if (request.getSession().getAttribute("user")!=null) {
            return "redirect:/app";
        }
        return "connexion";
    }

    @PostMapping("/connexion")
    public String PostConnexion (@RequestParam Map<String,String> allParams, HttpServletRequest request, RedirectAttributes rAttribute) {
        if (userService.allRegisterParams(allParams)) {
            User user = userRepository.getUsersByUsername(allParams.get("username"));
            if(user == null || !(BCrypt.checkpw(allParams.get("password"),user.getPassword()))) {
                rAttribute.addAttribute("cannotConnect",true);
                return "redirect:/connexion";
            } else {
                request.getSession().setAttribute("user",user);
                return "redirect:/app";
            }
        } else {
            rAttribute.addAttribute("invalidParam",true);
            return "redirect:/connexion";
        }
    }

    @GetMapping ("/profil")
    public String GetProfil (Model model, HttpSession session, ModelMap map) {
        if(session.getAttribute("user") == null){
            return("redirect:connexion");
        }
        model.addAttribute ("user", session.getAttribute("user"));
        User currentUser = (User) session.getAttribute("user");
        map.addAttribute("myRecipies", surfBoardRepository.findAllByUserId(currentUser.getId()));
        return "profil";
    }


    public String getMd5(String password) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
