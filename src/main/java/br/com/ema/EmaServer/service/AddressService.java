package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Address;

import java.util.List;

public interface AddressService {

    Address getSellerByCep(String cep);
    List<Address> getSellersByLatLong(Double radius, Double lat, Double longi);

}
