package com.example.demo.Redis;


import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.DTO.IdempotencyRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;

@Service
public class RedisIdempotencyService {

    private static final Duration ttl=Duration.ofMinutes(10);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public IdempotencyRecordDTO getIdempotencyRecord(String key) {
        String value=(String) redisTemplate.opsForValue().get(key);
        if(value==null){
            return null;
        }
        try{
            return objectMapper.readValue(value, IdempotencyRecordDTO.class);
        }catch(CustomException e){
            throw new CustomException("failed to get from redis!!!");
        }
    }
    public void save(String key, IdempotencyRecordDTO dto) {
        try{
            String value=objectMapper.writeValueAsString(dto);
            redisTemplate.opsForValue().set(key,value,ttl);
        }catch(CustomException e){
            throw new CustomException("Failed to save Idempotency record"+e.getMessage());
        }
    }
}
