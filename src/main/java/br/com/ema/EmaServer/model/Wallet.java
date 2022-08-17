package br.com.ema.EmaServer.model;


import br.com.ema.EmaServer.repository.item.OrderItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(value = {"id","ownerUuid"})
public class Wallet {

    private final Long id;
    private final String uuid;
    @JsonBackReference
    private User owner;
    private String ownerUuid;
    private String ownerName;
    private List<Order> orders;
    private Double balance = 0D;

    public User getOwner() {
        return owner;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        if (this.owner!=null) {
            this.ownerUuid = this.owner.getUuid();
            this.ownerName = this.owner.getName();
        }
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerUuid() {
        return this.getUuid();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    private Wallet(Builder builder) {
        this.id = builder.id;
        this.uuid = builder.uuid;
        this.setOwner(builder.owner);
        this.balance = builder.balance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String uuid;
        private User owner;
        private List<Order> orders;
        private Double balance = 0D;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setOwner(User owner) {
            this.owner = owner;
            return this;
        }

        public Builder setBalance(Double balance) {
            this.balance = balance;
            return this;
        }

        public Builder setOrders(List<Order> orders) {
            this.orders = orders;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }

        public Long getId() {
            return id;
        }

        public String getUuid() {
            return uuid;
        }

        public User getOwner() {
            return owner;
        }

        public List<Order> getOrders() {
            return orders;
        }

        public Double getBalance() {
            return balance;
        }
    }

    @Override
    public String toString() {
        List<String> list = new ArrayList<>();
        if(orders != null){
            for (Order o : orders) {
                list.add(o.getUuid());
            }
        } else {
            list = null;
        }


        return "{" + "\r\n" +
                "   WalletUuid ='" + uuid + '\'' + "," + "\r\n" +
                "   OwnerName ='" + "abobrinha" + '\'' + "," + "\r\n" +
                "   OrdersList ='" + list + '\'' + "," + "\r\n" +
                '}';
    }
}
