package com.main.packme.controllers;

import com.main.packme.dao.entity.Pack;
import com.main.packme.dao.components.Word;
import com.main.packme.dao.components.WordList;
import com.main.packme.services.PackService;
import com.main.packme.services.Translate;
import com.main.packme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/pack")
@Controller
public class PackController {
    @Autowired
    UserService userService;
    @Autowired
    PackService packService;
    @Autowired
    Translate translate;
    @GetMapping("/add")
    public String packForm(Model model){
        model.addAttribute("packEntity",new Pack());
        return "add";
    }
    @PostMapping(value = "/add",params = "add_pack")
    public String addPack(@Valid @ModelAttribute("packEntity") Pack pack, BindingResult bindingResult, Authentication auth, Model model){
        if(bindingResult.hasErrors()){
            return "add";
        }
        pack.setWordList(new WordList());
        userService.addPack(auth.getName(),pack);
        System.out.println(pack);
        return "redirect:/pack/"+ pack.getId()+"/edit";
    }
    @GetMapping("/all")
    public String selectAll(Authentication auth, ModelMap model){
        String username = auth.getName();
        List<Pack> packs = userService.findAllPacks(username);
        model.addAttribute("isAll", true);

        model.addAttribute("packs", packs);
        return "packs";
    }
    @GetMapping("/public")
    public String selectPublic(Authentication auth, ModelMap model){
        String username = auth.getName();
        List<Pack> packs = userService.findAllPublicPacks(username);
        model.addAttribute("isPublic", true);

        model.addAttribute("packs", packs);
        return "packs";
    }
    @GetMapping("/private")
    public String selectPrivate(Authentication auth, ModelMap model){
        String username = auth.getName();
        List<Pack> packs = userService.findAllPrivatePacks(username);
        model.addAttribute("isPrivate", true);

        model.addAttribute("packs", packs);
        return "packs";
    }
    @GetMapping("/{id}")
    public String select(@PathVariable(value = "id") long id,Authentication auth, ModelMap model){
        if(userService.isEditable(auth.getName(),id)){
            Pack pack = packService.findById(id);
            model.addAttribute("isEditable",true);
            model.addAttribute("pack", pack);
            return "view-pack";
        }
        else if(userService.isSelectable(id)){
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "view-pack";
        }
        else return "pack-not-found";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") long id,
                       Authentication auth,
                       ModelMap model){
        if(userService.isEditable(auth.getName(),id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "edit";
        }
        else return "pack-not-found";
    }
    @PostMapping(value = "/{id}/edit", params = "translate")
    public String translate(@PathVariable(value = "id") long id,
                       @RequestParam String key,
                       @RequestParam String value,
                       ModelMap model){
        Pack pack = packService.findById(id);
        if(key!=""||value!="") {
            if (key == "" || key == null) {
                String translation = translate.getTranslation(value, pack.getSecond_ln(), pack.getFirst_ln());
                System.out.println(translation);
                model.addAttribute("value", value);
                model.addAttribute("key", translation);
            }
            if (value == "" || value == null) {
                String translation = translate.getTranslation(key, pack.getFirst_ln(), pack.getSecond_ln());
                System.out.println(translation);
                model.addAttribute("key", key);
                model.addAttribute("value", translation);
            }
        }
        model.addAttribute("pack",pack);
        return "edit";
    }


    @PostMapping(value = "/{id}/edit",params = "add_word")
    public String addWord(
            @PathVariable(value = "id")long id,
            @RequestParam String key,
            @RequestParam String value,
            Model model){
        Pack pack = packService.findById(id);
        WordList wordList = pack.getWordList();
        if(wordList==null){
            wordList = new WordList();
        }
        wordList.add(new Word(key,value));
        pack.setWordList(wordList);
        packService.savePack(pack);
        return "redirect:/pack/"+ pack.getId()+"/edit";
    }
//    @PostMapping(value = "/{id}/edit",params = "translate")
//    public String translateWord(
//            @PathVariable(value = "id")long id,
//            @RequestParam String key,
//            @RequestParam String value,
//            @ModelAttribute("pack") PackEntity packEntity,
//            Model model){
//        if(key==""||key==null){
//            String translation = translate.getTranslation(value, packEntity.getSecond_ln(), packEntity.getFirst_ln());
//            System.out.println(translation);
//
//            model.addAttribute("value",value);
//            model.addAttribute("key",translation);
//
//        }
//        if(value==""||value==null){
//            String translation = translate.getTranslation(key, packEntity.getFirst_ln(), packEntity.getSecond_ln());
//            System.out.println(translation);
//            model.addAttribute("keyPost",key);
//            model.addAttribute("valuePost",translation);
//        }
//        return "redirect:/packs/"+id+"/edit";
//    }
}
