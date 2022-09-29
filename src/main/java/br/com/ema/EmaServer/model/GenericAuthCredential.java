package br.com.ema.EmaServer.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;


public class GenericAuthCredential {

    @Schema(
            allowableValues = {"password", "refresh_token"}
    )
    protected String grantType;
    private String clientId;

    public GenericAuthCredential() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
