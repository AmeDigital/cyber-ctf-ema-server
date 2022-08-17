package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.config.EmaUserDetails;
import br.com.ema.EmaServer.config.JwtTokenUtil;
import br.com.ema.EmaServer.controller.ImageController;
import br.com.ema.EmaServer.model.*;
import br.com.ema.EmaServer.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${ema-server.client_credential.client_id}")
    public String EMA_APP_CLIENT_ID;

    @Value("${ema-server.client_credential.client_secret}")
    private String EMA_APP_CLIENT_SECRET;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private WalletService walletService;

    private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public Token generateAmeToken(UserDetails userDetails) {
        User user = userRepository.getByUsername(userDetails.getUsername());
        Set<String> scopes = scopeService.getScopesFromUser(user);
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put(JwtTokenUtil.LABEL_JWT.WALLETID.name(), user.getWallet().getUuid());
        payload.put(JwtTokenUtil.LABEL_JWT.USERID.name(), user.getUuid());
        Token token = new Token();
        token.setUserId(user.getName());
        token.setScopes(scopes);
        token.setTokenType(AccessTokenType.BEARER);
        token.setPayload(payload);
        token.setClientId(EMA_APP_CLIENT_ID);
        token.setCreatedAt(LocalDateTime.now());
        String accessTokenSigned = jwtTokenUtil.signAccessToken(token);
        String refreshTokenSigned =  jwtTokenUtil.signRefreshToken(token);
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(accessTokenSigned);
        token.setExpireAt(expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        token.setAccessToken(accessTokenSigned);
        token.setRefreshToken(refreshTokenSigned);
        return token;
    }

    @Override
    public Token refreshAmeToken(RefreshToken refreshToken) throws EmaServerException {
        if(refreshToken.getClientId().equals(EMA_APP_CLIENT_ID) &&
        refreshToken.getClientSecret().equals(EMA_APP_CLIENT_SECRET)){
            String refreshTokenStr = refreshToken.getRefreshToken();
            if(jwtTokenUtil.canTokenBeRefreshed(refreshTokenStr)){
                String username = jwtTokenUtil.getUsernameFromToken(refreshTokenStr);
                logger.info("Gerando refresh token para o usuário {};", username);
                Token newToken = generateAmeToken(loadUserByUsername(username));
                return newToken;
            }else{
                throw new EmaServerException(Messages.TOKEN_INVALID_OR_EXPIRED, HttpStatus.FORBIDDEN);
            }
        }else{
            throw new EmaServerException(Messages.INVALID_CREDENTIAL, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public String getWalletUuidFromJwt(String tokenJwt){
        String uuid = jwtTokenUtil.getAmePayload(tokenJwt, JwtTokenUtil.LABEL_JWT.WALLETID);
        return uuid;
    }

    @Override
    public String getUserUuidFromJwt(String tokenJwt) {
        String userId = jwtTokenUtil.getAmePayload(tokenJwt, JwtTokenUtil.LABEL_JWT.USERID);
        return userId;
    }


    public Authentication getAuthenticationFromJwt(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetails userDetails = this.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String getAuthorizationToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    public boolean validateToken(String token) throws EmaServerException {
        return jwtTokenUtil.validateToken(token);
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return jwtTokenUtil.canTokenBeRefreshed(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.loadUsernameAndPasswordByUsername(username);
        if(user != null){
            return new EmaUserDetails(user);
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
