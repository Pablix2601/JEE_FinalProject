package com.example.final_project.service;

import com.example.final_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service @AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Boolean allRegisterParams(Map<String,String> allParams) {
        for (var entry : allParams.entrySet()) {
            switch (entry.getKey()) {
                case "username" :
                case "password" :
                    if (entry.getValue() == null || entry.getValue().equals("")) {
                        return false;
                    }
                case "register" :
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

}
