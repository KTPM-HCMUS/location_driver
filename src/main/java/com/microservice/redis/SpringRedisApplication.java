package com.microservice.redis;

import com.microservice.redis.dao.ClientDAO;
import com.microservice.redis.dao.ClientRequest;
import com.microservice.redis.dao.DriverRequest;
import com.microservice.redis.respository.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/location")
public class SpringRedisApplication extends SpringBootServletInitializer {
    @Autowired
    private LocationDAO locationDAO;

    @PostMapping
    public ClientDAO save(@RequestBody DriverRequest location){
        return locationDAO.save(location);
    }

//    @GetMapping
//    public Object getDriver(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude,
//                            @RequestParam("typeOfVehicle") int typeOfVehicle){
//        return locationDAO.getDriverNearest(new DriverRequest(longitude, latitude, typeOfVehicle));
//    }

    @GetMapping("/all")
    public ArrayList<DriverRequest> getAllMembers(){
        return locationDAO.getAllDriverIsOnline();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(SpringRedisApplication.class);
    }

    @PostMapping("/client")
    public ResponseEntity<Object> getDriverFromUser(@RequestBody ClientRequest clientRequest){
         return locationDAO.getDriverFromUser(clientRequest);
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisApplication.class, args);
    }
}
