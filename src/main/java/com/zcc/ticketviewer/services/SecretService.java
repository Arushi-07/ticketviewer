package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.pojo.Secrets;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Log4j2
@Component
@AllArgsConstructor
public class SecretService {
    private Secrets secrets;

    // Singleton design pattern
    public Secrets getSecrets(final String secretsPath){
        if(secrets == null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                secrets = objectMapper.readValue(new File(secretsPath), Secrets.class);
            } catch (final JsonParseException e) {
                log.error("Secrets json format not correct");
            } catch (final IOException e) {
                log.error("Secrets json format not correct 24 " );
            }

        }
        return secrets;
    }
}
