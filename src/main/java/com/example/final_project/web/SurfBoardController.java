package com.example.final_project.web;

import com.example.final_project.entities.Likes;
import com.example.final_project.entities.SurfBoards;
import com.example.final_project.entities.User;
import com.example.final_project.repositories.LikesRepository;
import com.example.final_project.repositories.SurfBoardRepository;
import com.example.final_project.repositories.UserRepository;
import com.example.final_project.service.ImgService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller @AllArgsConstructor
public class SurfBoardController {

    private final SurfBoardRepository surfBoardRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final ImgService imgService;

    @PostMapping("/newSurfBoard")
    public String postAjouterSurfBoard(@RequestParam Map<String,String> allParams,@RequestParam(name ="img") MultipartFile file, HttpServletRequest request, RedirectAttributes rAttribute) throws IOException {
        if (/*userService.allRegisterParams(allParams)*/ true) {
            User user = (User) request.getSession().getAttribute("user");
            SurfBoards sb = new SurfBoards(
                    null, allParams.get("nom"),
                    allParams.get("dess"), ImgService.compressImage(file.getBytes()),
                    user.getId(), user.getUsername(),null
            );
            surfBoardRepository.save(sb);
            return "redirect:/";
        } else {
            rAttribute.addAttribute("invalidParam",true);
            return "redirect:/newSurfBoard";
        }
    }

    @GetMapping("/newSurfBoard")
    public String getAjouterSurfBoard(HttpServletRequest request, RedirectAttributes rAttribute) {
        if (request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/index/0";
        }
        return "newSurfBoard";
    }
    @PostMapping("/modifSurfBoard/{id}")
    public String postAjouterSurfBoard(@RequestParam Map<String,String> allParams,@RequestParam(name ="img") MultipartFile file, @PathVariable Long id) throws IOException {
        SurfBoards sb = surfBoardRepository.getById(id);
        sb.setNom(allParams.get("nom"));
        sb.setContent(allParams.get("dess"));
        sb.setImage(ImgService.compressImage(file.getBytes()));
        surfBoardRepository.save(sb);
        return "redirect:/";
    }

    @GetMapping("/profil/{userId}/modifSurfBoard/{sbId}")
    public String getModifSurfBoard(HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable(value = "sbId") Long id) {
        SurfBoards sb = surfBoardRepository.getById(id);
        request.getSession().setAttribute("surfBoard",sb);
        User user = (User) request.getSession().getAttribute("user");
        if (request.getSession().getAttribute("user") == null || user.getId() != sb.getUserId()) {
            rAttribute.addAttribute("modifImpossible", true);
            if (request.getSession().getAttribute("user") == null ) {
                rAttribute.addAttribute("connected", false);
                return "redirect:/index/0";
            } else {
                return "redirect:/index/"+user.getId();
            }
        }
        return "modifSurfBoard";
    }

    @GetMapping("/profil/{userId}/deleteSurfBoard/{sbId}")
    public String DeleteInProfilSurfBoard(@PathVariable(value = "userId") Long userId,@PathVariable(value = "sbId") Long sbId, HttpServletRequest request, RedirectAttributes rAttribute){
        User user = (User) request.getSession().getAttribute("user");
        SurfBoards sb = surfBoardRepository.getById(sbId);
        if(!user.getId().equals(sb.getUserId())) {
            rAttribute.addAttribute("ImpossibleSupp", true);
            return "redirect:/profil/"+user.getId();
        }
        likesRepository.deleteAllBySurfboard(sbId);
        surfBoardRepository.deleteById(sbId);
        return "redirect:/profil/"+user.getId();
    }

    @GetMapping("/index/{userId}/deleteSurfBoard/{sbId}")
    public String DeleteInIndexSurfBoard(@PathVariable(value = "userId") Long userId,@PathVariable(value = "sbId") Long sbId, HttpServletRequest request, RedirectAttributes rAttribute){
        User user = (User) request.getSession().getAttribute("user");
        SurfBoards sb = surfBoardRepository.getById(sbId);
        if(!user.getId().equals(sb.getUserId())) {
            rAttribute.addAttribute("ImpossibleSupp", true);
            return "redirect:/index/"+user.getId();
        }
        likesRepository.deleteAllBySurfboard(sbId);
        surfBoardRepository.deleteById(sbId);
        return "redirect:/index/"+user.getId();
    }

    @GetMapping ("/")
    public String GetHome (HttpServletRequest request, RedirectAttributes rAttribute) {
        if( request.getSession().getAttribute("user") == null) {
            rAttribute.addAttribute("Connected", false);
            return "redirect:/index/"+0;
        } else {
            rAttribute.addAttribute("Connected", true);
            User user = (User) request.getSession().getAttribute("user");
            return "redirect:/index/"+user.getId();
        }
    }

    @GetMapping(value = "/index/{id}")
    public String Home (HttpServletRequest request, RedirectAttributes rAttribute, @PathVariable Long id) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<SurfBoards> listSb = ImgService.stringImgEncoded(surfBoardRepository.findAll());
        List<Likes> listLikes = new ArrayList<Likes>();
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < listSb.size() ; i++) {
            userList.add(userRepository.getUsersById(listSb.get(i).getUserId()));
            if (id != 0) {
                if (likesRepository.existsByClientAndSurfboard(user.getId(),listSb.get(i).getId())) {
                    listLikes.add(likesRepository.getByClientAndSurfboard(user.getId(),listSb.get(i).getId()));
                } else {
                    listLikes.add(new Likes(null,null,null));
                }
            } else {
                listLikes.add(new Likes(null,null,null));
            }
        }
        session.setAttribute("likedList",listLikes);
        session.setAttribute("userList", ImgService.stringAvatarEncoded(userList));
        session.setAttribute("surfBoard",  listSb);
        return "index";
    }
}
