package com.example.final_project.web;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.FollowsRepository;
import com.example.final_project.repositories.LikesRepository;
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
    private final LikesRepository likesRepository;
    private final FollowsRepository followsRepository;
    private final ImgService imgService;

    @GetMapping ("/register")
    public String getRegister (HttpServletRequest request) {
        if (request.getSession().getAttribute("user")!=null) {
            User user = (User) request.getSession().getAttribute("user");
            return "redirect:/index/"+user.getId();
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
            User user = (User) request.getSession().getAttribute("user");
            return "redirect:/index/"+user.getId();
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
        User user = (User) session.getAttribute("user");
        List<Likes> listlikedYoursb = new ArrayList<Likes>();
        List<SurfBoards> listYourSb = ImgService.stringImgEncoded(surfBoardRepository.findAllByUserId(id));
        for (int i = 0; i < listYourSb.size() ; i++) {
            if (session.getAttribute("user") != null) {
                if (likesRepository.existsByClientAndSurfboard(user.getId(),listYourSb.get(i).getId())) {
                    listlikedYoursb.add(likesRepository.getByClientAndSurfboard(user.getId(),listYourSb.get(i).getId()));
                } else {
                    listlikedYoursb.add(new Likes(null,null,null));
                }
            } else {
                listlikedYoursb.add(new Likes(null,null,null));
            }
        }
        session.setAttribute("likedYourSbList",  listlikedYoursb);
        if (listYourSb == null) {
            session.setAttribute("noYourSurfBoard",  false);
        } else {
            session.setAttribute("noYourSurfBoard",  true);
            session.setAttribute("yourSurfBoard", listYourSb);
        }

        List<SurfBoards> listLikedSb = ImgService.stringImgEncoded(surfBoardRepository.findAllLikedSurfBoardByUserId(id));
        List<Likes> listLikedsb = new ArrayList<Likes>();
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < listLikedSb.size() ; i++) {
            userList.add(userRepository.getUsersById(listLikedSb.get(i).getUserId()));
            if (id != 0) {
                if (likesRepository.existsByClientAndSurfboard(user.getId(),listLikedSb.get(i).getId())) {
                    listLikedsb.add(likesRepository.getByClientAndSurfboard(user.getId(),listLikedSb.get(i).getId()));
                } else {
                    listLikedsb.add(new Likes(null,null,null));
                }
            } else {
                listLikedsb.add(new Likes(null,null,null));
            }
        }
        session.setAttribute("userList", ImgService.stringAvatarEncoded(userList));
        session.setAttribute("listLikedSbList",  listLikedsb);
        if ( listLikedSb == null) {
            session.setAttribute("noLikedSurfBoard",  false);
        } else {
            session.setAttribute("noLikedSurfBoard",  true);
            session.setAttribute("likedSurfBoard",  listLikedSb);
        }
        User profilUser = userRepository.getUsersById(id);

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
        if (session.getAttribute("user") != null) {
            session.setAttribute("followed", followsRepository.existsByIdUserAndIdUserFollowed(user.getId(), id));
            session.setAttribute("followedList", followsRepository.findAllFollowedUserByUserId(id));
        }
        return "profil";
    }

    @GetMapping("modifProfil/{id}")
    public String GetModifProfil (@PathVariable Long id, HttpServletRequest request, RedirectAttributes rAttribute) {
        User user = (User) request.getSession().getAttribute("user");
        if(user.getId() != id) {
            rAttribute.addAttribute("ImpossibleModif", true);
            return "redirect:/index/"+user.getId();
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
