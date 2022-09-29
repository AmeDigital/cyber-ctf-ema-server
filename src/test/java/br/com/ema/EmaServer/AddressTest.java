package br.com.ema.EmaServer;

import br.com.ema.EmaServer.repository.AddressItemRepository;
import br.com.ema.EmaServer.repository.item.AddressItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AddressTest {

    @Autowired
    private AddressItemRepository addressItemRepository;


    @Test
    public void uploadAddresses(){
        List<AddressItem> addresses = addressItemRepository.findAll();
        assertThat(addresses).isNotEmpty();
    }

}
