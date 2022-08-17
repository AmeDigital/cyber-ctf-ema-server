package br.com.ema.EmaServer.config;


import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.model.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long ACCESS_TOKEN_VALIDITY = 60*60;
    public static final long REFRESH_TOKEN_VALIDITY = 10*60*60;
    public enum LABEL_JWT {
        USERID,
        WALLETID,
        PAYLOAD,
        SCOPE
    }

    @Value("${jwt.secret}")
    private String secret;
    @Value("${ema-server.ame.audience}")
    private String DEFAULT_AME_AUDIENCE;
    @Value("${ema-server.ame.issue}")
    private String DEFAULT_AME_ISSUE;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Map<String, Object> getPayload(String token) {
        Claims claims = getAllClaimsFromToken(token);
        if (claims.containsKey(LABEL_JWT.PAYLOAD.toString())) {
            Map<String, Object> payload = (Map<String, Object>) claims.get(LABEL_JWT.PAYLOAD.toString());
            return payload;
        }
        throw new EmaServerException(Messages.TOKEN_INVALID_OR_EXPIRED);
    }

    public String getAmePayload(String token, LABEL_JWT label) {
        token = token.replace("Bearer ", "");
        Map<String, Object> payload = getPayload(token);
        if (payload.containsKey(label.name())){
            return payload.get(label.name()).toString();
        }
        return null;
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }


    public String signAccessToken(Token token) {
        if(token!=null){
            Map<LABEL_JWT, Object> claims = new HashMap<>();
            claims.put(LABEL_JWT.PAYLOAD, token.getPayload());
            claims.put(LABEL_JWT.SCOPE, token.getScopes());
            String signedToken = generateAccessToken(claims, token.getUserId(), DEFAULT_AME_AUDIENCE, DEFAULT_AME_ISSUE);
            return signedToken;
        }
        throw new JwtException(Messages.TOKEN_INVALID_OR_EXPIRED);
    }

    public String signRefreshToken(Token token){
        if(token!=null){
            Map<LABEL_JWT, Object> claims = new HashMap<>();
            claims.put(LABEL_JWT.PAYLOAD, token.getPayload());
            claims.put(LABEL_JWT.SCOPE, token.getScopes());
            String signedToken = doGenerateRefrestToken(claims, token.getUserId(), DEFAULT_AME_AUDIENCE, DEFAULT_AME_ISSUE);
            return signedToken;
        }
        throw new JwtException(Messages.TOKEN_INVALID_OR_EXPIRED);
    }

    private String doGenerateRefrestToken(Map<LABEL_JWT, Object> claims, String subject, String audience, String issue) {
        Date expires = new Date(System.currentTimeMillis()+ REFRESH_TOKEN_VALIDITY*1000);
        return doGenerateToken(claims, subject, audience, issue, expires);
    }

    private String generateAccessToken(Map<LABEL_JWT, Object> claims, String subject, String audience, String issue) {
        Date expires = new Date(System.currentTimeMillis()+ ACCESS_TOKEN_VALIDITY*1000);
        return doGenerateToken(claims, subject, audience, issue, expires);
    }


    private String doGenerateToken(Map<LABEL_JWT, Object> claims, String subject, String audience, String issue, Date expires) {
        Map<String, Object> myStringMap = new HashMap<>();
        for (LABEL_JWT labels:
             claims.keySet()) {
            myStringMap.put(labels.name(), claims.get(labels));
        }
        return Jwts.builder()
                .setClaims(myStringMap)
                .setAudience(audience)
                .setSubject(subject)
                .setIssuer(issue)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expires)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String signedToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(signedToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(signedToken));
    }

    public boolean validateToken(String token) throws EmaServerException {
        try {
            if(token.startsWith("Bearer ")){
                token = token.replace("Bearer ", "");
            }
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new EmaServerException(Messages.TOKEN_INVALID_OR_EXPIRED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}