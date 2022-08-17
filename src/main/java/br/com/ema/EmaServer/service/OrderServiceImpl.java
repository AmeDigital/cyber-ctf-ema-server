package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.*;
import br.com.ema.EmaServer.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public Order findByUuid(String orderUuid) {
        return orderRepository.findByUuid(orderUuid);
    }

    @Override
    public String generateOrderUuid() {
        String OrderUuid = UUID.randomUUID().toString();
        return OrderUuid;
    }

    @Override
    public List<Order> list() {
        return this.orderRepository.list();
    }

    @Override
    public List<Order> getOrdersByWalletUuid(String walletUuid) {
        return this.orderRepository.getOrdersByWalletUuid(walletUuid);
    }

}
