package infotech.controller;

import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetController {

    @Autowired
    private ObjectRepository objectRepository;

    @GetMapping("/get")
    public String showGetPage(Model model) {
        return "get";
    }

    @PostMapping("/get")
    public String getValue(@RequestParam String key, Model model) {
        String answer;
        DataBaseObject dataBaseObject = objectRepository.findByKey(key);
        if (dataBaseObject==null) answer = "Запись с таким ключом не найдена";
        else {
            answer = dataBaseObject.getAttributes();
            if (answer == null) answer = "По этому ключу находится пустая запись";
        }
        model.addAttribute("attributes", answer);
        return "get";
    }
}
