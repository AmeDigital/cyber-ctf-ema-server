package br.com.ema.EmaServer.model;


import java.util.ArrayList;
import java.util.List;

public class UserProfile {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private List<String> scopes;


    public UserProfile(){

    }

    public UserProfile(Long id, String name, Scope ... scopesEnum) {
        this.id = id;
        this.name = name;
        this.scopes = new ArrayList<>();
        for (int i = 0;i<scopesEnum.length;i++){
            this.scopes.add(scopesEnum[i].getValue());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }
}
