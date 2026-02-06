package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class IdempotencyKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdempotencyId;
    @Column(unique = true,  nullable = false)
    private String idempotencyKey;
    @Column(nullable = false)
    private String RequestHash;
    @Lob
    private String responseBody;
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime createdAt=LocalDateTime.now();
}
