package com.bh.easychatbanked.config

import lombok.extern.slf4j.Slf4j
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository

@Configuration
@EnableWebSecurity()
class SecurityConfig {
    @Bean
    fun csrfTokenRepository(): CsrfTokenRepository {
        val csrfTokenRepository = HttpSessionCsrfTokenRepository()
        csrfTokenRepository.setHeaderName("X-CSRF-TOKEN")
        return csrfTokenRepository
    }
    @Bean
    @Throws(Exception::class)
    fun web(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                authorize
                    .requestMatchers("/users/**").permitAll()
                    .requestMatchers("/friends/**").permitAll()
                    .anyRequest().permitAll()
            }
            .csrf { csrfTokenRepository()}
        return http.build()
    }

}
