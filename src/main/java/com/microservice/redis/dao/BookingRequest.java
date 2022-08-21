package com.microservice.redis.dao;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingRequest {
    private String price;
    private String name;
    private String phoneNumber;
    private String addressDepart;
    private String addressDestination;
    private String timeStamp;
}
