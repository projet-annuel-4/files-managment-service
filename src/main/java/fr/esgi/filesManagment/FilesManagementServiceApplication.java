package fr.esgi.filesManagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class filesManagmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(filesManagmentApplication.class, args);
    }

}
