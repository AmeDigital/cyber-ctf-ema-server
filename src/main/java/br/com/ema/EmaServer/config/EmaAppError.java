package br.com.ema.EmaServer.config;

import org.springframework.http.HttpStatus;

public class EmaAppError {

    private String message;
    private HttpStatus status;

    public EmaAppError(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
