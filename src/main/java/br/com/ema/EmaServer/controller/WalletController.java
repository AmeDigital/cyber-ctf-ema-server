package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.model.Wallet;
import br.com.ema.EmaServer.service.AuthServiceImpl;
import br.com.ema.EmaServer.service.WalletService;
import br.com.ema.EmaServer.util.LinkUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/wallet")
public class WalletController extends  AbstractController{

    @Autowired
    public WalletService walletService;

    private static final Logger logger = LogManager.getLogger();

    @Operation(summary = "Informações de associadas a uma Wallet.")
    @GetMapping("/{walletUuid}/info")
    public Wallet getInfo(@PathVariable("walletUuid") String walletUuid, @RequestHeader(name="Authorization") String token){
        Wallet wallet = (this.walletService.findByUuid(walletUuid));
        String FLAG_ownerUuid = "3361";
        if(wallet == null){
            throw new EmaServerException(Messages.NOT_FOUND_OBJECT, HttpStatus.NOT_FOUND);
        } else if(!Objects.equals(walletUuid,FLAG_ownerUuid)){
            return wallet;
        } else {
            logger.info("Flag encontrada para a wallet_uuid={};",walletUuid);
            wallet.setOwnerName("Arthur.Morgan - FLAG#4_{Br0k3n_0bj3c7_L3v3l_4u7h0r1z4710n_15_4w350m3}");
            return wallet;
        }
    }

    @Operation(hidden = true)
    @GetMapping("/list")
    public List<Wallet> list(@RequestParam("page") Optional<Integer> optPage, @RequestParam("size") Optional<Integer> optSize, HttpServletRequest request, HttpServletResponse response){
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
        return walletService.list(page, size);
    }

    @Operation(hidden = true)
    @GetMapping("/status")
    public String getStatus(){
        return "API is working";
    }
}
