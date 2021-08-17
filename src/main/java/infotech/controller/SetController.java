package infotech.controller;

import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SetController {

    @Autowired
    ObjectRepository objectRepository;

    @GetMapping("/set")
    public String showSetPage(Model model){
        return "/set";
    }

    @PostMapping("/set")
    public String setNewObject(@RequestParam String key, @RequestParam String attributes, @RequestParam Integer ttl, Model model){

        return "/set";
    }
}
