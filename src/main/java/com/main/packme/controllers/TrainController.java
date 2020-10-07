//package com.main.packme.controllers;
//
//import com.main.packme.dao.entity.Pack;
//import com.main.packme.services.PackService;
//import com.main.packme.services.Translate;
//import com.main.packme.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/pack")
//
//public class TrainController {
//    @Autowired
//    UserService userService;
//    @Autowired
//    PackService packService;
//    @Autowired
//    Translate translate;
//    @GetMapping("/{id}/train")
//    public Pack train(@PathVariable(value = "id") long id,
//                      Authentication auth,
//                      ModelMap model){
//        if (userService.isSelectable(auth.getName(), id)){
//            System.out.println(packService.findById(id));
//            return packService.findById(id);
//        }
//        else return null;
//    }
//}
