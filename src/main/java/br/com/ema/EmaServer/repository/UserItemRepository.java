package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.repository.item.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    @Query("select u from UserItem u where u.name = ?1 and u.password = ?2")
    public UserItem getByUsernameAndPassword(String username, String hashedPassword);

    @Query("select u from UserItem u where u.name = ?1")
    public UserItem getByUsername(String username);

    @Query("select u from UserItem u where u.uuid = ?1")
    public UserItem getByUuid(String uuid);

    @Query("select new br.com.ema.EmaServer.repository.item.UserItem(name, password) from UserItem u where u.name = ?1")
    public UserItem getUsernameAndPassword(String username);
}
