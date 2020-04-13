package com.demente.ideas.learnwords.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author 1987diegog
 */
@Configuration
public class GlobalConfig {

    // Registramos nuestro passwordEncoder como componente Spring.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
