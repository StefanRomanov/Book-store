package app.project.gamestart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GamestartApplication {

    public static void main(String[] args){

       SpringApplication.run(GamestartApplication.class, args);
    }
}
