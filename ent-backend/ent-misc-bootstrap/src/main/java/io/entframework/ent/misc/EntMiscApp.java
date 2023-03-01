package io.entframework.ent.misc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class EntMiscApp {

    public static void main(String[] args) {
        SpringApplication.run(EntMiscApp.class, args);
    }

}
