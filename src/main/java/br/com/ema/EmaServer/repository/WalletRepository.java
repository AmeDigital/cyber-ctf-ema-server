package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.repository.item.UserItem;
import br.com.ema.EmaServer.repository.item.WalletItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WalletRepository {

    @Autowired
    private WalletItemRepository walletItemRepository;

    public Wallet findByUuid(String walletUuid) {
        WalletItem walletItem = walletItemRepository.getByUuid(walletUuid);
        if(walletItem!=null){
            Wallet wallet = walletItem.toModel(null);
            wallet.setOwner(walletItem.getOwner().toModel());
            return wallet;
        }
        return null;
    }

    public List<Wallet> list(int page, int size) {
        Page<WalletItem> pageWallet = walletItemRepository.findAll(PageRequest.of(page, size));
        List<WalletItem> list = pageWallet.get().collect(Collectors.toList());
        if(list!=null){
            List<Wallet> l = new ArrayList<>();
            list.stream().forEach(w ->
            {
                Wallet wallet = w.toModel(null);
                wallet.setOwner(w.getOwner().toModel());
                l.add(wallet);
            }
            );
            return l;
        }
        return null;
    }
}
