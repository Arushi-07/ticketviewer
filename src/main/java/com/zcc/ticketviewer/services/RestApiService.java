package com.zcc.ticketviewer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zcc.ticketviewer.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

@Log4j2
@Component
public class RestApiService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    /**
     *
     * @param requesturl Rest endpoint to hit
     * @param accountId account ID for basic authorization
     * @param password password for basic authorization
     * @return the response of the API
     * @throws ApiException
     */
    public String getResponse(final String requesturl, final String accountId, final String password) throws ApiException {
            String err = "";
            try{
                ResponseEntity<Object> response
                        = restTemplate.exchange(new URI(requesturl), HttpMethod.GET, new HttpEntity(createHeaders(accountId, password)),Object.class);
                return objectMapper.writeValueAsString(response.getBody());
            } catch(HttpClientErrorException | HttpServerErrorException ex ){
                throw new ApiException("Http Exceptions", ex.getStatusCode().value());

            } catch( ResourceAccessException ex){
                throw new ApiException("Resource Success Exception", 500);
            } catch (URISyntaxException e) {
                throw new ApiException("Unable to generate URI", 500);
            } catch (IOException e) {
                throw new ApiException("Unable to parese response", 500);
            }
    }

    /**
     * Create headers for basic Authorization
     * @param accountId
     * @param password
     * @return HttpHeaders
     */
    public HttpHeaders createHeaders(String accountId, String password){
        return new HttpHeaders() {{
            String encoding = null;
            try {
                encoding = Base64.getEncoder().encodeToString((accountId+":"+password).getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                encoding = "";
            }
            String authHeader = "Basic " + encoding;
            set( "Authorization", authHeader );
        }};
    }
}
