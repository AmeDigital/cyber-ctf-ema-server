package br.com.ema.EmaServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"id","referencedWallet"})
public class Order {

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    private Long id;
    private String uuid;
    private String name;
    private String description;
    private Double amount = 0D;
    private Double cashbackPercent;
    private Wallet referencedWallet;



    public Order(Builder builder) {
        this.id = builder.id;
        this.uuid = builder.uuid;
        this.name = builder.name;
        this.description = builder.description;
        this.amount = builder.amount;
        this.cashbackPercent = builder.cashbackPercent;
        this.referencedWallet = builder.referencedWallet;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String uuid;
        private String name;
        private String description;
        private Double amount = 0D;
        private Double cashbackPercent;
        private Wallet referencedWallet;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setCashbackPercent(Double cashbackPercent) {
            this.cashbackPercent = cashbackPercent;
            return this;
        }

        public Builder setReferencedWallet(Wallet referencedWallet) {
            this.referencedWallet = referencedWallet;
            return this;
        }

        public Order build() {
            return new Order(this);
        }

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getCashbackPercent() {
        return cashbackPercent;
    }

    public Wallet getReferencedWallet() {
        return referencedWallet;
    }

    public void setReferencedWallet(Wallet referencedWallet) {
        this.referencedWallet = referencedWallet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCashbackPercent(Double cashbackPercent) {
        this.cashbackPercent = cashbackPercent;
    }

    public Order() {
    }

    @Override
    public String toString() {
        return  '{' + "\r\n" +
                "   \"orderUuid\"='" + uuid + '\'' + ',' + "\r\n" +
                "   \"name\"='" + name + '\'' + ',' + "\r\n" +
                "   \"description\"='" + description + '\'' + ',' + "\r\n" +
                "   \"amount\"='" + amount + '\'' + ',' + "\r\n" +
                "   \"cashbackPercent\"='" + cashbackPercent + '\'' + ',' + "\r\n" +
                "   \"referencedWalletUuid\"='" + referencedWallet.getUuid() + '\'' + "\r\n" +
                '}';
    }

}