package com.example.final_project.web;

import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.SurfBoardRepository;
import com.example.final_project.repositories.UserRepository;
import com.example.final_project.service.ImgService;
import com.example.final_project.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.mindrot.jbcrypt.BCrypt;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

@Controller @AllArgsConstructor
public class UserController{
    private final SurfBoardRepository surfBoardRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ImgService imgService;

    @GetMapping ("/register")
    public String getRegister (HttpServletRequest request) {
        if (request.getSession().getAttribute("user")!=null) {
            return "redirect:/";
        }
        return "register";
    }
    /* test */
    @PostMapping("/register")
    public String PostRegister (@RequestParam Map<String,String> allParams, HttpServletRequest request, RedirectAttributes rAttribute){
        if (/*userService.allRegisterParams(allParams)*/ true) {
            User user = new User(
                    null, allParams.get("username"),
                    BCrypt.hashpw(allParams.get("password"),BCrypt.gensalt()),allParams.get("email"),
                    allParams.get("lastname"),allParams.get("firstname"),null,null
            );
            userRepository.save(user);
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        } else {
            rAttribute.addAttribute("invalidParam",true);
            return "redirect:/register";
        }
    }

    @GetMapping("/connexion")
    public String getConnexion (HttpServletRequest request) {
        if (request.getSession().getAttribute("user")!=null) {
            return "redirect:/";
        }
        return "connexion";
    }

    @PostMapping("/connexion")
    public String PostConnexion (@RequestParam Map<String,String> allParams, HttpServletRequest request, RedirectAttributes rAttribute) {
        if (/*userService.allConnexionParams(allParams)*/ true) {
            User user = userRepository.getUsersByUsername(allParams.get("username"));
            if(user == null || !(BCrypt.checkpw(allParams.get("password"),user.getPassword()))) {
                rAttribute.addAttribute("cannotConnect",true);
                return "redirect:/connexion";
            } else {
                request.getSession().setAttribute("user",user);
                return "redirect:/";
            }
        } else {
            rAttribute.addAttribute("invalidParam",true);
            return "redirect:/connexion";
        }
    }

    @GetMapping("/profil")
    public String GetProfil (HttpServletRequest request, RedirectAttributes rAttribute) {
        if( request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/connexion";
        } else {
            rAttribute.addAttribute("Connected", true);
            User user = (User) request.getSession().getAttribute("user");
            return "redirect:/profil/" + user.getId();
        }
    }

    @GetMapping(value = "/profil/{id}")
    public String GetProfilById (HttpServletRequest request, @PathVariable Long id, RedirectAttributes rAttribute) {
        HttpSession session = request.getSession();
        List<SurfBoards> listYourSb = ImgService.stringImgEncoded(surfBoardRepository.findAllByUserId(id));
        if (listYourSb == null) {
            session.setAttribute("noYourSurfBoard",  false);
        } else {
            session.setAttribute("noYourSurfBoard",  true);
            session.setAttribute("yourSurfBoard", listYourSb);
        }

        List<SurfBoards> listLikedSb = ImgService.stringImgEncoded(surfBoardRepository.findLikedSurfBoardByUserId(id));
        if ( listLikedSb == null) {
            session.setAttribute("noLikedSurfBoard",  false);
        } else {
            session.setAttribute("noLikedSurfBoard",  true);
            session.setAttribute("likedSurfBoard",  listLikedSb);
        }
        User profilUser = userRepository.getUsersById(id);
        User user = (User) session.getAttribute("user");
        if( user == null || user.getId() != profilUser.getId()) {
            session.setAttribute("notYourProfil", true);
            if (profilUser.getImage() == null) {
                profilUser.setImgEncoded(profilUser.getPrenom().charAt(0)+""+profilUser.getNom().charAt(0));
            } else {
                profilUser.setImgEncoded(new String(Base64.getEncoder().encode(ImgService.decompressImage(profilUser.getImage())), StandardCharsets.UTF_8));
            }
            if (user == null) {
                session.setAttribute("connected", false);
            } else {
                session.setAttribute("connected", true);
            }
        } else {
            session.setAttribute("notYourProfil", false);
            if (profilUser.getImage() == null) {
                profilUser.setImgEncoded(profilUser.getPrenom().charAt(0)+""+profilUser.getNom().charAt(0));
            } else {
                profilUser.setImgEncoded(new String(Base64.getEncoder().encode(ImgService.decompressImage(profilUser.getImage())), StandardCharsets.UTF_8));
            }
        }
        session.setAttribute("profilUser", profilUser);


        return "profil";
    }

    @GetMapping("modifProfil/{id}")
    public String GetModifProfil (@PathVariable Long id, HttpServletRequest request, RedirectAttributes rAttribute) {
        User user = (User) request.getSession().getAttribute("user");
        if(user.getId() != id) {
            rAttribute.addAttribute("ImpossibleModif", true);
            return "redirect:/";
        }
        return "modifProfil";
    }

    @PostMapping ("/modifProfil/{id}")
    public String PostModifProfil(@PathVariable Long id, @RequestParam(name ="image") MultipartFile file, @RequestParam Map<String,String> allParams) throws IOException {
        User user = userRepository.getUsersById(id);
        user.setEmail(allParams.get("email"));
        user.setNom(allParams.get("lastname"));
        user.setPrenom(allParams.get("firstname"));
        user.setUsername(allParams.get("username"));
        user.setImage(ImgService.compressImage(file.getBytes()));
        userRepository.save(user);
        return "redirect:/profil/"+user.getId();
    }

    @GetMapping("/deconnexion")
    public String GetDeconnexion (HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
}
