package com.microservice.redis;

import com.microservice.redis.entity.Location;
import com.microservice.redis.respository.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/location")
public class SpringRedisApplication {
    @Autowired
    private LocationDAO locationDAO;

    @PostMapping
    @Cacheable(value = "Location",key="#location.getDriverID().toString()", cacheManager = "cacheManager")
    public Location save(@RequestBody Location location){
        return locationDAO.save(location);
    }

    @GetMapping
    public List<Location> getAllLocations(){
        return locationDAO.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "Location",key="#id.toString()", cacheManager = "cacheManager")
    public Location findLocation(@PathVariable int id){
        return locationDAO.findLocationByID(id);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id){
        return locationDAO.deleteLocation(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }
}
