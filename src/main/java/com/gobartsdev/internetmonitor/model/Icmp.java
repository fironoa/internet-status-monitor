package com.gobartsdev.internetmonitor.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
public class Icmp {
    private Instant timestamp;
    private Integer packetsSent;
    private Integer packetsReceived;
    private Double packetLossPercentage;
    private Double minRtt;
    private Double maxRtt;
    private Double avgRtt;
    private String client;
    private Map<Integer, IcmpMetrics> packets;
}
