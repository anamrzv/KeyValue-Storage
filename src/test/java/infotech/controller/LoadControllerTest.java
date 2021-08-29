package infotech.controller;

import infotech.repo.ObjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LoadController.class)
public class LoadControllerTest {

    @MockBean
    private ObjectRepository repository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void ifValidFile_shouldLoadFileAndFillDatabaseFromIt() throws Exception {
        MockMultipartFile inputFile = new MockMultipartFile("inputFile", "testdata.txt", MediaType.APPLICATION_OCTET_STREAM_VALUE, "{\"key\":\"TEST\",\"attributes\":\"test\",\"ttl\":\"00:10:00\",\"creationDateTime\":\"2021-08-29T14:50:32.707\",\"deleteDateTime\":\"2021-08-29T15:00:32.707\"}".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/load")
                .file(inputFile))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("answer", "comment1", "comment2"))
                .andExpect(model().attribute("answer", "Хранилище успешно заполнено данными из файла"))
                .andExpect(status().isOk());
    }

    @Test
    public void ifNotValidFile_shouldTellAboutIt() throws Exception {
        MockMultipartFile inputFile = new MockMultipartFile("inputFile", "testdata.txt", MediaType.APPLICATION_OCTET_STREAM_VALUE, "Hello".getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/load")
                .file(inputFile))
                .andExpect(model().size(3))
                .andExpect(model().attributeExists("answer", "comment1", "comment2"))
                .andExpect(model().attribute("answer", "В файле найдена ошибка. Проверьте формат файла (все строки должны быть в формате json), дат и времени. Обновите файл и запустите команду снова"))
                .andExpect(status().isOk());
    }
}
