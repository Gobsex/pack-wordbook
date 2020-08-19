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
    public String packForm(Model model) {
        model.addAttribute("packEntity", new Pack());
        return "add";
    }

    @PostMapping(value = "/add", params = "add_pack")
    public String addPack(@Valid @ModelAttribute("packEntity") Pack pack, BindingResult bindingResult, Authentication auth, Model model) {
        if (bindingResult.hasErrors()) {
            return "add";
        }
        pack.setWordList(new WordList());
        userService.addPack(auth.getName(), pack);
        System.out.println(pack);
        return "redirect:/pack/" + pack.getId() + "edit-general";
    }

    @GetMapping("/all")
    public String selectAll(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packs = userService.findAllPacks(username);
        model.addAttribute("isAll", true);

        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/public")
    public String selectPublic(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packs = userService.findAllPublicPacks(username);
        model.addAttribute("isPublic", true);

        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/private")
    public String selectPrivate(Authentication auth, ModelMap model) {
        String username = auth.getName();
        List<Pack> packs = userService.findAllPrivatePacks(username);
        model.addAttribute("isPrivate", true);

        model.addAttribute("packs", packs);
        return "packs";
    }

    @GetMapping("/{id}")
    public String select(@PathVariable(value = "id") long id, Authentication auth, ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("isEditable", true);
            model.addAttribute("pack", pack);
            return "view-pack";
        } else if (userService.isSelectable(id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "view-pack";
        } else return "pack-not-found";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(value = "id") long id,
                       Authentication auth,
                       ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "edit-general";
        } else return "pack-not-found";
    }




    @GetMapping("/{id}/edit-ln")
    public String editLn(@PathVariable(value = "id") long id,
                       Authentication auth,
                       ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            Pack pack = packService.findById(id);
            model.addAttribute("pack", pack);
            return "edit-ln";
        } else return "pack-not-found";
    }



    @PostMapping(value = "/{id}/edit-ln", params = "save_ln")
    public String saveLn(@PathVariable(value = "id") long id,
                       @Param(value = "firstLn") String firstLn,
                       @Param(value = "secondLn") String secondLn,
                       Authentication auth,
                       ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            packService.saveLn(id,firstLn,secondLn);
        }
        return "redirect:/pack/" + id;
    }


    @PostMapping(value = "/{id}/edit", params = "save_general")
    public String save(@PathVariable(value = "id") long id,
                       @Param(value = "name") String name,
                       @Param(value = "description") String description,
                       @Param(value = "type") String type,
                       Authentication auth,
                       ModelMap model) {
        System.out.println(name);
        System.out.println(description);
        if (userService.isEditable(auth.getName(), id)) {
            packService.saveGeneral(id, name, description, type);
        }
        return "redirect:/pack/" + id;
    }


    //    @PostMapping(value = "/{id}/edit",params = "add")
//    public String addWord(
//            @PathVariable(value = "id")long id,
//            @RequestParam String key,
//            @RequestParam String value,
//            Model model){
//        Pack pack = packService.findById(id);
//        WordList wordList = pack.getWordList();
//        if(wordList==null){
//            wordList = new WordList();
//        }
//        wordList.add(new Word(key,value));
//        pack.setWordList(wordList);
//        packService.savePack(pack);
//        return "redirect:/pack/"+ pack.getId()+ "/edit";
//    }
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
        wordList.add(new Word(key, value));
        pack.setWordList(wordList);
        packService.savePack(pack);
        return "redirect:/pack/" + pack.getId();
    }

    //    @PostMapping(value = "/{id}/edit",params = "translate")
//    public String translateWord(
//            @PathVariable(value = "id")long id,
//            @RequestParam String key,
//            @RequestParam String value,
//            Model model){
//        Pack pack = packService.findById(id);
//        if(key!=""||value!="") {
//            if (key == "" || key == null) {
//                String translation = translate.getTranslation(value, pack.getSecond_ln(), pack.getFirst_ln());
//                System.out.println(translation);
//                model.addAttribute("value", value);
//                model.addAttribute("key", translation);
//            }
//            if (value == "" || value == null) {
//                String translation = translate.getTranslation(key, pack.getFirst_ln(), pack.getSecond_ln());
//                System.out.println(translation);
//                model.addAttribute("key", key);
//                model.addAttribute("value", translation);
//            }
//        }
//        if(key!=""&&value!=""){
//            model.addAttribute("key", key);
//            model.addAttribute("value", value);
//        }
//        model.addAttribute("pack",pack);
//        return "edit-general";
//    }
    @PostMapping(value = "/{id}", params = "translate")
    public String translateWordInView(
            @PathVariable(value = "id") long id,
            @RequestParam String key,
            @RequestParam String value,
            Model model) {
        Pack pack = packService.findById(id);
        if (key != "" || value != "") {
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
        if (key != "" && value != "") {
            model.addAttribute("key", key);
            model.addAttribute("value", value);
        }
        model.addAttribute("pack", pack);
        return "view-pack";
    }


    @GetMapping("/{id}/delete/{wordId}")
    public String removeWord(@PathVariable(value = "id") long id,
                             @PathVariable(value = "wordId") long wordId,
                         Authentication auth,
                         ModelMap model) {
        if (userService.isEditable(auth.getName(), id)) {
            System.out.println(wordId);
            Pack pack = packService.findById(id);
            pack.removeWord(wordId);
            packService.savePack(pack);
        }
        return "redirect:/pack/" + id;
    }


}
