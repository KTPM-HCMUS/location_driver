package com.microservice.redis.dao;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessagePublish {
    private String userId;
    private String message;
    private long timestamp;
}
