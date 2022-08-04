package com.microservice.redis.respository;

import com.microservice.redis.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class LocationDAO {
    public static final String HASH_KEY = "Location";

    @Autowired()
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public Location save(Location location){
        template.opsForHash().put(HASH_KEY, location.getDriverID(), location);
        return location;
    }

    public List<Location> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public Location findLocationByID(int id){
        return (Location) template.opsForHash().get(HASH_KEY, "Location::" + id);
    }

    public String deleteLocation(int id){
        template.opsForHash().delete(HASH_KEY, id);
        return "location removed!";
    }
}
