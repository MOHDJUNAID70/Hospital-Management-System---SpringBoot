package com.example.demo.Model.DTO;


import lombok.Data;

@Data
public class IdempotencyRecordDTO {
    private String requestHash;
    private String response;
}
