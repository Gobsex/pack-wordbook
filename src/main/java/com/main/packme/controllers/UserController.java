package com.main.packme.controllers;

import com.main.packme.dao.entity.User;
import com.main.packme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login(@RequestParam(name = "error",   required = false) String error, Model model) {
        try {
            if(error.equals("")&&error!=null){
                model.addAttribute("error", true);
            }
        }
        catch (NullPointerException e){

        }


        return "login";
    }



    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        userForm.setUsername(userForm.getUsername().trim());
        if (bindingResult.hasErrors()) {


            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", true);
            return "registration";

        }
        if(userForm.getUsername().equals("")||userForm.getPassword().trim().equals("")){
            model.addAttribute("emptyError",true);
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", true);
            return "registration";

        }



        return "redirect:/login";
    }
}
