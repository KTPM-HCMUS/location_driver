package com.microservice.redis.service;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestService {
    private RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public RestService(){

    }


    public boolean authentication(String token){
        boolean isValid = true;
        String url = "http://34.121.234.226:8080/v1/revokeToken";
        ResponseEntity<String> response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);
        try {
             response = this.restTemplate.postForEntity(url, entity, String.class);
            if(response.getStatusCode() == HttpStatus.OK){
                isValid = true;
            }
        }catch (Exception e){
            isValid = false;
        }
        return isValid;
    }
}
