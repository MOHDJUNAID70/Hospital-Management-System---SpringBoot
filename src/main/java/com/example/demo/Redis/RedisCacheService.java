package com.example.demo.Redis;


import com.example.demo.ExceptionHandler.CustomException;
import com.example.demo.Model.DTO.DoctorAvailabilityDTO;
import com.example.demo.Model.Doctor;
import com.example.demo.Model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;

@Service
public class RedisCacheService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static final Duration ttl=Duration.ofHours(3);
// Doctors list with specialization
    public List<Doctor> getDoctorBySpecialization(String key){
        String value=redisTemplate.opsForValue().get(key);
        if(value==null){
            return null;
        }
        try{
            System.out.println("Data coming from Redis");
            return objectMapper.readValue(
                    value,
                    new TypeReference<>(){}
            );
        }catch(CustomException e){
            throw new CustomException("Failed to fetch!!!");
        }
    }

    public void setDoctorBySpecialization(String key, List<Doctor> value){
        try{
            String record=objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, record, ttl);
        }
        catch (CustomException e){
            throw new CustomException("Failed to save in cache!!!");
        }
    }

//    doctor availability list by their ID
    public List<DoctorAvailabilityDTO> getDoctorAvailabilityByTheirId(String id){
        String value=redisTemplate.opsForValue().get(id);
        if(value==null){
            return null;
        }
        try{
            System.out.println("Data coming from Redis");
            return objectMapper.readValue(
                    value,
                    new TypeReference<>(){}
            );
        }catch(CustomException e){
            throw new CustomException("Failed to fetch!!!");
        }
    }

    private static final Duration ttlOfAvailability=Duration.ofHours(2);

    public void setDoctorAvailabilityByTheirId(String key, List<DoctorAvailabilityDTO> value){
        try{
            String record=objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, record, ttlOfAvailability);
            System.out.println("Data have been saved in Redis");
        }catch (CustomException e){
            throw new CustomException("Failed to save in cache!!!");
        }
    }

//    Patient Info by their ID
    public Patient getPatientById(String id){
        String value=redisTemplate.opsForValue().get(id);
        if(value==null){
            return null;
        }
        try{
            System.out.println("Data coming from Redis");
            return objectMapper.readValue(value, Patient.class);
        }catch(CustomException e){
            throw new CustomException("Failed to fetch!!!");
        }
    }

    public void setPatientById(String id, Patient value){
        try{
            String record=objectMapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(id, record, Duration.ofMinutes(30));
            System.out.println("Data have been saved in Redis for 30 mins");
        }catch(CustomException e){
            throw new CustomException("Failed to save in cache!!!");
        }
    }
}
