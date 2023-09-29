package com.biblioteca.bibliotecauteq.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@AllArgsConstructor
public class WeSecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/login/validate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/areaConocimiento").authenticated()
                        .requestMatchers(HttpMethod.GET, "/areaConocimiento").permitAll()
                        .requestMatchers(HttpMethod.POST, "/subAreaConocimiento").authenticated()
                        .requestMatchers(HttpMethod.GET, "/subAreaConocimiento/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/subAreaEspecificas").authenticated()
                        .requestMatchers(HttpMethod.GET, "/subAreaEspecificas/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/upload").authenticated()
                        .requestMatchers("/usuarios").authenticated()
                        .requestMatchers(HttpMethod.GET, "/libro/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/libro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/libro").permitAll()
                        .requestMatchers("/autoresLibro").permitAll()
                        .requestMatchers("/capitulo").permitAll()
                        .requestMatchers("/capitulo/eliminarLibro").authenticated()
                        .requestMatchers("/downloadZip").permitAll()
                        .requestMatchers("/download").permitAll()
                        .requestMatchers("/files").permitAll()
                        .requestMatchers("/files/portada").permitAll()
                        .requestMatchers("/files/pdf").permitAll()
                        .requestMatchers("/api/libro").authenticated()
                        .requestMatchers("/autor").authenticated()
                        .requestMatchers("/tipoAutor").authenticated()
                );
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
