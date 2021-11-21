package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.pojo.Secrets;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
@NoArgsConstructor
@AllArgsConstructor
public class SecretService {
    private Secrets secrets;

    // Singleton design pattern
    public Secrets getSecrets(){
        if(secrets == null){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                secrets = objectMapper.readValue("", Secrets.class);
            } catch (final JsonProcessingException e) {
                log.error("Secrets json format not correct");
            } catch (final IOException e) {
                log.error("Secrets json format not correct");
            }
        }
        return secrets;
    }
}
