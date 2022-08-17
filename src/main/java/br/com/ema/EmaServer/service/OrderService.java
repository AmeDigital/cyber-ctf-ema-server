package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Order;

import java.util.List;

public interface OrderService {

    Order findByUuid(String orderUuid);
    String generateOrderUuid();
    List<Order> getOrdersByWalletUuid(String walletUuid);
    List<Order> list();

}
