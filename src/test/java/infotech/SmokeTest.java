package infotech;

import infotech.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTest {
    @Autowired
    DumpController dumpController;
    @Autowired
    GetController getController;
    @Autowired
    LoadController loadController;
    @Autowired
    MainController mainController;
    @Autowired
    RemoveController removeController;
    @Autowired
    SetController setController;

    @Test
    public void contextLoads() {
        assertThat(dumpController).isNotNull();
        assertThat(getController).isNotNull();
        assertThat(loadController).isNotNull();
        assertThat(mainController).isNotNull();
        assertThat(removeController).isNotNull();
        assertThat(setController).isNotNull();
    }
}
