package com.ispan.warashibe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
//                    .requestMatchers("/login", "/oauth2/**", "/warashibe/**").permitAll()  // 允許訪問登錄和OAuth2相關的URL
                    .anyRequest().permitAll()  // 其他請求需要認證
            )
            .csrf(csrf -> csrf.disable())
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .loginPage("/login")  // 自訂登錄頁面
                    .defaultSuccessUrl("/home")  // 登錄成功後的重定向頁面
                    .failureUrl("/login?error=true")  // 登錄失敗後的重定向頁面
            )
            .logout(logout -> 
                logout
                    .logoutUrl("/logout")  // 自訂登出URL
                    .logoutSuccessUrl("/login?logout=true")  // 登出成功後的重定向頁面
            );

        return http.build();
    }


}

