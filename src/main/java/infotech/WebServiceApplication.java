package infotech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class WebServiceApplication {

    public static void main(String[] args){
        try {
            //Statement statement = conn.createStatement();
            //statement.executeUpdate("CREATE TABLE IF NOT EXISTS OBJECTS (KEY VARCHAR(255) NOT NULL PRIMARY KEY, ATTRIBUTES VARCHAR(255), TTL INT)");
            SpringApplication.run(WebServiceApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
