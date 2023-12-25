package com.gobartsdev.internetmonitor.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.gobartsdev.internetmonitor.constants.IcmpConstants.*;

@Configuration
public class OsConfig {

    @Bean
    public Map<String, String> osSpecificIcmpConfig() {
        Map<String, String> icmpConfig = new HashMap<>();
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            icmpConfig.put(OS.getValue(), osName);
            icmpConfig.put(PING_COMMAND.getValue(), "ping");
            icmpConfig.put(COUNT_FLAG.getValue(), "-n");
            icmpConfig.put(PACKETS_COUNT.getValue(), "8");
            icmpConfig.put(PING_HOST.getValue(), "google.com");
            icmpConfig.put(LINUX_PING_METRICS_STATS_PATTERN.getValue(), "Reply from [^:]+: bytes=(\\d+) time=([0-9]+)ms TTL=(\\d+)");
            icmpConfig.put(LINUX_PING_METRICS_SUMMARY_PATTERN.getValue(), "Packets: Sent = (\\d+), Received = (\\d+), Lost = (\\d+) \\((\\d+)% loss\\),");
            icmpConfig.put(LINUX_PING_METRICS_CONSOLIDATE_PATTERN.getValue(), "Minimum = (\\d+)ms, Maximum = (\\d+)ms, Average = (\\d+)ms");
            // other configurations if any...
        } else if (osName.contains("lin")) {
            icmpConfig.put(OS.getValue(), osName);
            icmpConfig.put(PING_COMMAND.getValue(), "ping");
            icmpConfig.put(COUNT_FLAG.getValue(), "-c");
            icmpConfig.put(PACKETS_COUNT.getValue(), "8");
            icmpConfig.put(PING_HOST.getValue(), "google.com");
            icmpConfig.put(LINUX_PING_METRICS_STATS_PATTERN.getValue(), "icmp_seq=(\\d+) ttl=(\\d+) time=([0-9.]+) ms");
            icmpConfig.put(LINUX_PING_METRICS_SUMMARY_PATTERN.getValue(), "(\\d+) packets transmitted, (\\d+) received, (\\d+)% packet loss, time (\\d+)ms");
            icmpConfig.put(LINUX_PING_METRICS_CONSOLIDATE_PATTERN.getValue(), "rtt min/avg/max/mdev = (\\d+\\.\\d+)/(\\d+\\.\\d+)/(\\d+\\.\\d+)/(\\d+\\.\\d+) ms");
            // other configurations if any...
        }
        return icmpConfig;
    }

}
