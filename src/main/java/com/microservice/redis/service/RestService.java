package com.microservice.redis.service;

import com.google.gson.Gson;
import com.microservice.redis.dao.BookingItem;
import com.microservice.redis.dao.BookingList;
import com.microservice.redis.dao.ResponseItem;
import com.microservice.redis.dao.Result;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.naming.spi.DirObjectFactory;
import java.util.ArrayList;
import java.util.List;

public class RestService {
    private RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public RestService(){

    }

    public BookingList getAll(String token){
        String url = "http://34.172.47.16:8080/v1/history";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = null;
        BookingList bookingList = null;
        try {
            response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            Gson gson = new Gson();
            ResponseItem result = gson.fromJson(response.getBody(), ResponseItem.class);
            bookingList = new BookingList();
            for(int i = 0; result.getResult().size() > i; i++){
                try {
                    BookingItem bookingItem = gson.fromJson(result.getResult().get(i).getMessage(), BookingItem.class);
                    bookingList.getBookingItems().add(bookingItem);
                }catch (Exception e){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookingList;
    }

    public List<Double> getTotal(String token){
        String url = "http://34.172.47.16:8080/v1/history";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = null;
        List<Double> list = new ArrayList<>();
        double totalPrice = 0;
        double size = 0;
        try {
            response = this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            Gson gson = new Gson();
            ResponseItem result = gson.fromJson(response.getBody(), ResponseItem.class);
            size = result.getResult().size();
            for(int i = 0; result.getResult().size() > i; i++){
                try {
                    BookingItem bookingItem = gson.fromJson(result.getResult().get(i).getMessage(), BookingItem.class);
                    double price = Double.parseDouble(bookingItem.getPrice());
                    totalPrice += price;
                }catch (Exception e){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        list.add(totalPrice);
        list.add(size);
        return list;
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
