package com.microservice.redis.dao;

import com.microservice.redis.dao.LocationClient;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientRequest implements Serializable {
    private String token;
    private Double longitudeDepart;
    private Double latitudeDepart;
    private Double longitudeDestination;
    private Double latitudeDestination;
    private String addressDepart;
    private String addressDestination;
    private int typeOfVehicle;
    private Double price;

    public ClientRequest(double longitude, double latitude, int typeOfVehicle){
        this.longitudeDepart = longitude;
        this.latitudeDepart = latitude;
        this.typeOfVehicle = typeOfVehicle;
    }
}
