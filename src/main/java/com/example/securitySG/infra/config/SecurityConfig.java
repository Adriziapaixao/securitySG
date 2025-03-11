package com.example.securitySG.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private final JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {

                    authorize.requestMatchers("/auth/refresh-token").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/users").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/users/user").permitAll();
                    authorize.requestMatchers(HttpMethod.POST, "/auth/login").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // Configura autenticação básica
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Define política de sessão como stateless (recomendado para APIs REST)



        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
