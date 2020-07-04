package com.main.packme.controllers;

import com.main.packme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {
    @Autowired
    UserService userService;
    @GetMapping("/account")
    public String account(Authentication auth, Model model){
        String name = auth.getName();

        User user = (User) userService.loadUserByUsername(name);
        model.addAttribute("user",user);
        return "account";
    }
}
