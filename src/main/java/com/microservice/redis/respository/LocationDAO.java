package com.microservice.redis.respository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.redis.dao.*;
import com.microservice.redis.utils.JWTUtils;
import com.microservice.redis.utils.RedisPubSub;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class LocationDAO {
    // set time out for each location
    public static final String HASH_KEY_1 = "Location_1";

    // using geo redis to calculate distance
    public static final String HASH_KEY_2 = "Location_2";

    //using database to store request client
    public static final String HASH_KEY_REQUEST_CLIENT = "REQUEST_CLIENT";

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RedisPubSub redisPubSub;

    public ClientResponse save(DriverRequest location){
        ClientResponse clientDAO = null;
        RMapCache<String, DriverRequest> map = redissonClient.getMapCache(HASH_KEY_1);
        RGeo<String> geo = redissonClient.getGeo(HASH_KEY_2);
        RMap<String, ClientRequest> map1 = redissonClient.getMap(HASH_KEY_REQUEST_CLIENT);
        geo.add(new GeoEntry(location.getLongitude(), location.getLatitude(), location.getDriverID()));
        map.put(location.getDriverID(), location, 600, TimeUnit.SECONDS);
        if(!map1.isEmpty()){
            boolean s = map1.readAllKeySet().stream().anyMatch(x -> x.equals(location.getDriverID()));
            if(s){
                ClientRequest x = map1.get(location.getDriverID());
                clientDAO = JWTUtils.parseTokenToGetClient(x.getToken(), new LocationClient(
                        x.getLongitudeDepart(),
                        x.getLatitudeDepart(),
                        x.getLongitudeDestination(),
                        x.getLatitudeDestination(),
                        x.getAddressDepart(),
                        x.getAddressDestination()),
                        x.getPrice());
                map1.remove(location.getDriverID());
            }
        }
        return clientDAO;
    }


    public DriverRequest getDriverNearest(ClientRequest clientRequest){
        RGeo<Object> geo = redissonClient.getGeo(HASH_KEY_2);
        List<Object> geoList =  geo.radius(clientRequest.getLongitudeDepart(), clientRequest.getLatitudeDepart(), 2, GeoUnit.KILOMETERS, GeoOrder.ASC);
        Optional<Object> id = Optional.of(getDriverIDIsOnline()
                .values()
                .stream()
                .filter(x -> (geoList.contains(x.getDriverID()))&&
                        (x.getStatus().equals("NOT_MATCH"))&&
                        (x.getTypeOfVehicle()== clientRequest.getTypeOfVehicle()))
                .findFirst());
        Optional<Object> driver = (Optional<Object>) id.get();
        if(!driver.isPresent()){
            return null;
        }
        return (DriverRequest) driver.get();
    }

    public ArrayList<DriverRequest> getAllDriverIsOnline(){
        RMapCache<String, DriverRequest> s = redissonClient.getMapCache(HASH_KEY_1);
        return (ArrayList<DriverRequest>) s.readAllValues();
    }

    public ResponseEntity<Object> getDriverFromUser(ClientRequest clientRequest){
        //authentication

        // write into DB
        RMap<String, ClientRequest> map = redissonClient.getMap(HASH_KEY_REQUEST_CLIENT);
        //process
        DriverRequest driverRequest = getDriverNearest(clientRequest);
        DriverResponse driverDAO = null;
        if(driverRequest!=null){
            driverDAO = JWTUtils.parseTokenToGetDriver(driverRequest.getToken(), driverRequest.getLongitude(), driverRequest.getLatitude());
            map.putIfAbsent(driverRequest.getDriverID(), clientRequest);
            return new ResponseEntity<>(driverDAO, HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("NOT FOUND"), HttpStatus.OK);
    }

    private RMap<Integer, DriverRequest> getDriverIDIsOnline(){
        return redissonClient.getMapCache(HASH_KEY_1);
    }

    public ResponseEntity<Message> saveBooking(BookingRequest bookingRequest){
        ObjectMapper objectMapper = new ObjectMapper();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String jsonMessage = null;
        String jsonBooking = null;
        try {
            jsonBooking = objectMapper.writeValueAsString(bookingRequest);
            MessagePublish messagePublish = new MessagePublish(bookingRequest.getPhoneNumber(), jsonBooking, timestamp.getTime());
            jsonMessage = objectMapper.writeValueAsString(messagePublish);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(jsonBooking!=null&&jsonMessage!=null){
            redisPubSub.publish("user_history", jsonMessage);
            return new ResponseEntity<>(new Message("SUCCESS"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("FAILURE"), HttpStatus.OK);

    }

}
