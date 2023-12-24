package com.gobartsdev.internetmonitor.service;

import com.gobartsdev.internetmonitor.model.Icmp;
import com.gobartsdev.internetmonitor.model.request.IcmpSearchRequest;

import java.util.List;

public interface IcmpService {

    Icmp startPing();

    List<Icmp> getIcmpMetricsByDate(IcmpSearchRequest request);

    void cleanHistory();

    List<String> getClients();

}
