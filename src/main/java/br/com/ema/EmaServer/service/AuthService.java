package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.RefreshToken;
import br.com.ema.EmaServer.model.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

public interface AuthService extends  UserDetailsService {


    Token generateAmeToken(UserDetails userDetails);
    Token refreshAmeToken(RefreshToken refreshToken) throws EmaServerException;

    Authentication getAuthenticationFromJwt(String token);
    String getAuthorizationToken(HttpServletRequest req);

    String getWalletUuidFromJwt(String token);
    String getUserUuidFromJwt(String tokenJwt);

    boolean validateToken(String token) throws EmaServerException;
    Boolean isTokenExpired(String token);
}
