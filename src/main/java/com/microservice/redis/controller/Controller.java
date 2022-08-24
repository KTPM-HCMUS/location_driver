package com.microservice.redis.controller;

import com.microservice.redis.dao.*;
import com.microservice.redis.respository.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/location")
public class Controller {
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

    @PostMapping("/booking")
    public ResponseEntity<Message> saveBooking(@RequestBody BookingItem bookingRequest){
        return locationDAO.saveBooking(bookingRequest);
    }

    @GetMapping("/history/all/{token}")
    public ResponseEntity<BookingList> getAllBooking(@PathVariable String token){
        return locationDAO.getAllBooking(token);
    }

    @GetMapping("/total/{token}")
    public ResponseEntity<List<Double>> getTotal(@PathVariable String token){
        return locationDAO.getTotal(token);
    }

}
