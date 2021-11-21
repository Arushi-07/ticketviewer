package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.pojo.Secrets;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
                secrets = objectMapper.readValue(new File("src/main/resources/secrets.json"), Secrets.class);
            } catch (final JsonProcessingException e) {
                log.error("Secrets json format not correct");
                e.printStackTrace();
            } catch (final IOException e) {
                log.error("Secrets json format not correct 2 " );
                e.printStackTrace();
            }

        }
        return secrets;
    }
}
