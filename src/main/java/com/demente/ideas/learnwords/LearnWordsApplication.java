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

/**
 * @author 1987diegog
 */
//same as @Configuration @EnableAutoConfiguration @ComponentScan
@SpringBootApplication
@EntityScan(basePackageClasses = {
        LearnWordsApplication.class,
        Jsr310JpaConverters.class
})
public class LearnWordsApplication implements CommandLineRunner {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(LearnWordsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("----------------------------------------------- ");
        System.out.println("------------- LEARN WORDS STARTED ------------- ");
        System.out.println("----------------------------------------------- ");
    }
}
