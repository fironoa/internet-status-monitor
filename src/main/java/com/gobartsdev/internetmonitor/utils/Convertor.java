package com.gobartsdev.internetmonitor.utils;

import com.gobartsdev.internetmonitor.entity.IcmpEntity;
import com.gobartsdev.internetmonitor.model.Icmp;

public class Convertor {

    public static IcmpEntity convertIcmpModelToEntity(Icmp icmp) {
        IcmpEntity entity = new IcmpEntity();
        entity.setTimestamp(icmp.getTimestamp());
        entity.setPackets(icmp.getPackets());
        entity.setPacketsSent(icmp.getPacketsSent());
        entity.setPacketsReceived(icmp.getPacketsReceived());
        entity.setPacketLossPercentage(icmp.getPacketLossPercentage());
        entity.setMaxRtt(icmp.getMaxRtt());
        entity.setMinRtt(icmp.getMinRtt());
        entity.setAvgRtt(icmp.getAvgRtt());
        entity.setClient(icmp.getClient());
        return entity;
    }

    public static Icmp convertIcmpEntityToModel(IcmpEntity icmpEntity) {
        Icmp icmp = new Icmp();
        icmp.setTimestamp(icmpEntity.getTimestamp());
        icmp.setPackets(icmpEntity.getPackets());
        icmp.setPacketsSent(icmpEntity.getPacketsSent());
        icmp.setPacketsReceived(icmpEntity.getPacketsReceived());
        icmp.setPacketLossPercentage(icmpEntity.getPacketLossPercentage());
        icmp.setMaxRtt(icmpEntity.getMaxRtt());
        icmp.setMinRtt(icmpEntity.getMinRtt());
        icmp.setAvgRtt(icmpEntity.getAvgRtt());
        icmp.setClient(icmpEntity.getClient());
        return icmp;
    }
}
