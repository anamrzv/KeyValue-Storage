package infotech.controller;

import com.sun.xml.bind.v2.TODO;
import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class SetController {

    @Autowired
    ObjectRepository objectRepository;

    @GetMapping("/set")
    public String showSetPage(){
        return "/set";
    }

    @PostMapping("/set")
    public String setNewObject(@RequestParam String key, @RequestParam String attributes, @RequestParam String ttl, Model model){
        String answerForHTML;
        String commentForHTML;
        DataBaseObject newObject;
        DataBaseObject.Builder builder = new DataBaseObject.Builder()
                .withKey(key)
                .withAttributes(attributes)
                .withTTL(ttl);
        if (!objectRepository.existsByKey(key)) {
             newObject = builder
                    .withCreationTime(LocalDateTime.now())
                    .withDeleteTime()
                    .build();
             answerForHTML = "Запись успешно добавлена:";
             commentForHTML = "Ключ: "+key+" Данные: "+attributes+" Запись удалится: "+newObject.getDeleteDateTimeAsString();
        } else {
            newObject = builder
                    .withCreationTime(objectRepository.getTimestampByKey(key).toLocalDateTime())
                    .withDeleteTime()
                    .build();
            answerForHTML = "Запись по ключу "+key+" успешно обновлена. Обновленная запись:";
            commentForHTML = "Ключ: "+key+" Данные: "+attributes+" Запись удалится: "+newObject.getDeleteDateTimeAsString();
        }
        objectRepository.save(newObject);
        if (!objectRepository.existsByKey(key)) answerForHTML = "Ошибка. Запись не добавлена.";
        model.addAttribute("answer", answerForHTML);
        model.addAttribute("comment", commentForHTML);
        return "set";
    }
}
