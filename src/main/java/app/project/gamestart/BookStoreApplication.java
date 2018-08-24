package app.project.gamestart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableAsync
@EnableWebSecurity
@EnableScheduling
public class BookStoreApplication {

    public static void main(String[] args){

       SpringApplication.run(BookStoreApplication.class, args);
    }
}
