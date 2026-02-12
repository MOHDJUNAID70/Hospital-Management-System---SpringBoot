package com.example.demo.Idempotency;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface IdempotencyRepo extends JpaRepository<IdempotencyKey, Integer> {


    IdempotencyKey findByIdempotencyKeyAndExpiresAtAfter(String key, LocalDateTime expiresAt);

    void deleteByExpiresAtBefore(LocalDateTime now);
}
