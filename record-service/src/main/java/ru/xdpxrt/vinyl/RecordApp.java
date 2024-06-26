package ru.xdpxrt.vinyl;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableCaching
@EnableFeignClients
@EnableDiscoveryClient
@EnableMethodSecurity
@SpringBootApplication
@EnableEncryptableProperties
public class RecordApp {
    public static void main(String[] args) {
        SpringApplication.run(RecordApp.class);
    }
}