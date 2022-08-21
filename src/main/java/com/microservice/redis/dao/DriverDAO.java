package com.microservice.redis.dao;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverDAO {
    private String userId;
    private String name;
    private String vehiclePlate;
    private int typeOfVehicle;
    private Double longitude;
    private Double latitude;

}
