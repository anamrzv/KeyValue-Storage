package infotech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String showSetPage(Model model) {
        return "/main";
    }
}
