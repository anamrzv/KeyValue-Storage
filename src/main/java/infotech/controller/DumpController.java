package infotech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

@Controller
public class DumpController {

    @Autowired
    private ObjectRepository objectRepository;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @GetMapping(value = "/dump")
    public String showSetPage(Model model) {
        model.addAttribute("time", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        return "/dump";
    }

    @GetMapping(value = "/state-{time}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] returnFile(@PathVariable(value = "time") String date) throws IOException {
        fillFileWithCurrentData();
        File file = new File("src/main/resources/data.txt");
        FileInputStream in = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        in.read(arr);
        in.close();
        return arr;
    }

    private void fillFileWithCurrentData() {
        try {
            File toReturnFile = new File("src/main/resources/data.txt");
            PrintWriter writer = new PrintWriter(toReturnFile);

            LinkedList<DataBaseObject> objects = objectRepository.getListOfObjects();
            for (DataBaseObject object : objects) {
                writer.write(OBJECT_MAPPER.writeValueAsString(object) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
