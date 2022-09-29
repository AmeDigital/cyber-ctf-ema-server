package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.CepNotFoundException;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.Address;
import br.com.ema.EmaServer.model.Secret;
import br.com.ema.EmaServer.repository.AddressItemRepository;
import br.com.ema.EmaServer.repository.AddressRepository;
import br.com.ema.EmaServer.repository.item.AddressItem;
import br.com.ema.EmaServer.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final String API_ID = "ema";

    @Value("${ema-server.flag3.api_secret}")
    private String API_SECRET;

    @Value("${ema-server.flag3.flag}")
    private String FLAG;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Operation(hidden = true)
    @PostMapping(value = "/info", produces = "application/json")
    public Map<String, Object> getFlagByCodeSecret(@RequestBody Secret secret){
        HashMap<String, Object> map = new HashMap<>();
        if((Objects.equals(secret.getApi_id(), API_ID)) && (Objects.equals(secret.getApi_secret(), API_SECRET))){
            map.put("info",FLAG);
            return map;
        }
        return null;
    }

    @Operation(hidden = true)
    @GetMapping("/{cep}")
    @ResponseBody
    public Address getSellerByCep(@PathVariable("cep") String cep, @RequestParam String api_id, @RequestParam String api_secret) {
        if ((Objects.equals(api_id, API_ID)) && (Objects.equals(api_secret, API_SECRET))) {
            if (this.addressService.getSellerByCep(cep) != null) {
                return this.addressService.getSellerByCep(cep);
            } else {
                throw new EmaServerException(Messages.NOT_FOUND_OBJECT, HttpStatus.NOT_FOUND);
            }
        }
        return new Address();
    }

    @Operation(hidden = true)
    @GetMapping("/search/geolocalizacao")
    @ResponseBody
    public List<Address> getSellersByLatLong(@RequestParam Double radius, @RequestParam Double lat, @RequestParam Double longi){
        return addressService.getSellersByLatLong(radius,lat,longi);
    }

    @Operation(hidden = true)
    @GetMapping("/status")
    public void doStartTheSellers(){
        AddressRepository addressRepository = new AddressRepository();
        this.addressRepository.uploadAddresses();
    }
}
