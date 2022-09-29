package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.repository.item.AddressItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressItemRepository extends JpaRepository<AddressItem, Long> {

    @Query("select a from AddressItem a where a.id = ?1")
    AddressItem getid(Long id);

    @Query("select a from AddressItem a where a.cep = ?1")
    AddressItem getByCep(String cep);

}
