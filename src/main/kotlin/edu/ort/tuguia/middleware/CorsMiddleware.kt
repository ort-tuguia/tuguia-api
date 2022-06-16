package edu.ort.tuguia.middleware

import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val AccessControlMaxAge = "300"

class CorsMiddleware : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == "OPTIONS") {
            response.setHeader("Access-Control-Allow-Origin", "*")
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
            response.setHeader("Access-Control-Max-Age", AccessControlMaxAge)
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type")
            response.status = HttpStatus.OK.value()

            return false
        }

        return true
    }
}