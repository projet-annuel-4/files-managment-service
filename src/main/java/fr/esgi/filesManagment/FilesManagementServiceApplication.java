package fr.esgi.filesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FilesmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesmanagementApplication.class, args);
    }

}
