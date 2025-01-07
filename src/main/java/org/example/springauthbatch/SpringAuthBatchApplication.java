package org.example.springauthbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpringAuthBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthBatchApplication.class, args);
    }

}
