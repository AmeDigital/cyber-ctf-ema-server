package br.com.ema.EmaServer;

import br.com.ema.EmaServer.controller.ImageController;
import br.com.ema.EmaServer.repository.AddressItemRepository;
import br.com.ema.EmaServer.repository.item.AddressItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class AddressTest {

    @Autowired
    private AddressItemRepository addressItemRepository;

    private final static Logger logger = LoggerFactory.getLogger(AddressTest.class);

    @Test
    public void uploadAddresses(){
        List<AddressItem> addresses = addressItemRepository.findAll();
        if(addresses.isEmpty()){
            loadAddressFromFile();
        }
    }

    private void loadAddressFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File f = new File(AddressTest.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            f = f.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
            Map<?, ?> json = mapper.readValue(Paths.get(f.getPath() + "\\apoio\\enderecos_geolocalizacao.json").toFile(), Map.class);
//            Map<?, ?> json = mapper.readValue(Paths.get("C:\\Users\\paulo.salgueiro\\OneDrive - Ame Digital\\Documentos\\GitHub\\appsec_utils\\projects\\ctf\\apoio\\enderecos_geolocalizacao.json").toFile(), Map.class);
            List<Map<String, String>> mapa = (List<Map<String, String>> ) json.get("mapa");
            for (Map<String, String> obj : mapa) {
                AddressItem addressItem = new AddressItem();
                addressItem.setId(Long.parseLong(obj.get("id")));
                addressItem.setLatitude(Double.parseDouble(obj.get("latitude")));
                addressItem.setLongitude(Double.parseDouble(obj.get("longitude")));
                addressItem.setState(obj.get("uf"));
                addressItem.setCity(obj.get("cidade"));
                addressItem.setDistrict(obj.get("bairro"));
                addressItem.setAddress(obj.get("endereco"));
                addressItem.setCep(obj.get("cep"));
                addressItem.setCategory(obj.get("categoria"));
                logger.info(addressItem.toString());
                addressItemRepository.save(addressItem);
            }
            addressItemRepository.flush();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
