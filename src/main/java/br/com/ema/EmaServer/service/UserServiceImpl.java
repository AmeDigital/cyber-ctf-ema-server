package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public User findByUuid(String userUuid) {
        return userRepository.getByUuid(userUuid);
    }

    @Override
    public List<User> list(int page, int size) {
        return userRepository.list(page, size);
    }

}
