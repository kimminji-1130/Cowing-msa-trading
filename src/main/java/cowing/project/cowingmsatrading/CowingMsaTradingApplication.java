package cowing.project.cowingmsatrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CowingMsaTradingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CowingMsaTradingApplication.class, args);
    }

}
