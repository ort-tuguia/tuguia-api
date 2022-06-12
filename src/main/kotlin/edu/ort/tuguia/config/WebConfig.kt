package edu.ort.tuguia.config

import edu.ort.tuguia.middleware.AuthMiddleware
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .exposedHeaders("Authorization")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(AuthMiddleware())
            .addPathPatterns("/api/*")
            .excludePathPatterns(
                listOf(
                    "/api/user/login",
                    "/api/user/register"
                )
            )
    }
}