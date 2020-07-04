package com.main.packme.controllers;

import com.main.packme.dao.entity.Pack;
import com.main.packme.services.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    PackService packService;
    @GetMapping("/search")
    public String getAllPublicPacks(Model model){
        List<Pack> allPublicPacks = packService.findAllPublicPacks();
        model.addAttribute("publicPacks", allPublicPacks);
        System.out.println(allPublicPacks);
        return "search";
    }
}
