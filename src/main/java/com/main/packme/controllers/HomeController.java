package com.main.packme.controllers;

import com.main.packme.dao.entity.Pack;
import com.main.packme.repository.PacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    PacksRepository packsRepository;
    @GetMapping("/")
    public String home( Model model) {
        model.addAttribute("isHome",true);
        return "home";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

}
