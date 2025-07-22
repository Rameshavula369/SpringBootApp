package com.effort.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(
                    new AntPathRequestMatcher("/v17/api/init/login**"),    
                    new AntPathRequestMatcher("/v17/api/resend/activition/code**"),
                    new AntPathRequestMatcher("/v17/api/init/**")

                )
            )
            .exceptionHandling(exception -> exception
                    .defaultAuthenticationEntryPointFor(
                        restAuthenticationEntryPoint(),
                        new AntPathRequestMatcher("/v17/api/**")
                    )
                )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                	new AntPathRequestMatcher("/v17/api/init/login**"),
                    new AntPathRequestMatcher("/v17/api/init/**"),
                    new AntPathRequestMatcher("/v17/api/resend/activition/code**"),
                    new AntPathRequestMatcher("/h2-console/**")
                ).permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers.frameOptions().disable())
            .formLogin(form -> form.defaultSuccessUrl("/home", true))
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector handlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unauthorized access. Please login.\"}");
        };
    }
}
