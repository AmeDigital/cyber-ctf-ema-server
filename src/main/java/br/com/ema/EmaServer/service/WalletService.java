package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Wallet;

import java.util.List;

public interface WalletService {

    Wallet findByUuid(String walletUuid);
    public List<Wallet> list(int page, int size);
}
