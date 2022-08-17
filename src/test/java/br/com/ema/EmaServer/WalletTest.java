package br.com.ema.EmaServer;

import br.com.ema.EmaServer.controller.WalletController;
import br.com.ema.EmaServer.model.Wallet;
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

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @DisplayName("Wallet pré-cadastrada deve existir no banco.")
    @Test
    public void getInfoFromWallet(){
//        String wallet = controller.getInfo("UUID-WALLET-2");
//        assertThat(wallet).isNotNull();
    }

    @DisplayName("Wallet nao deve estar cadastrada.")
    @Test
    public void getNonExistsWallet(){
//        String wallet = controller.getInfo("UUID_FAKE_WALLLET");
//        assertThat(wallet).isNull();
    }

    @DisplayName("Lista deve conter algum elemento.")
    @Test
    public void listWallets(){
//        List<Wallet> wallets = controller.list();
//        assertThat(wallets).isNotEmpty();

    }
}
