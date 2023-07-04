package com.bh.easychatbanked.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {

        registry.addMapping("/**")
            .allowedOrigins("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","Patch")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)

        // Add more mappings...
    }

//    @Bean
//    fun corsFilter(): FilterRegistrationBean<CorsFilter> {
//        val source = UrlBasedCorsConfigurationSource()
//        val config = CorsConfiguration()
//        config.addAllowedOriginPattern("*")
//        config.addAllowedHeader("*")
//        config.addAllowedMethod("*")
//        config.allowCredentials = true
//        config.maxAge = 3600
//        source.registerCorsConfiguration("/**", config)
//        val bean = FilterRegistrationBean(CorsFilter(source))
//        bean.order = Ordered.HIGHEST_PRECEDENCE
//        return bean
//    }

}