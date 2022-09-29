package br.com.ema.EmaServer.repository.item;

import br.com.ema.EmaServer.model.Order;
import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.model.Wallet;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_WALLET")
public class WalletItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @OneToOne(mappedBy = "wallet")
    private UserItem owner;

   @OneToMany(mappedBy = "referencedWallet")
   private List<OrderItem> orders;

   public WalletItem(){

   }

   public WalletItem(Wallet wallet){
       this.id = wallet.getId();
       this.uuid = wallet.getUuid();
       if (wallet.getOrders() != null){
           this.orders = new ArrayList<>();
           for(Order order: wallet.getOrders()){
               orders.add(new OrderItem(order));
           }
       }
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

    public UserItem getOwner() {
        return owner;
    }

    public void setOwner(UserItem owner) {
        this.owner = owner;
    }

    public List<OrderItem> getOrders() {
        return null;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }


    public Wallet toModel(User user){
        return new Wallet.Builder()
                .setId(this.getId())
                .setUuid(this.getUuid())
                .setOwner(user)
                .build();
    }
}
