package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Address;

import java.util.List;

public interface AddressService {

    public Address getSellerByCep(String cep);
    public List<Address> getSellersByLatLong(Double radius, Double lat, Double longi);

}
