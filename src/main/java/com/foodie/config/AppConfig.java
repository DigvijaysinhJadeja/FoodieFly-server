package com.foodie.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig { // here we will write spring security configuration

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//which endpoint should be whitelisted,secure,accessable certain role provide by user.
                .authorizeHttpRequests(Authorize->Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole( "RESTAURANT_OWNER","ADMIN") // endpoint starting with api/admin is available for only those user who have a role of restaurant owner or admin.
                        .requestMatchers("/api/**").authenticated() // user must provide JWT token then he will be able to provide all the endpoints
                        .anyRequest().permitAll()// without any token user can only access this (only during signup/signin)
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)  // filter is used to check weather jwt token is given or not we need to validate
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();

    }


    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
           @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();

                cfg.setAllowedOrigins(Arrays.asList(
//                        "",  use this when i have deployed by backend
                        "https://foodie-fly.vercel.app"
                )); // now we can set frontend urls which this backend should be accessable
                cfg.setAllowedMethods(Collections.singletonList("*"));// methods we want to allow for frontend url
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization")); // we will use only authorization so that we can send our jwt token
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // frist we will bcrypt the password for security purpose so that other cannot read that password
    }
}
