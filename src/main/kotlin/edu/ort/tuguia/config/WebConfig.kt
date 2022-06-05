package edu.ort.tuguia.config

import edu.ort.tuguia.middleware.AuthMiddleware
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuthMiddleware())
            .addPathPatterns("/api/*")
            .excludePathPatterns(
                listOf(
                    "/api/users/login",
                    "/api/users/register"
                )
            )
    }
}