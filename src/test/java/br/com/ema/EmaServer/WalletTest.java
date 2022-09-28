package br.com.ema.EmaServer;

import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.controller.WalletController;
import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletTest {

    @Autowired
    private WalletController controller;

    @Autowired
    private WalletService service;


    @Test
    public void contextLoads(){
        assertThat(controller).isNotNull();
    }

    @DisplayName("Wallet pré-cadastrada deve existir no banco.")
    @Test
    public void getInfoFromWallet(){
        Wallet wallet = controller.getInfo("UUID-WALLET-2");
        assertThat(wallet).isNotNull();
    }

    @DisplayName("Wallet nao deve estar cadastrada.")
    public void getNonExistsWallet(){
        Wallet wallet = null;
        try {
            wallet = controller.getInfo("UUID_FAKE_WALLLET");
            assertThat(wallet).isNotNull();
        }catch (EmaServerException exc){
            assertThat(wallet).isNotNull();
        }
    }

    @DisplayName("Lista deve conter algum elemento.")
    @Test
    public void listWallets(){
        List<Wallet> wallets = service.list(0,1);
        assertThat(wallets).isNotEmpty();
    }
}
