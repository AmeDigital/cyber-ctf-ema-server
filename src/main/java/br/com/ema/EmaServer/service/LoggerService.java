package br.com.ema.EmaServer.service;

import java.io.IOException;

public interface LoggerService {

    StringBuffer doRequest(String logMe) throws IOException;

    String viewFlag() throws IOException;
}
