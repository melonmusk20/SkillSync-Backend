package com.resume.ResumeMatcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){

                http
                   .csrf(csrf -> csrf.disable())
                   .authorizeHttpRequests(auth -> auth.requestMatchers("/customers/register", "/auth/login")
                                          .permitAll()
                                          .anyRequest()
                                          .authenticated())
                                          .addFilterBefore(jwtAuthFilter, UsernamePasswordAutheticationFilter.class);
                  
        
           return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
            throws Exception{
               return configuration.getAuthenticationManager();
            }
}
