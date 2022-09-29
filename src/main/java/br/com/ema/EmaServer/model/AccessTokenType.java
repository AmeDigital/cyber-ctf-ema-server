package br.com.ema.EmaServer.model;

public enum AccessTokenType {

    BEARER("Bearer"), BASIC("Basic");

    private final String value;

    AccessTokenType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
