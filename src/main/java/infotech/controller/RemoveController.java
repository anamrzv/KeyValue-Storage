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
public class RemoveController {

    @Autowired
    private ObjectRepository objectRepository;

    @GetMapping("/remove")
    public String showRemovePage() {
        return "remove";
    }

    @PostMapping("/remove")
    public String removeObject(@RequestParam String key, Model model) {
        String answerForHTML;
        String commentForHTML = "";
        if (!objectRepository.existsByKey(key)) answerForHTML = "Записи с таким ключом не существует.";
        else {
            DataBaseObject foundObject = objectRepository.findByKey(key);
            objectRepository.delete(foundObject);
            answerForHTML = "Запись по ключу " + key + " успешно удалена. Информация об удаленной записи:";
            commentForHTML = "Ключ: " + key + " Данные: " + foundObject.getAttributes() + " Запись должна была удалиться: " + foundObject.getDeleteDateTimeAsString();
        }
        model.addAttribute("answer", answerForHTML);
        model.addAttribute("comment", commentForHTML);
        return "remove";
    }
}
