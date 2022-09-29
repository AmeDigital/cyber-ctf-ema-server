package br.com.ema.EmaServer.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Token {

    final static String TOKEN_BEARER = "BEARER";
    private  String accessToken= null;
    private  String refreshToken= null;
    private  String clientId= null;
    private  String userId= null;
    private  AccessTokenType tokenType= null;
    private  LocalDateTime createdAt= null;
    private  LocalDateTime expireAt= null;
    private  Set<String> scopes= null;
    private  Map<String, Object> payload = null;
    private String FLAG = "FLAG#2_{N0w_Y0u_H4v3_Y0ur_71ck37}";



    public Token(){

    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTokenType(AccessTokenType tokenType) {
        this.tokenType = tokenType;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public void setScopes(Set<String> scopes) {
        this.scopes = scopes;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }



    public AccessTokenType getTokenType() {
        return tokenType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUserId() {
        return userId;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public Set<String> getScopes() {
        return scopes != null ? new HashSet<>(scopes) : new HashSet<>();
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public long getExpiresIn() {
        if (expireAt != null)
            return LocalDateTime.now().until(expireAt, ChronoUnit.SECONDS);
        return -1;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public boolean isExpired() {
        return expireAt == null || LocalDateTime.now().isAfter(expireAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        if (accessToken != null ? !accessToken.equals(token.accessToken) : token.accessToken != null) return false;
        if (refreshToken != null ? !refreshToken.equals(token.refreshToken) : token.refreshToken != null) return false;
        if (tokenType != token.tokenType) return false;
        if (clientId != null ? !clientId.equals(token.clientId) : token.clientId != null) return false;
        if (userId != null ? !userId.equals(token.userId) : token.userId != null) return false;
        if (createdAt != null ? !createdAt.equals(token.createdAt) : token.createdAt != null) return false;
        if (expireAt != null ? !expireAt.equals(token.expireAt) : token.expireAt != null) return false;
        if (scopes != null ? !scopes.equals(token.scopes) : token.scopes != null) return false;
        return payload != null ? payload.equals(token.payload) : token.payload == null;
    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (refreshToken != null ? refreshToken.hashCode() : 0);
        result = 31 * result + tokenType.hashCode();
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (expireAt != null ? expireAt.hashCode() : 0);
        result = 31 * result + (scopes != null ? scopes.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
