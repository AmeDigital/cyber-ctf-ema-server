package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.User;
import br.com.ema.EmaServer.service.AuthService;
import br.com.ema.EmaServer.service.UserService;
import br.com.ema.EmaServer.util.LinkUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController extends  AbstractController{


    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Operation(hidden = true, summary = "Busca todos os usuários cadastrados na plataforma.")
    @ApiResponse(responseCode = "200", description = "Lista dos usuários")
    @GetMapping("/list")
    public List<User> list(@RequestParam("page")Optional<Integer> optPage, @RequestParam("size") Optional<Integer> optSize, HttpServletRequest request, HttpServletResponse response){
        int page = 0;
        int size = DEFAULT_SIZE;
        if (optPage.isPresent()){
            if (optPage.get()>0){
                page = optPage.get();
            }
        }
        if (optSize.isPresent()){
            if (optSize.get()<DEFAULT_SIZE && optSize.get()>0){
                size = optSize.get();
            }
        }
        String uriFirstPage = this.constructFirstPageUri(request, size);
        String headerFirstPage = LinkUtil.createLinkHeader(uriFirstPage, LinkUtil.REL_FIRST);
        response.addHeader("Link",headerFirstPage);
        return userService.list(page, size);
    }

    @Operation(summary = "API recuperar as informações de um usuário",
            description = "FLAG#1_{H0w_N1c3_70_H4v3_4n_4p1_D0cum3n74710n}",
            security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/{userUuid}/info")
    public User getInfo(@PathVariable("userUuid") String userUuid) {
        logger.info("Procurando por usuário com user_uuid={};", userUuid);
        User user = this.userService.findByUuid(userUuid);
        return user.toModelHash();
    }

    @GetMapping("/info")
    public User getInfoLogged(
            @Parameter(description = "Recupera informação do usuário logado.")
            @RequestHeader(name="Authorization") String token) throws EmaServerException {
        if (token==null){
            throw new EmaServerException(Messages.AUTHORIZATION_NOT_NULL_OR_EMPTY, HttpStatus.FORBIDDEN);
        }else{
            String userUuidFromJwt = authService.getUserUuidFromJwt(token);
            User user = this.userService.findByUuid(userUuidFromJwt);
            return user;
        }
    }

    public User getInfo(
            @Parameter(description = "UUID do usuário a ser procurado")
            @PathVariable("userUuid") String userUuid,
            @RequestHeader(name="Authorization") String token) throws EmaServerException {
        if (token==null){
            throw new EmaServerException(Messages.AUTHORIZATION_NOT_NULL_OR_EMPTY, HttpStatus.FORBIDDEN);
        }else{
            String userUuidFromJwt = authService.getUserUuidFromJwt(token);
            if (userUuidFromJwt!=null && userUuidFromJwt.equals(userUuid)){
                logger.info("Usuário recuperado != token - uuid no parametro {} - uuid from jwt {}", userUuid, userUuidFromJwt);
            }
            User user = this.userService.findByUuid(userUuid);
            return user;
        }
    }

}
