package com.microservice.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "Location")
public class Location implements Serializable {
    @Id
    private int driverID;
    private double longitude;
    private double latitude;

    public Location(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
