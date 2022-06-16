package edu.ort.tuguia.middleware

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorsMiddleware : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == HttpMethod.OPTIONS.name) {
            response.status = HttpStatus.OK.value()

            return false
        }

        return true
    }
}