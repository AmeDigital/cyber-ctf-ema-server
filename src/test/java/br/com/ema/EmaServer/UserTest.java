package br.com.ema.EmaServer;

import br.com.ema.EmaServer.repository.UserItemRepository;
import br.com.ema.EmaServer.repository.item.UserItem;
import br.com.ema.EmaServer.repository.item.WalletItem;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Value("${ema-server.test.password}")
    private String password;

    @Autowired
    private UserItemRepository userRepository;

    private void createDummyUsers(){
        UserItem user = userRepository.getByUsername("paulag6");
        if(user==null){
            user = new UserItem();
            user.setName("paulag6");
            user.setPassword(password);
            user.setUuid("uuid-paula");
            WalletItem wallet = new WalletItem();
            wallet.setUuid("UUID-WALLET-DUMMY2" );
            user.setWallet(wallet);
            userRepository.save(user);
        }
    }

    @Test
    void fillUsers(){
        this.createDummyUsers();
    }
}
