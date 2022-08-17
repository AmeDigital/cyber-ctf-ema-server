package br.com.ema.EmaServer.repository.item;

import br.com.ema.EmaServer.model.Order;
import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.model.Wallet;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "TB_USER")
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", length = 32, nullable = false)
    private String uuid;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private WalletItem wallet;

    public UserItem() {
    }

    public UserItem(String username, String password) {
        this.setName(username);
        this.setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public WalletItem getWallet() {
        return wallet;
    }

    public void setWallet(WalletItem wallet) {
        this.wallet = wallet;
    }

    public User toModel(){
        User user = new User();
        user.setId(this.getId());
        user.setName(this.getName());
        user.setPassword(this.getPassword());
        user.setUuid(this.getUuid());
        if(this.wallet!=null)
            user.setWallet(this.wallet.toModel(user));
        return user;
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
        user.setPassword(getHashMd5(this.getPassword()));
        user.setUuid(this.getUuid());
        if(this.wallet!=null)
            user.setWallet(this.wallet.toModel(user));
        return user;
    }

    public User toModel(Wallet wallet) {
        User user = new User();
        user.setId(this.getId());
        user.setName(this.getName());
        user.setPassword(this.getPassword());
        user.setUuid(this.getUuid());
        if(wallet!=null)
            user.setWallet(wallet);
        return user;
    }
}
