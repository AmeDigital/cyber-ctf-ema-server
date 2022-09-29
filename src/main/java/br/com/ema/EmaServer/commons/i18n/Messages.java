package br.com.ema.EmaServer.commons.i18n;


import br.com.ema.EmaServer.model.User;

public final class Messages {

    public static final String OPERATION_NOT_ALLOWED = "You don't have authorization to complete this operation.";
    public static final String AUTHORIZATION_NOT_NULL_OR_EMPTY = "The 'Authorization' must not be null or empty.";
    public static final String INVALID_PAYLOAD = "You are sending me an invalid payload.";
    public static final String INVALID_CREDENTIAL = "You are not sending me a valid credential.";
    public static final String TOKEN_INVALID_OR_EXPIRED = "This authorization is invalid or expired.";
    public static final String INVALID_PATH = "This file does not exist.";
    public static final String USER_DISABLED = "This user is disabled.";
    public static final String PATH_TRAVERSAL_OVER_SCOPE = "PathTraversal attack is almost there! Don't give up, but you are not allowed search into this path.";
    public static final String UNDEFINED_ERROR = "Error undefined";
    public static final String NOT_FOUND_OBJECT = "I was not able to find this object, sorry.";
    //public static final String UNDEFINED_ERROR = "This application uses Log4J version 2.14.1 to log information on header X-LogMe.";
    public static final String HEADER_NOT_FOUND = "This application uses Log4J version 2.14.1 to log information on header X-LogMe.";
}
