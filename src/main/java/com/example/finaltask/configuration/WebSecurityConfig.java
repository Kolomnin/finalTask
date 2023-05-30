package com.example.finaltask.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

  private static final String[] AUTH_WHITELIST = { //  массив URL-шаблонов, которые разрешены без аутентификации.
          "/swagger-resources/**",
          "/swagger-ui.html",
          "/v3/api-docs",
          "/webjars/**",
          "/login",
          "/register"
  };

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user =
            User.builder()
                    .username("user@gmail.com")
                    .password("password")
                    .passwordEncoder((plainText) -> passwordEncoder().encode(plainText))
                    .roles("USER")
                    .build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .authorizeHttpRequests(
                    (authorization) ->
                            authorization
                                    .mvcMatchers(AUTH_WHITELIST)
                                    .permitAll()
                    /**
                     Эта строка отвечает за то, какие запросы будут под авторизацией(нужно закомментировать).
                     */
//                    .mvcMatchers("/ads/**", "/users/**").authenticated()
            )
            .cors()
            .and()
            .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}