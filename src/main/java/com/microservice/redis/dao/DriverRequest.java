package com.microservice.redis.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "Location")
public class DriverRequest implements Serializable {
    @Id
    private String driverID;
    private double longitude;
    private double latitude;
    private int typeOfVehicle;
    private String token;
    private String status;

    public DriverRequest(double longitude, double latitude, int typeOfVehicle){
        this.longitude = longitude;
        this.latitude = latitude;
        this.typeOfVehicle = typeOfVehicle;
    }
}
