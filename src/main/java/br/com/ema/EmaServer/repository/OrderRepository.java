package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.model.Order;
import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.repository.item.OrderItem;
import br.com.ema.EmaServer.repository.item.WalletItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Component
public class OrderRepository {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order get(Long id){
        OrderItem orderItem = this.orderItemRepository.getById(id);
        if(orderItem!=null){
            return orderItem.toModel();
        }
        return null;
    }

    public Order getByUuid(String orderUuid){
        OrderItem orderItem = orderItemRepository.getByUuid(orderUuid);
        if(orderItem!=null) {
            return orderItem.toModel();
        }
        return null;
    }

    public Order save(Order order){
        OrderItem orderItem = new OrderItem(order);

        orderItem = this.orderItemRepository.save(orderItem);
        return orderItem.toModel();
    }

    public Set<Order> List() {
        return null;
    }

    public Order findByUuid(String orderUuid) {
        OrderItem orderItem = orderItemRepository.getByUuid(orderUuid);
        if(orderItem!=null){
            return orderItem.toModel();
        }
        return null;
    }

    public List<Order> list(){
        List<OrderItem> list = orderItemRepository.findAll();
        if(list!=null){
            List<Order> l = new ArrayList<>();
            list.stream().forEach(o -> l.add(o.toModel()));
            return l;
        }
        return null;
    }

    public List<Order> getOrdersByWalletUuid(String referencedWalletUuid){
        List<OrderItem> list = orderItemRepository.findAll();
        if(list!=null){
            List<Order> l = new ArrayList<>();
            for (OrderItem o : list) {
                if (!Objects.equals(o.getReferencedWallet().getUuid(), referencedWalletUuid)) {
                    continue;
                }
                l.add(o.toModel());
            }
            return l;
        }
        return null;
    }
}
