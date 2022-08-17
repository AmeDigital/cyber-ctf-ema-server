package br.com.ema.EmaServer.config;

import org.springframework.security.core.GrantedAuthority;

public class EmaUserProfile implements GrantedAuthority {
    private Integer id;

    private String name;

    public EmaUserProfile(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
