package com.microservice.redis.dao;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LocationClient {
    private Double longitudeDepart;
    private Double latitudeDepart;
    private Double longitudeDestination;
    private Double latitudeDestination;
    private String addressDepart;
    private String addressDestination;
}
