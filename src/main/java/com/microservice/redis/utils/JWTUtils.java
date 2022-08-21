package com.microservice.redis.utils;

import com.microservice.redis.dao.ClientDAO;
import com.microservice.redis.dao.DriverDAO;
import com.microservice.redis.dao.LocationClient;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class JWTUtils {

    public static JSONObject decoded(String JWTEncoded) throws Exception {
        JSONObject s1 = null;
        try {
            String[] split = JWTEncoded.split("\\.");
            s1 = new JSONObject(getJson(split[1]));
        } catch (UnsupportedEncodingException e) {
            //Error
            e.printStackTrace();
        }
        return s1;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
       return new String(Base64.getUrlDecoder().decode(strEncoded));
    }

    public static DriverDAO parseTokenToGetDriver(String token, Double longitude, Double latitude){
        DriverDAO driver = null;
        try {
            JSONObject s = JWTUtils.decoded(token);
            String userId = s.getString("userId");
            String name = s.getString("userName");
            int type = s.getInt("type");
            String vehiclePlate = s.getString("vehicle_plate");
            driver = new DriverDAO(userId, name, vehiclePlate, type, longitude, latitude);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driver;
    }


    public static ClientDAO parseTokenToGetClient(String token, LocationClient locationClient, Double price){
        ClientDAO clientDAO = null;
        try {
            JSONObject s = JWTUtils.decoded(token);
            String userId = s.getString("userId");
            String name = s.getString("userName");
            clientDAO = new ClientDAO(userId, name, locationClient, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientDAO;
    }
}