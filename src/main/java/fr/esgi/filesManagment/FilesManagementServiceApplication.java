package fr.esgi.filesManagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FilesManagementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesManagementServiceApplication.class, args);
    }

}
