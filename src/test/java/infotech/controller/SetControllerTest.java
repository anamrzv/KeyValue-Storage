package infotech.controller;

import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SetController.class)
public class SetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenAllParamsAreCorrect_thenObjectShouldBeAdded() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/set")
                .param("key","TEST")
                .param("attributes","test")
                .param("ttl","00:10"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("answer", "comment"))
                .andExpect(model().attribute("answer","Запись успешно добавлена:"))
                .andExpect(status().isOk());
    }
}
