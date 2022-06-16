package edu.ort.tuguia.config

import edu.ort.tuguia.middleware.AuthMiddleware
import edu.ort.tuguia.middleware.CorsMiddleware
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .exposedHeaders("Authorization", "Access-Control-Allow-Headers", "Access-Control-Allow-Origin")
            .allowedOrigins("*")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
            .allowedMethods("*")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(CorsMiddleware())
            .addPathPatterns("/api/*")
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