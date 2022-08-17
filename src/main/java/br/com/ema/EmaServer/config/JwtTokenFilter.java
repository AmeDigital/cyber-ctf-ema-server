package br.com.ema.EmaServer.config;

import br.com.ema.EmaServer.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class JwtTokenFilter extends OncePerRequestFilter {

    private AuthService authService;

    public JwtTokenFilter(AuthService authService){
        this.authService = authService;
    }

    private Logger logger = LogManager.getLogger(JwtTokenFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = authService.getAuthorizationToken(request);
        try {
            if (token != null && authService.validateToken(token)) {
                Authentication auth = authService.getAuthenticationFromJwt(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (EmaServerException ex) {
            EmaAppError error = new EmaAppError();
            error.setMessage(ex.getMessage());
            error.setStatus(ex.getStatus());
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
            ((HttpServletResponse) response).setStatus(ex.getStatus().value());
            String serialized = new ObjectMapper().writeValueAsString(error);
            response.getOutputStream().write(serialized.getBytes(StandardCharsets.UTF_8));
            logger.error("AmeServer[ERROR]: ${} - {}", request.getRequestURI(), ex.getMessage());
            SecurityContextHolder.clearContext();
            return;
        }
        chain.doFilter(request, response);
    }
}
