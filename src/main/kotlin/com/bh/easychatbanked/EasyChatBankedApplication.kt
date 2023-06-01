package com.bh.easychatbanked

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class EasyChatBankedApplication

fun main(args: Array<String>) {
    runApplication<EasyChatBankedApplication>(*args)
}
