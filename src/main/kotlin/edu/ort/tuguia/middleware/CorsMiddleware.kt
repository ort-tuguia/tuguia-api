package edu.ort.tuguia.middleware

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val AccessControlMaxAgeValue = "300"

class CorsMiddleware : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.method == HttpMethod.OPTIONS.name) {
            response.addHeader("Access-Control-Allow-Origin", "*")
            response.addHeader("Access-Control-Max-Age", AccessControlMaxAgeValue)
            response.status = HttpStatus.OK.value()

            return false
        }

        return true
    }
}