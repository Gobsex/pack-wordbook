package com.main.packme.controllers;

import com.main.packme.dao.entity.Pack;
import com.main.packme.dao.components.Word;
import com.main.packme.dao.components.WordList;
import com.main.packme.services.PackService;
import com.main.packme.services.Translate;
import com.main.packme.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    public String packForm(Authentication auth,Model model) {
        model.addAttribute("packEntity", new Pack());
        long id = userService.addPack(auth.getName());
        return "redirect:/pack/" + id+"/edit";
    }


    @GetMapping("/all")
    public String selectFavorite(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packList = userService.findFavoritePacks(username);
        List<Pack> packs = userService.loadPack(username, packList);
        model.addAttribute("isAll", true);
        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/public")
    public String selectPublic(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packList = userService.findAllPublicPacks(username);
        List<Pack> packs = userService.loadPack(username, packList);

        model.addAttribute("isPublic", true);

        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/private")
    public String selectPrivate(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packList = userService.findAllPrivatePacks(username);
        List<Pack> packs = userService.loadPack(username, packList);

        model.addAttribute("isPrivate", true);

        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/{id}")
    public String select(@PathVariable(value = "id") long id,
                         Authentication auth,
                         @RequestHeader(value = "referer")String referer,
                         ModelMap model) {
        if(userService.isFavorite(auth.getName(),id)){
            model.addAttribute("favorite", true);
        }
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("isEditable", true);
            model.addAttribute("pack", pack);
            return "view-pack";
        } else if (userService.isSelectable(auth.getName(),id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "view-pack";
        }
        else return "redirect:/pack/all";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") long id,
                       Authentication auth,
                       ModelMap model) {
        if(userService.isFavorite(auth.getName(),id)){
            model.addAttribute("favorite", true);
        }
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("isEditable", true);

            model.addAttribute("pack", pack);
            return "edit-general";
        } else return "redirect:/pack/all";
    }











    @PostMapping(value = "/{id}/edit", params = "save_general")
    public String save(@PathVariable(value = "id") long id,
                       @Param(value = "name") String name,
                       @Param(value = "description") String description,
                       @Param(value = "type") String type,
                       @Param(value = "firstLn") String firstLn,
                       @Param(value = "secondLn") String secondLn,
                       Authentication auth,
                       ModelMap model) {

        if (userService.isEditable(auth.getName(), id)) {
            model.addAttribute("isEditable", true);
            packService.saveGeneral(id, name, description, type,firstLn,secondLn);
        }
        return "redirect:/pack/" + id;
    }



    @PostMapping(value = "/{id}", params = "add")
    public String addWordInView(
            @PathVariable(value = "id") long id,
            @RequestParam String key,
            @RequestParam String value,
            Model model) {
        Pack pack = packService.findById(id);
        WordList wordList = pack.getWordList();
        if (wordList == null) {
            wordList = new WordList();
        }
        if(key!=""&&value!="") {
            wordList.add(new Word(key.trim(), value.trim()));
            pack.setWordList(wordList);
            packService.savePack(pack);
        }
        return "redirect:/pack/" + pack.getId();
    }


    @PostMapping(value = "/{id}", params = "translate")
    public String translateWordInView(
            @PathVariable(value = "id") long id,
            @RequestParam String key,
            @RequestParam String value,
            Authentication auth,
            Model model) {
        Pack pack = packService.findById(id);
        if (key != "" || value != "") {
            if (key == "" || key == null) {
                String translation = translate.getTranslation(value, pack.getSecond_ln(), pack.getFirst_ln());
                model.addAttribute("value", value);
                model.addAttribute("key", translation);
            }
            if (value == "" || value == null) {
                String translation = translate.getTranslation(key, pack.getFirst_ln(), pack.getSecond_ln());
                model.addAttribute("key", key);
                model.addAttribute("value", translation);
            }
        }

        if (key != "" && value != "") {
            model.addAttribute("key", key);
            model.addAttribute("value", value);
        }
        boolean favorite = userService.isFavorite(auth.getName(), id);
        model.addAttribute("favorite",favorite);
        model.addAttribute("isEditable", true);

        model.addAttribute("pack", pack);
        return "view-pack";
    }

    @GetMapping("/{id}/delete")
    public String deletePack(@PathVariable(value = "id") long id,
                             Authentication auth,
                             @RequestHeader(value = "referer", required = false)String referer,

                             ModelMap model){
        if (userService.isEditable(auth.getName(), id)) {
            userService.removePack(auth.getName(),id);
        }
        String redirect;
        if(referer=="http://localhost:8080/pack/"+id){
            redirect = "/pack/all";
        }
        else {
            redirect = referer;
        }
        System.out.println(redirect);
        return "redirect:"+redirect;
    }
    @GetMapping("/{id}/remove/{wordId}")
    public String removeWord(@PathVariable(value = "id") long id,
                             @PathVariable(value = "wordId") long wordId,
                         Authentication auth,
                         ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            pack.removeWord(wordId);
            packService.savePack(pack);
        }
        return "redirect:/pack/" + id;
    }

    @GetMapping("/{id}/toFavorites")
    public String toFavorites(@PathVariable(value = "id") long id,
                             @RequestHeader(value = "referer", required = false)String referer,
                             Authentication auth,
                             ModelMap model){
        userService.addToFavorites(auth.getName(),id);
        return "redirect:"+referer;
    }
    @GetMapping("/{id}/fromFavorites")
    public String fromFavorites(@PathVariable(value = "id") long id,
                                @RequestHeader(value = "referer", required = false)String referer,
                              Authentication auth,
                              ModelMap model){
        userService.removeFromFavorites(auth.getName(),id);

        return "redirect:"+referer;
    }


    @GetMapping("/{id}/train")
    public String train(@PathVariable(value = "id") long id,
                      Authentication auth,
                      ModelMap model){
        if (userService.isSelectable(auth.getName(), id)){
            model.addAttribute("pack",packService.findById(id));
        }
        return "train";
    }



}
