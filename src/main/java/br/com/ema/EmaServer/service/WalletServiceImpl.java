package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements  WalletService{

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository){
        this.walletRepository = walletRepository;
    }


    @Override
    public Wallet findByUuid(String walletUuid) {
        Wallet wallet = walletRepository.findByUuid(walletUuid);

        return wallet;
    }

    @Override
    public List<Wallet> list(int page, int size) {
        return this.walletRepository.list(page,size);
    }
}
