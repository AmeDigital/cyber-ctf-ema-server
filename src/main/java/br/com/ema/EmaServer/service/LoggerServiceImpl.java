package br.com.ema.EmaServer.service;

import br.com.ema.EmaServer.controller.ImageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LoggerServiceImpl implements LoggerService{

    private final static Logger logger = LoggerFactory.getLogger(LoggerServiceImpl.class);

    @Override
    public StringBuffer doRequest(String logMe) throws IOException {
        URL url = new URL("http://127.0.0.1:8082/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();


        con.setRequestMethod("GET");
        con.setRequestProperty("Accept","*/*");
        con.setRequestProperty("X-LogMe", logMe);


        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        return content;

    }

    @Override
    public String viewFlag() throws IOException {
        String str = "Refresh page, please !";
        try {
            Path flag = Path.of("/home/ubuntu/flag.txt");
            str = Files.readString(flag);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return str;
    }
}