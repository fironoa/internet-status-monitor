package com.gobartsdev.internetmonitor.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IcmpMetrics {
    private Integer sequence;
    private Integer ttl;
    private Double rtt;
}