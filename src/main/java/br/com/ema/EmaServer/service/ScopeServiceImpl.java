package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.model.Scope;
import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ScopeServiceImpl implements  ScopeService{

    @Override
    public Set<String> getScopesFromUser(User user) {
        Set<String> scopes = new HashSet<>();
        scopes.add(Scope.LOGIN.getValue());
        scopes.add(Scope.LOGOUT.getValue());
        scopes.add(Scope.READ_USER.getValue());
        scopes.add(Scope.GET_WALLET_ORDERS.getValue().replace("{walletId}", user.getWallet().getUuid()));
        scopes.add(Scope.CREATE_ORDER.getValue().replace("{userId}", user.getUuid()));
        scopes.add(Scope.GET_USER_INFO.getValue());
        return scopes;
    }
}
