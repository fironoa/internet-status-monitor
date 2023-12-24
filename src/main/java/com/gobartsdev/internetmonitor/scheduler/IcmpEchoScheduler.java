package com.gobartsdev.internetmonitor.scheduler;

import com.gobartsdev.internetmonitor.service.IcmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IcmpEchoScheduler {

    @Autowired
    private IcmpService icmpService;

    @Scheduled(fixedRate = 10000) // runs every 10 seconds
    public void performTask() {
        log.info("Regular task performed at " + java.time.LocalDateTime.now());
        icmpService.startPing();
    }

}
