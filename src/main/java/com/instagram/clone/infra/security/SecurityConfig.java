package com.instagram.clone.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @SuppressWarnings("unused")
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/{currentUsername}/follow/{targetUsername}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/{currentUsername}/unfollow/{targetUsername}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}/isFollowing/{targetUsername}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/{username}/newPost").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}/posts").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}/feed").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/images/upload").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/posts/{postId}/likes").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/posts/{postId}/likes").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/posts/{postId}/likes/check-like").permitAll()
                        .requestMatchers(HttpMethod.POST, "/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/comments/post/{postId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/p/{postId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "user/{username}/verify").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/user/updateProfile/{username}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/p/{postId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/user/{username}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/change-password").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/auth/change-email").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/verify-code").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/comments/{commentId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{postId}/likes/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}/following").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}/followers").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/top-users").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}