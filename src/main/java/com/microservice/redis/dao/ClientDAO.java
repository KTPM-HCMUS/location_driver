package com.microservice.redis.dao;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDAO {
    private String phoneNumber; // userID
    private String name;
    private LocationClient locationClient;
    private Double price;
}
