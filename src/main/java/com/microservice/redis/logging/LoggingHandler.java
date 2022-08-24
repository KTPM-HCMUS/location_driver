package com.microservice.redis.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.redis.dao.LogRequest;
import com.microservice.redis.utils.RedisPubSub;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Aspect
@Component
public class LoggingHandler {
    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisPubSub redisPubSub;

    LogRequest logRequest = new LogRequest();

    @Pointcut("execution(* com.microservice.redis.controller.*.* (..))")
    public void controller(){
    }

    @Before("controller() && args(yourString,..)")
    public void before(JoinPoint joinPoint, String yourString){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        logRequest.setRequestDate(now.format(dtf));
        logRequest.setUri(request.getRequestURI());
        logRequest.setMethod(request.getMethod());
        logRequest.setRequestData(yourString);
        logRequest.setRequestId(request.getRemoteHost());
        logRequest.setId(UUID.randomUUID().toString());
    }
    @Around("controller()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        Long startTime = System.currentTimeMillis();
        Object s = joinPoint.proceed();
        Long timeTaken = System.currentTimeMillis() - startTime;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        logRequest.setProcessTime(timeTaken);
        logRequest.setResponseDate(now.format(dtf));
        logRequest.setResponseData(s.toString());

        redisPubSub.publish("log", objectMapper.writeValueAsString(objectMapper));
        return s;
    }


}