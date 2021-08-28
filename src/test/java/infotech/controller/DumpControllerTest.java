package infotech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DumpController.class)
public class DumpControllerTest {

    @MockBean
    private ObjectRepository repository;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setUp() {
        DataBaseObject testObject = new DataBaseObject.Builder()
                .withKey("TEST")
                .withAttributes("test")
                .withTTL("00:10")
                .withCreationTime(LocalDateTime.now())
                .withDeleteTime()
                .build();

        LinkedList<DataBaseObject> list = new LinkedList<> ();
        list.add(testObject);

        Mockito.when(repository.getListOfObjects())
                .thenReturn(list);
    }

    @Test
    public void shouldReturnFile() throws Exception{
        ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/state-{time}", "21:21:21"))
                .andExpect(status().isOk())
                .andReturn();
        byte[] actualResponse = mvcResult.getResponse().getContentAsByteArray();

        File expectedFile = new File("src/main/resources/testdata.txt");
        PrintWriter writer = new PrintWriter(expectedFile);
        LinkedList<DataBaseObject> objects = repository.getListOfObjects();
        for (DataBaseObject object : objects) {
            writer.write(OBJECT_MAPPER.writeValueAsString(object) + "\n");
        }
        writer.close();

        FileInputStream in = new FileInputStream(expectedFile);
        byte[] expectedResponse = new byte[(int)expectedFile.length()];
        in.read(expectedResponse);
        in.close();

        assertArrayEquals(expectedResponse, actualResponse);
    }

}
