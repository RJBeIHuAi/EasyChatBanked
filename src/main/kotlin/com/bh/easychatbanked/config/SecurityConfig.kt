package com.bh.easychatbanked.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity()
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                authorize
                    .requestMatchers("/users/**").permitAll()
                    .requestMatchers("/friends/**").permitAll()
                    .anyRequest().permitAll()
            }
            .csrf {  }
        return http.build()
    }

}