package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.repository.item.WalletItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {

    @Query("select w from WalletItem w where w.uuid = ?1")
    public WalletItem getByUuid(String uuid);
}
