package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.User;

import java.util.List;

public interface UserService {

    User get(Long id);
    User getByUsername(String username);
    User findByUuid(String userUuid);
    List<User> list(int page, int size);

}
