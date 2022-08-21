package com.microservice.redis.utils;

import java.nio.charset.StandardCharsets;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class  RedisPubSub {
  private final RedissonClient client;

  @Autowired
  public RedisPubSub(RedissonClient client) {
    this.client = client;
  }

  public  Long publish(String topic, byte[] message) {
    return client.getTopic(topic).publish(message);
  }

  public  Long publish(String topic, String message) {
    return publish(topic, message.getBytes(StandardCharsets.UTF_8));
  }

  public Integer subscribe(String topic, MessageListener<byte[]> listener) {
    return client.getTopic(topic).addListener(byte[].class, listener);
  }

  public void unsubscribe(String topic, MessageListener<byte[]> listener) {
    client.getTopic(topic).removeListener(listener);
  }
}
