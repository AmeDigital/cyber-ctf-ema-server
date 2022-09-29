package br.com.ema.EmaServer.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class RefreshToken extends  GenericAuthCredential{

    private String refreshToken;

    private String clientSecret;

    public RefreshToken(){
        this.setGrantType("refresh_token");
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
