package com.example.demo.Idempotency;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling
public class IdempotencyCleanupJob {

    @Autowired
    private IdempotencyRepo idempotencyRepo;

    @Transactional
    @Scheduled(cron = "0 */45 * * * *")
    public void cleanup() {
        idempotencyRepo.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
