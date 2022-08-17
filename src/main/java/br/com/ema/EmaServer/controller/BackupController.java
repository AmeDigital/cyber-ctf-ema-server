package br.com.ema.EmaServer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class BackupController {

    private final static Logger logger = LoggerFactory.getLogger(BackupController.class);

    @GetMapping("/backup")
    @ResponseBody
    public ModelAndView redirect() {
        logger.info("Acesso ao diretório de backup");
        return new ModelAndView("redirect:/backup/index.html");
    }

    @GetMapping("/admin_utils")
    @ResponseBody
    public ModelAndView redirectAdmin() {
        logger.info("Acesso ao diretório admin_utils");
        return new ModelAndView("redirect:/admin_utils/index.html");
    }

}
