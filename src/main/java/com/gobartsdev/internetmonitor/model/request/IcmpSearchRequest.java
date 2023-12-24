package com.gobartsdev.internetmonitor.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class IcmpSearchRequest {
    private Instant startTime;
    private Instant endTime;
    private String client;
}
