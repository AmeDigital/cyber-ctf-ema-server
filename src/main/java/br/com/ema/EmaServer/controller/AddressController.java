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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/address")
public class AddressController {

    private String API_ID = "ema";
    private String API_SECRET = "^#5f&9wFV$D4&Ll6D6AGmbXc5sU2A8Y!G%wPf00095l@T4BukSsHobWjWaGV2%XX6mkEVK";
    private String FLAG = "FLAG#3_{D0_N07_H4rd0c0d3_Y0ur_53cr375}";

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Operation(hidden = true)
    @PostMapping(value = "/info", produces = "application/json")
    public Map<String, Object> getFlagByCodeSecret(@RequestBody(required = true) Secret secret){
        HashMap<String, Object> map = new HashMap<String, Object>();
        if((Objects.equals(secret.getApi_id(), API_ID)) && (Objects.equals(secret.getApi_secret(), API_SECRET))){
            map.put("info",FLAG);
            return map;
        }
        return null;
    }

    @Operation(hidden = true)
    @GetMapping("/{cep}")
    @ResponseBody
    public Address getSellerByCep(@PathVariable("cep") String cep, @RequestParam(required = true) String api_id, @RequestParam(required = true) String api_secret) {
        if ((Objects.equals(api_id, API_ID)) && (Objects.equals(api_secret, API_SECRET))) {
            if (this.addressService.getSellerByCep(cep) != null) {
                return this.addressService.getSellerByCep(cep);
            } else {
                throw new EmaServerException(Messages.NOT_FOUND_OBJECT, HttpStatus.NOT_FOUND);
            }
        }

        Address address = new Address();
        return address;
    }

    @Operation(hidden = true)
    @GetMapping("/search/geolocalizacao")
    @ResponseBody
    public List<Address> getSellersByLatLong(@RequestParam(required = true) Double radius, @RequestParam(required = true) Double lat, @RequestParam(required = true) Double longi){
//        return "The given parameters are Radius: " + radius + " lat: " + lat + " long: " + longi;
        List<Address> SellersByLatLong = addressService.getSellersByLatLong(radius,lat,longi);
        return SellersByLatLong;
    }

    @Operation(hidden = true)
    @GetMapping("/status")
    public void doStartTheSellers(){
        AddressRepository addressRepository = new AddressRepository();
        this.addressRepository.uploadAddresses();
    }
}
