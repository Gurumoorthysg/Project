package com.cognizant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
   

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	System.out.println("Entering filter chain");
    	
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(user -> user
            		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/user/login","/api/user/register","/api/event/getAllEvent","/api/event/getById/**","/api/event/category/**","/api/event/location/**","/api/event/date/**").permitAll()
                
                .requestMatchers("/api/feedback/**","/api/event/add","/api/event/update/**","/api/event/delete/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/api/ticket/booktickets","/api/ticket/cancelticket/**","/api/ticket/viewallticketsByUserId/**").hasAnyRole("USER","ADMIN")
                .requestMatchers("/api/user/**","/api/notification/**","/api/ticket/**","/api/event/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("Returning to http build");
        return http.build();
    }
    
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//        converter.setAuthorityPrefix("ROLE_");
//        converter.setAuthoritiesClaimName("roles"); // or "authorities" depending on your token
//
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
//        return jwtConverter;
//    }
}
