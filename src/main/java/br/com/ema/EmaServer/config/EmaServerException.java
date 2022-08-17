package br.com.ema.EmaServer.config;

import org.springframework.http.HttpStatus;

public class EmaServerException extends RuntimeException{

    private HttpStatus status;

    public int getStatusError() {
        return status.value();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public EmaServerException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public EmaServerException(String message){
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
