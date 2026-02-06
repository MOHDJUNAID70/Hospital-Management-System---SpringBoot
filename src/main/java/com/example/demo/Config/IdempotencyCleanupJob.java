package com.example.demo.Config;


import com.example.demo.Repository.IdempotencyRepo;
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

    @Scheduled(cron = "0 0 20 * * *")
    public void cleanup() {
        idempotencyRepo.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}
