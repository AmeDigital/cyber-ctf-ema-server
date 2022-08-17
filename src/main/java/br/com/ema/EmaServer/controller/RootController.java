package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class RootController {

    private final static Logger logger = LoggerFactory.getLogger(RootController.class);

    @Operation(hidden = true)
    @GetMapping(value={"/robots.txt"})
    @ResponseBody
    public String getRobotsTxt() {
        logger.info("Acesso arquivo arquivo robotx.txt");
        return "User-agent: *\n" +
                "Disallow: /backup/\n" +
                "Disallow: /admin_utils/\n";
    }

    @Operation(hidden = true)
    @GetMapping(value={"/status"})
    @ResponseBody
    public String getStatus() {
        return "Service is on\n";
    }

//
    @Operation(hidden = true)
    @GetMapping(value={"/actuator"})
    @ResponseBody
    public String getActuator() {
        return "FLAG#10_{EXPOS1NG_INTERN@L_FE@TURE_AT_PR0DUCTI0N}";
    }

    @Operation(hidden = true)
    @PatchMapping(value={"/admin"})
    @ResponseBody
    public String getAdminpath() {
        return "FLAG#11_{EXPOS1NG_ADM1N_1NFORMAT10N}";
    }
}
