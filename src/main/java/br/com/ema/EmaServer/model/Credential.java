package br.com.ema.EmaServer.model;

public class Credential extends GenericAuthCredential {

    private String password;
    private String username;

    public Credential(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
