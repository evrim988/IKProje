package org.example.ikproje.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(req->req
                .requestMatchers("/v1/dev/admin/login").permitAll()
                .requestMatchers("/v1/dev/admin/**").hasAuthority("ADMIN")
                .requestMatchers(("/v1/dev/break/**")).hasAuthority("COMPANY_MANAGER")
                .requestMatchers("/v1/dev/companymanager/**").hasAuthority("COMPANY_MANAGER")
                .requestMatchers(("/v1/dev/employee/**")).hasAuthority("EMPLOYEE")
                .requestMatchers("/v1/dev/leave/**").hasAnyAuthority("COMPANY_MANAGER","EMPLOYEE")
                .requestMatchers("/v1/dev/shift/**").hasAnyAuthority("COMPANY_MANAGER","EMPLOYEE")
                .requestMatchers(("/v1/dev/usershift/**")).hasAnyAuthority("COMPANY_MANAGER","EMPLOYEE")
                .requestMatchers("/**").permitAll());

        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:3000");
                    corsConfig.addAllowedMethod("*");
                    corsConfig.addAllowedHeader("*");
                    corsConfig.setAllowCredentials(true);   
                    return corsConfig;
                }))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}