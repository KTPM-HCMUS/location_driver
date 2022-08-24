package com.microservice.redis.dao;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessagePublish implements Serializable {
    private String user_id;
    private String message;
    private long timestamp;
}
