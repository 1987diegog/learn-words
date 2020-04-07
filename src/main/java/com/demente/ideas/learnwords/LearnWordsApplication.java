package com.demente.ideas.learnwords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

//same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
@EntityScan(basePackageClasses = {
        LearnWordsApplication.class,
        Jsr310JpaConverters.class
})
public class LearnWordsApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(LearnWordsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Solamente a modo de ejemplo, se encriptan dos password que seran posteriormente utilizadas
        // en el import.sql
        String password_admin = "admin";
        String password_num = "user";

        String bCryptPassword = passwordEncoder.encode(password_admin);
        System.out.println("BCryptPasswordEncoder - Clave encriptada para admin: " + bCryptPassword);

        bCryptPassword = passwordEncoder.encode(password_num);
        System.out.println("BCryptPasswordEncoder - Clave encriptada para otros usuarios: " + bCryptPassword);
    }
}
