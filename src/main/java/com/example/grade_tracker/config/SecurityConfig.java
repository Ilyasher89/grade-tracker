package com.example.grade_tracker.config;

import com.example.grade_tracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Разрешаем доступ к статическим ресурсам всем
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        // Страницы регистрации и логина доступны всем
                        .requestMatchers("/auth/register", "/auth/login").permitAll()
                        // Страница со списком оценок доступна USER и ADMIN
                        .requestMatchers("/grades").hasAnyRole("USER", "ADMIN")
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")          // Наша кастомная страница логина
                        .loginProcessingUrl("/auth/login") // URL для обработки формы логина
                        .defaultSuccessUrl("/grades")      // Куда перенаправлять после успешного входа
                        .failureUrl("/auth/login?error")   // Куда перенаправлять при ошибке
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")         // URL для выхода
                        .logoutSuccessUrl("/auth/login?logout") // Куда перенаправлять после выхода
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/auth/access-denied") // Страница "Доступ запрещен"
                );

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}