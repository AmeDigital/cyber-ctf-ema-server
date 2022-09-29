package br.com.ema.EmaServer.config;

import br.com.ema.EmaServer.commons.i18n.Messages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler({EmaServerException.class})
    public ResponseEntity<EmaAppError> defaultEmaError(EmaServerException e) {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        EmaAppError error = new EmaAppError();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(Messages.UNDEFINED_ERROR);
        error.setMessage(e.getMessage());
        error.setStatus(e.getStatus());
        logger.error("AmeServer[ERROR]: ${}", e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<EmaAppError> handleNullPointerExceptions(Exception e) {
        EmaAppError error = new EmaAppError();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setMessage(Messages.NOT_FOUND_OBJECT);
        logger.error("AmeServer[ERROR]: ${} ${}", e.getClass().getName(),e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<EmaAppError> handleExceptions(Throwable e) {
        EmaAppError error = new EmaAppError();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        error.setMessage(Messages.UNDEFINED_ERROR);
        logger.error("AmeServer[ERROR]: ${}", e.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        EmaAppError error = new EmaAppError();
        error.setStatus(status);
        error.setMessage(Messages.UNDEFINED_ERROR);
        if (ex instanceof MissingRequestHeaderException) {
            error.setMessage(ex.getMessage());
        }
        logger.error("AmeServer[ERROR]: ${}", ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }

    @Override
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        EmaAppError error = new EmaAppError();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setMessage(Messages.NOT_FOUND_OBJECT);
        logger.error("AmeServer[ERROR]: ${}", ex.getMessage());
        return new ResponseEntity<>(error, error.getStatus());
    }


}