package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.User;

import java.util.List;

public interface UserService {

    public User get(Long id);
    public User getByUsername(String username);
    User findByUuid(String userUuid);
    public List<User> list(int page, int size);

}
