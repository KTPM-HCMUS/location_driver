package com.microservice.redis.dao;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingItem implements Serializable {
    private String price;
    private String name;
    private String phoneNumber;
    private String addressDepart;
    private String addressDestination;
    private long timeStamp;
//    private String token;
    private String userId;
}
