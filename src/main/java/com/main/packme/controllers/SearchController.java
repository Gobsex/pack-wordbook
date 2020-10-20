package com.main.packme.controllers;

import com.main.packme.dao.entity.Pack;
import com.main.packme.services.PackService;
import com.main.packme.services.Translate;
import com.main.packme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    UserService userService;
    @Autowired
    PackService packService;
    @Autowired
    Translate translate;
    @GetMapping("/search")
    public String getAllPublicPacks(Authentication auth,Model model){
        List<Pack> packs = userService.loadPack(auth.getName(), packService.findAllPublicPacks());

        model.addAttribute("packs", packs);
        return "search";
    }
}
