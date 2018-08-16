package app.project.gamestart;

import app.project.gamestart.domain.enums.PegiRatings;
import app.project.gamestart.domain.enums.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class GamestartApplication {

    public static void main(String[] args) {
       SpringApplication.run(GamestartApplication.class, args);
    }

    @Bean
    public Executor asyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GameStart-");
        executor.initialize();
        return executor;
    }
}
