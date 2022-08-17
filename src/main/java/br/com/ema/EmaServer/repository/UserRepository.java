package br.com.ema.EmaServer.repository;

import br.com.ema.EmaServer.model.*;
import br.com.ema.EmaServer.repository.item.OrderItem;
import br.com.ema.EmaServer.repository.item.UserItem;
import br.com.ema.EmaServer.repository.item.WalletItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRepository {

    @Autowired
    private UserItemRepository userItemRepository;

    public User get(Long id){
        UserItem userItem = this.userItemRepository.getById(id);
        if(userItem!=null){
            return userItem.toModel();
        }
        return null;
    }

    public User getByUuid(String uuid){
        UserItem userItem = this.userItemRepository.getByUuid(uuid);
        if(userItem!=null){
            return userItem.toModel();
        }
        return null;
    }

    public List<User> list(final int page, final int size){
        Page<UserItem> pageUserItem  = userItemRepository.findAll(PageRequest.of(page, size));
        List<UserItem> list = pageUserItem.get().collect(Collectors.toList());
        if(list!=null){
            List<User> l = new ArrayList<>();
            list.stream().forEach(w -> l.add(w.toModelHash()));
            return l;
        }
        return null;
    }

    public User getByCredential(Credential credential){
        UserItem userItem = this.userItemRepository.getByUsernameAndPassword(credential.getUsername(), credential.getPassword());
        if(userItem!=null){
            return userItem.toModel();
        }
        return null;
    }

    public User getByUsername(String username){
        UserItem userItem = this.userItemRepository.getByUsername(username);
        if(userItem!=null){
            return userItem.toModel();
        }
        return null;
    }

    public User loadUsernameAndPasswordByUsername(String username){
        UserItem userItem = this.userItemRepository.getUsernameAndPassword(username);
        if(userItem!=null){
            return userItem.toModel();
        }
        return null;
    }


}
