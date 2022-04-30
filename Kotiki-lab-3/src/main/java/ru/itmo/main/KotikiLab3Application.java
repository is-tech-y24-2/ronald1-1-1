package ru.itmo.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itmo.main.service.UserService;

@SpringBootApplication
public class KotikiLab3Application {

    public static void main(String[] args) {
        SpringApplication.run(KotikiLab3Application.class, args);
    }


}
