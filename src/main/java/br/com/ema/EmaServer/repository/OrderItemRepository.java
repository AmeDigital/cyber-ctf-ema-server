package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.repository.item.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select o from OrderItem o where o.uuid = ?1")
    OrderItem getByUuid(String uuid);
}
