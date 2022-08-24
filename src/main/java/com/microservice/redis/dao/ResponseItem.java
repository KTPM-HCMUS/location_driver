package com.microservice.redis.dao;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseItem implements Serializable {
    private Result responseInfo;
    private List<MessagePublish> result;
}
