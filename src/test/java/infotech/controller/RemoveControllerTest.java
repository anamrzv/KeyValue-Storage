package infotech.controller;

import infotech.domain.DataBaseObject;
import infotech.repo.ObjectRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RemoveController.class)
public class RemoveControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ObjectRepository repository;

    @Before
    public void setUp() {
        DataBaseObject testObject = new DataBaseObject.Builder()
                .withKey("TEST")
                .withAttributes("test")
                .withTTL("00:10")
                .withCreationTime(LocalDateTime.now())
                .withDeleteTime()
                .build();

        Mockito.when(repository.findByKey(testObject.getKey()))
                .thenReturn(testObject);
        Mockito.when(repository.existsByKey(testObject.getKey())).thenReturn(true);
    }

    @Test
    public void whenDeletingAnItemItShouldUseTheRepository() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/remove")
                .param("key", "TEST"))
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("answer", "comment"))
                .andExpect(model().attribute("answer", "Запись по ключу TEST успешно удалена. Информация об удаленной записи:"))
                .andExpect(status().isOk());

        verify(repository).delete(repository.findByKey("TEST"));
    }

}
