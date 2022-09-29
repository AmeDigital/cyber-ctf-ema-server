package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Address;
import br.com.ema.EmaServer.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getSellerByCep(String cep){
        return this.addressRepository.getSellerByCep(cep);
    }

    @Override
    public List<Address> getSellersByLatLong(Double radius, Double lat, Double longi) {
        return this.addressRepository.getSellersByLatLong(radius, lat, longi);
    }
}
