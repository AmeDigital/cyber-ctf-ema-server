package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.Address;
import br.com.ema.EmaServer.repository.item.AddressItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AddressRepository {

    @Autowired
    private AddressItemRepository addressItemRepository;

    private final static Logger logger = LoggerFactory.getLogger(AddressRepository.class);

    public Address getSellerByCep(String cep){
        AddressItem addressItem = this.addressItemRepository.getByCep(cep);
        if(addressItem!=null){
            return addressItem.toModel();
        }
        return null;
    }

    public List<Address> getSellersByLatLong(Double radius, Double lat, Double longi){
        List<AddressItem> list = addressItemRepository.findAll();
        if(list!=null){
            List<Address> l = new ArrayList<>();
            for (AddressItem a : list) {
                if (a.doCalculateEuclidianDistance(lat,longi) > radius) {
                    continue;
                }
                l.add(a.toModel());
            }
            return l;
        }
        return null;
    }

    public void uploadAddresses(){
        List<AddressItem> addresses = addressItemRepository.findAll();
        if(addresses.isEmpty()){
            loadAddressFromFile();
        }
    }

    private void loadAddressFromFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File f = new File(AddressItem.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            f = f.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
            Map<?, ?> json = mapper.readValue(Paths.get(f.getPath() + "\\apoio\\enderecos_geolocalizacao.json").toFile(), Map.class);
            if(json.get("mapa") instanceof  List){
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
                    addressItemRepository.save(addressItem);
                }
            }
            logger.info("Endereços foram carregados a partir do arquivo de geolocalização.");
            addressItemRepository.flush();
        } catch (Exception ex) {
            throw new EmaServerException(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }



}
