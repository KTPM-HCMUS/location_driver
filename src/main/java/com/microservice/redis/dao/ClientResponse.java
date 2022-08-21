package com.microservice.redis.dao;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientResponse {
    private String phoneNumber; // userID
    private String name;
    private LocationClient locationClient;
    private Double price;
}
