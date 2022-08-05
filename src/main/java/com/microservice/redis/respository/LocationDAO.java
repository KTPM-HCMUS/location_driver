package com.microservice.redis.respository;

import com.microservice.redis.entity.Location;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class LocationDAO {
    public static final String HASH_KEY_1 = "Location_1";
    public static final String HASH_KEY_2 = "Location_2";

    @Autowired
    RedissonClient redissonClient;

    public Location save(Location location){
        redissonClient.getMapCache(HASH_KEY_1).put(location.getDriverID(), new Location(location.getLongitude(), location.getLatitude()), 60, TimeUnit.SECONDS);
        RGeo<String> geo = redissonClient.getGeo(HASH_KEY_2);
        geo.add(new GeoEntry(location.getLongitude(), location.getLatitude(), location.getDriverID()));
        return location;
    }


    public String getDriverNearest(Location clientLocation){
        RGeo<Object> geo = redissonClient.getGeo(HASH_KEY_2);
        List<Object> geoList =  geo.radius(clientLocation.getLongitude(), clientLocation.getLatitude(), 2, GeoUnit.KILOMETERS, GeoOrder.ASC);
        Set<Object> allDriverIsOnline = getAllDriverIsOnline();

        Optional<Object> id = geoList.stream().filter(x -> allDriverIsOnline.contains(x)).findFirst();
        if(!id.isPresent()){
            return "Don't have driver near you!";
        }
        return id.get().toString();
    }

    public Set<Object> getAllDriverIsOnline(){
        return  redissonClient.getMapCache(HASH_KEY_1).keySet();
    }

}
