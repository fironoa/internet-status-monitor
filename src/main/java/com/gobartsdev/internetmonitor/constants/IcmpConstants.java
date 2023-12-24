package com.gobartsdev.internetmonitor.constants;

public enum IcmpConstants {

    PING_COMMAND("command"),
    COUNT_FLAG("countFlag"),
    PING_HOST("host"),
    PACKETS_COUNT("count"),
    LINUX_PING_METRICS_STATS_PATTERN("metricsPattern"),
    LINUX_PING_METRICS_SUMMARY_PATTERN("summaryPattern"),
    LINUX_PING_METRICS_CONSOLIDATE_PATTERN("consolidatedMetricsPattern");

    private final String value;

    IcmpConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
