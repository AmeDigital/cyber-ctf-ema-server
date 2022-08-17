package br.com.ema.EmaServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

@JsonIgnoreProperties(value = {"id","wallet","profiles","password"})
public class User {

    private Long id;
    private String name;
    private String password;
    private String uuid;

    private Set<UserProfile> profiles;
    private Wallet wallet;

    public User(Long id, String name, String email) {
        this();
        this.id = id;
        this.name = name;
        this.uuid = uuid;
    }
    public User() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<UserProfile> profiles) {
        this.profiles = profiles;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public static String getHashMd5(String value) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        BigInteger hash = new BigInteger(1, md.digest(value.getBytes()));
        return hash.toString(16);
    }

    public User toModelHash(){
        User user = new User();
        user.setId(this.getId());
        user.setName(this.getName());
        user.setPassword(this.getHashMd5(this.getPassword()));
        user.setUuid(this.getUuid());

        return user;
    }
}
