package infotech.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;

@Controller
public class LoadController {

    @Autowired
    private ObjectRepository objectRepository;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private String answerForHTML = "";
    private String errorNum = "";
    private String errorRow = "";
    private String currentJsonLine;
    private int currentNumOfLine;

    @GetMapping("/load")
    public String showLoadPage() {
        return "load";
    }

    @PostMapping("/load")
    public String loadStateFromFile(@RequestParam MultipartFile inputFile, Model model) {
        File fileFromHTML = new File("src/main/resources/fileWithState.txt");
        try (OutputStream os = new FileOutputStream(fileFromHTML)) {
            os.write(inputFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(fileFromHTML))) {
            LinkedList<DataBaseObject> newObjects = new LinkedList<>();
            currentJsonLine = br.readLine();
            while (currentJsonLine != null) {
                currentNumOfLine += 1;
                DataBaseObject objectFromFile = OBJECT_MAPPER.readValue(currentJsonLine, DataBaseObject.class);
                if (objectFromFile.getKey() == null || objectFromFile.getAttributes() == null || objectFromFile.getTtl() == null || objectFromFile.getCreationDateTime() == null || objectFromFile.getDeleteDateTime() == null) {
                    answerForHTML = "Проверьте, что в файле заполнены все обязательные поля.";
                    formErrorMsg(currentNumOfLine, currentJsonLine);
                    break;
                } else newObjects.add(objectFromFile);
                currentJsonLine = br.readLine();
            }
            if (answerForHTML.isEmpty()) {
                objectRepository.deleteAll();
                objectRepository.saveAll(newObjects);
                answerForHTML = "Хранилище успешно заполнено данными из файла";
            }
        } catch (JacksonException e) {
            formErrorMsg(currentNumOfLine, currentJsonLine);
            answerForHTML = "В файле найдена ошибка. Проверьте формат файла (все строки должны быть в формате json), дат и времени. Обновите файл и запустите команду снова";
            e.printStackTrace();
        } catch (Exception e) {
            answerForHTML = ("Непредвиденная ошибка при чтении файла. Замените файл и запустите команду снова.");
            e.printStackTrace();
        }
        model.addAttribute("answer", answerForHTML);
        model.addAttribute("comment1", errorNum);
        model.addAttribute("comment2", errorRow);
        return "load";
    }

    private void formErrorMsg(int num, String jsonLine) {
        errorNum = "Ошибка в следующей строке => " + num;
        errorRow = "Неправильная строка => " + jsonLine;
    }
}
