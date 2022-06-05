package edu.ort.tuguia.middleware

import edu.ort.tuguia.tools.auth.JwtAuth
import edu.ort.tuguia.tools.helpers.http.ApiException
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthMiddleware : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        JwtAuth.getTokenFromRequest(request)
            ?: throw ApiException(HttpStatus.UNAUTHORIZED, "Se debe realizar login para acceder a este recurso")
        return true
    }
}