package br.com.ema.EmaServer.repository.item;


import br.com.ema.EmaServer.model.Order;
import br.com.ema.EmaServer.model.Wallet;

import javax.persistence.*;

@Entity
@Table(name = "TB_ORDER")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "cashback_percent")
    private Double cashbackPercent;

    @ManyToOne
    @JoinColumn(name="wallet_id", nullable = false)
    private WalletItem referencedWallet;


    public OrderItem(Order order){
        this.id = order.getId();
        this.amount = order.getAmount();
        this.cashbackPercent = order.getCashbackPercent();
        this.description = order.getDescription();
        this.name = order.getName();
        this.uuid = order.getUuid();
        this.referencedWallet = new WalletItem(order.getReferencedWallet());

    }

    public OrderItem() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCashbackPercent() {
        return cashbackPercent;
    }

    public void setCashbackPercent(Double cashbackPercent) {
        this.cashbackPercent = cashbackPercent;
    }

    public WalletItem getReferencedWallet() {
        return referencedWallet;
    }

    public void setReferencedWallet(WalletItem referencedWallet) {
        this.referencedWallet = referencedWallet;
    }

    public Order toModel(){
        return new Order.Builder().setId(this.getId())
                .setUuid(this.getUuid()).setName(this.getName())
                .setDescription(this.getDescription())
                .setAmount(this.getAmount())
                .setCashbackPercent(this.getCashbackPercent())
                .setReferencedWallet(this.getReferencedWallet().toModel(null)).build();
    }
}
