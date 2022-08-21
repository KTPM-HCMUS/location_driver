package com.microservice.redis;

import com.microservice.redis.dao.ClientResponse;
import com.microservice.redis.dao.ClientRequest;
import com.microservice.redis.dao.DriverRequest;
import com.microservice.redis.respository.LocationDAO;
import com.microservice.redis.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/location")
public class SpringRedisApplication extends SpringBootServletInitializer {
    @Autowired
    private LocationDAO locationDAO;

    @PostMapping
    public ClientResponse save(@RequestBody DriverRequest location){
        return locationDAO.save(location);
    }

    @GetMapping("/all")
    public ArrayList<DriverRequest> getAllMembers(){
        return locationDAO.getAllDriverIsOnline();
    }


    @PostMapping("/client")
    public ResponseEntity<Object> getDriverFromUser(@RequestBody ClientRequest clientRequest){
         return locationDAO.getDriverFromUser(clientRequest);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SpringRedisApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }
}
