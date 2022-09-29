package br.com.ema.EmaServer.config;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CepNotFoundException extends RuntimeException {
    public CepNotFoundException(String s) {
        super(s);
    }
}
