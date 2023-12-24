package com.gobartsdev.internetmonitor.entity;

import com.gobartsdev.internetmonitor.model.IcmpMetrics;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.Instant;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "icmp_entity")
public class IcmpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant timestamp;
    private Integer packetsSent;
    private Integer packetsReceived;
    private Double packetLossPercentage;
    private Double minRtt;
    private Double maxRtt;
    private Double avgRtt;
    private String client;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<Integer, IcmpMetrics> packets;
}
