package br.com.ema.EmaServer.controller;

import br.com.ema.EmaServer.commons.i18n.Messages;
import br.com.ema.EmaServer.config.EmaServerException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ResourceLoader resourceLoader;

    private final static Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Operation(summary = "Recupera os arquivos PNG do sistema.")
    @GetMapping(
            value = "/",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getFile(@Parameter(description="Nome do arquivo", example = "horse")  @RequestParam("filename") String filename) {
        logger.info("Recuperando arquivo do diretório file={};", filename);
        String path = "/subdirectory/subdirectory/" + filename + ".png";
        try {
            Resource resource = resourceLoader.getResource("classpath:/static" + path);
            InputStream in = resource.getInputStream();
            if (in != null){
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = null;
                try {
                    resources = resolver.getResources("classpath:/static/**");
                    for (Resource r : resources) {
                        if(r.getURI().equals(resource.getURI())){
                            return in.readAllBytes();
                        }
                    }
                    throw new EmaServerException(Messages.PATH_TRAVERSAL_OVER_SCOPE, HttpStatus.FORBIDDEN);
                } catch (IOException e) {
                    throw new EmaServerException(Messages.INVALID_PATH, HttpStatus.FORBIDDEN);
                }
            }
            return in.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new EmaServerException(Messages.INVALID_PATH, HttpStatus.FORBIDDEN);
        } catch (IOException e) {
            throw new EmaServerException(Messages.INVALID_PATH, HttpStatus.FORBIDDEN);
        }
    }

}
