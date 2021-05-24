package pl.piasta.hotel.security.utils;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) {
        String tokenExpired = (String) request.getAttribute("token-expired");
        String tokenInvalid = (String) request.getAttribute("token-invalid");
        if (tokenExpired != null) {
            handlerExceptionResolver.resolveException(request, response, null, new JwtException(tokenExpired));
            return;
        } else if (tokenInvalid != null) {
            handlerExceptionResolver.resolveException(request, response, null, new JwtException(tokenInvalid));
            return;
        }
        handlerExceptionResolver.resolveException(request, response, null, ex);
    }
}
