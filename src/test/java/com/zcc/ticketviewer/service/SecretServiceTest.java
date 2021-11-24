package com.zcc.ticketviewer.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.pojo.Secrets;
import com.zcc.ticketviewer.services.SecretService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SecretServiceTest {

    @InjectMocks
    SecretService secretService;

    private static final String secretsTestFile = "src/main/resources/secrets_test.json";

    @Test
    public void testSucess() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Secrets secrets = new Secrets("arushi", "hello");
        if(createFile(secretsTestFile, objectMapper.writeValueAsString(secrets))){
            final Secrets secretsCreated = secretService.getSecrets(secretsTestFile);
            assertEquals(secretsCreated.getAccountId(), secrets.getAccountId());
            assertEquals(secretsCreated.getPassword(), secrets.getPassword());
            if(!deleteFile(secretsTestFile)){
                System.out.println("not delete test secrets file");
            }
        }else {
            System.out.println("unable to create file");
        }
    }

    @Test
    public void testJsonParsingException() {
        if(createFile(secretsTestFile, "Hello World")){
            final Secrets secretsCreated = secretService.getSecrets(secretsTestFile);
            assertEquals(secretsCreated, null);
            if(!deleteFile(secretsTestFile)){
                System.out.println("not delete test secrets file");
            }
        }else {
            System.out.println("unable to create file");
        }
    }
    @Test
    public void testIOException() {
        final Secrets secretsCreated = secretService.getSecrets(secretsTestFile);
        assertEquals(secretsCreated, null);
    }

    @Test
    public void testSingleton() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Secrets secrets = new Secrets("arushi", "hello");
        if(createFile(secretsTestFile, objectMapper.writeValueAsString(secrets))){
            final Secrets secretsCreated = secretService.getSecrets(secretsTestFile);
            final Secrets secretsCreatedSecond = secretService.getSecrets(secretsTestFile);
            assertEquals(secretsCreated, secretsCreatedSecond);
            if(!deleteFile(secretsTestFile)){
                System.out.println("not delete test secrets file");
            }
        }else {
            System.out.println("unable to create file");
        }
    }

    public boolean createFile(final String filename, final String data) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(data);
            myWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFile(final String filename) {
        File myObj = new File(filename);
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
            return true;
        } else {
            System.out.println("Failed to delete the file.");
            return false;
        }
    }


}
