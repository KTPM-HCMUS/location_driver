package com.microservice.redis;

import com.microservice.redis.entity.Location;
import com.microservice.redis.respository.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/location")
public class SpringRedisApplication {
    @Autowired
    private LocationDAO locationDAO;

    @PostMapping
    public Location save(@RequestBody Location location){
        return locationDAO.save(location);
    }

    @GetMapping
    public Object getDriver(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude){
        return locationDAO.getDriverNearest(new Location(longitude, latitude));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }
}
