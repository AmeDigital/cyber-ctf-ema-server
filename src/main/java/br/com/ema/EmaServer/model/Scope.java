package br.com.ema.EmaServer.model;

public enum Scope {

    LOGIN("auth:login"),
    LOGOUT("auth:logout"),
    READ_USER("auth:readUser"),
    GET_WALLET_ORDERS("wallet:{walletId}"),
    CREATE_ORDER("customer:{userId}:orders"),
    GET_USER_INFO("user:info");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Scope get(String value){
        if(value!=null && !value.equals("")) {
            for (Scope scope : values()) {
                if (scope.getValue().equals(value))
                    return scope;
            }
        }
        return null;
    }
}
