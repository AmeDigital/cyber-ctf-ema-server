package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.Order;
import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.repository.OrderRepository;
import br.com.ema.EmaServer.service.AuthService;
import br.com.ema.EmaServer.service.OrderService;
import br.com.ema.EmaServer.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private WalletService walletService;

    private static final Logger LOG = LogManager.getLogger(OrderController.class);

    @Operation(summary = "Listagem de Ordens associadas a uma Wallet.")
    @GetMapping(value = "/{walletUuid}/list", produces = "application/json")
    public List<Order> getOrdersByWalletUuid(@PathVariable("walletUuid") String walletUuid, @RequestHeader (name="Authorization") String token){
        String walletIdFromToken = authService.getWalletUuidFromJwt(token);
        if(walletIdFromToken==null || !walletIdFromToken.equals(walletUuid)){
            throw new EmaServerException(Messages.OPERATION_NOT_ALLOWED, HttpStatus.FORBIDDEN);
        }
        List<Order> ordersByWalletUuid = orderService.getOrdersByWalletUuid(walletUuid);
        return ordersByWalletUuid;
    }

    @Operation(hidden = true, summary = "Informações relacionadas a uma determinada Ordem.")
    @GetMapping("/{orderUuid}/info")
    public Order getInfo(@PathVariable("orderUuid") String orderUuid, @RequestHeader (name="Authorization") String token){
        Order order = this.orderService.findByUuid(orderUuid);
        return order;
    }

    @Operation(hidden = true)
    @PutMapping("/{orderUuid}/update")
    //TO DO configurar o firewall local da máquina para não permitir conexões de dentro para fora
    public Order updateCashback(@RequestBody Order newOrder, @PathVariable String orderUuid, @RequestHeader (name="Authorization") String token) throws IOException {
        Order order = (this.orderService.findByUuid(orderUuid));
        Order output = (this.orderService.findByUuid(orderUuid));
        LOG.error("Cadastrando a nova Order description {}", newOrder.getDescription());

        LOG.info(newOrder.getDescription());
        order.setCashbackPercent(newOrder.getCashbackPercent());
        output.setCashbackPercent(newOrder.getCashbackPercent());

        order = repository.save(order);

        String FLAG_UUID = "3b366c9b-4022-4fae-b2d5-63f0ca298efd";
        if (Objects.equals(order.getUuid(), FLAG_UUID)) {
            output.setDescription("FLAG#5_{4v01d_Br0k3n_Func710n_L3v3l_4u7h0r1z4710n_1n_Y0ur_C0d3}");
            return output;
        } else {
            return order;
        }

    }

    @Operation(summary = "Listagem de todas as Ordens do sistema.")
    @GetMapping("/list")
    public List<Order> list(@RequestHeader (name="Authorization") String token){
        return orderService.list();
    }

    @Operation(summary = "Criação de Ordens de Compra.")
    @PostMapping("/create")
    public Order orderCreate(@RequestBody Order order, @RequestHeader (name="Authorization") String token) {
        String referencedWalletUuid = authService.getWalletUuidFromJwt(token);
        Wallet referencedWallet = this.walletService.findByUuid(referencedWalletUuid);
        order.setReferencedWallet(referencedWallet);
        order.setUuid(orderService.generateOrderUuid());
        order =  repository.save(order);
        return order;
    }

}
