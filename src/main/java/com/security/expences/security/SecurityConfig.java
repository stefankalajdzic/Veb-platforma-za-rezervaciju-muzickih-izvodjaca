package com.security.expences.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authentication/login").permitAll()
                        .requestMatchers("/authentication/registration").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/authentication/login")
                        .usernameParameter("username")
                        .failureUrl("/authentication/login?loginError=true")
                        .defaultSuccessUrl("/dashboard/list"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/authentication/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/authentication/login?loginRequired=true")))
                .build();
    }

}