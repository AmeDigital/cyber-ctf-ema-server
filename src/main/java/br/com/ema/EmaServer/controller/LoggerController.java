package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import br.com.ema.EmaServer.config.GlobalControllerExceptionHandler;
import br.com.ema.EmaServer.service.LoggerService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/logger")
public class LoggerController {

    private final Logger logger = LogManager.getLogger(LoggerController.class);

    @Autowired
    private LoggerService loggerService;

    @Operation(hidden = true)
    @GetMapping(value={"", "/"})
    @ResponseBody
    public String getPath() {
        logger.info("Requisicao no 'logger/' realizada com sucesso.");
        return "";
    }


    @Operation(hidden = true)
    @GetMapping("/me")
    public ModelAndView logMe(
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="X-LogMe") String logMe) throws Exception {
        if (token == null) {
            throw new EmaServerException(Messages.AUTHORIZATION_NOT_NULL_OR_EMPTY, HttpStatus.FORBIDDEN);
        } else if (logMe == null) {
            throw new EmaServerException(Messages.HEADER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Requisicao no 'logger/me' realizada com sucesso.");
            loggerService.doRequest(logMe);
            TimeUnit.SECONDS.sleep(1);
            return new ModelAndView("forward:/logger/flag");
        }

    }

    @Operation(hidden = true)
    @GetMapping("/flag")
    public String viewFlag(@RequestHeader(name="Authorization") String token) throws Exception {
        if (token == null) {
            throw new EmaServerException(Messages.AUTHORIZATION_NOT_NULL_OR_EMPTY, HttpStatus.FORBIDDEN);
        } else {
            logger.info("Requisicao no 'logger/flag' realizada com sucesso.");
            return loggerService.viewFlag();
        }
    }


}
