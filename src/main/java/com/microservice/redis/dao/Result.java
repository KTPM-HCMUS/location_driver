package com.microservice.redis.dao;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Result implements Serializable {
    private String event;
    private int errorCode;
    private String status;
}
