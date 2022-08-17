package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.model.UserProfile;

import java.util.Set;

public interface ScopeService {
    Set<String> getScopesFromUser(User user);
}
