package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.Credential;
import br.com.ema.EmaServer.model.RefreshToken;
import br.com.ema.EmaServer.model.Token;
import br.com.ema.EmaServer.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final String GRANT_TYPE_CLIENT_CREDENTIAL = "client_credential";
    private final String GRANT_TYPE_PASSWORD = "password";
    private final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Get Access Token from User",
            description = "Rest endpoint that returns access token of a certain user")
    @ApiResponses(value = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
    })
    @RequestMapping(value = "/token", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Token authenticate(@RequestBody Credential credential) throws Exception {
        doAuthentication(credential.getUsername(), credential.getPassword());
        final UserDetails userDetails = authService.loadUserByUsername(credential.getUsername());
        logger.info("Gerando token de autenticacao para o username={};", credential.getUsername());
        return authService.generateAmeToken(userDetails);
    }

    @Operation(summary = "Get new access_token and refresh_token.",
            description = "Access token and refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class))),
    })
    @RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public Token refreshToken(@RequestBody RefreshToken refreshToken) {
        Token token = authService.refreshAmeToken(refreshToken);
        token.setFLAG("FLAG#3_{H4v1n6_4_R3fr35h_70k3n_W1ll_R34lly_C0m3_1n_H4ndy}");
        return token;
    }

    private void doAuthentication(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            logger.info("Falha ao tentar autenticar o username={};",username);
            throw new EmaServerException(Messages.INVALID_CREDENTIAL);
        } catch (AuthenticationException e) {
            throw new EmaServerException(Messages.NOT_FOUND_OBJECT);
        }
    }
}
