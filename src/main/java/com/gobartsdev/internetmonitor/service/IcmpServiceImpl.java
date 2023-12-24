package com.gobartsdev.internetmonitor.service;

import com.gobartsdev.internetmonitor.model.Icmp;
import com.gobartsdev.internetmonitor.model.IcmpMetrics;
import com.gobartsdev.internetmonitor.model.request.IcmpSearchRequest;
import com.gobartsdev.internetmonitor.repository.IcmpRepository;
import com.gobartsdev.internetmonitor.utils.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.gobartsdev.internetmonitor.constants.IcmpConstants.*;

@Service
public class IcmpServiceImpl implements IcmpService{

    @Value("${provider.name}")
    private String PROVIDER;

    @Autowired
    private Map<String, String> osSpecificConfig;

    @Autowired
    private IcmpRepository icmpRepository;

    @Override
    public Icmp startPing() {
        Icmp icmp = new Icmp();
        icmp.setTimestamp(Instant.now());
        icmp.setClient(PROVIDER);
        ProcessBuilder processBuilder = new ProcessBuilder(
                osSpecificConfig.get(PING_COMMAND.getValue()),
                osSpecificConfig.get(COUNT_FLAG.getValue()) ,
                osSpecificConfig.get(PACKETS_COUNT.getValue()),
                osSpecificConfig.get(PING_HOST.getValue())
        );
        try {

            Process p = processBuilder.start();

            // Process p = Runtime.getRuntime().exec(PING_COMMAND + PACKETS_COUNT + PING_HOST);
            BufferedReader inputStream = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            Map<Integer, IcmpMetrics> packets = new HashMap<>();
            String s;
            Pattern pattern = Pattern.compile(osSpecificConfig.get(LINUX_PING_METRICS_STATS_PATTERN.getValue()));
            Pattern summaryPattern = Pattern.compile(osSpecificConfig.get(LINUX_PING_METRICS_SUMMARY_PATTERN.getValue()));
            Pattern consolidatedPattern = Pattern.compile(osSpecificConfig.get(LINUX_PING_METRICS_CONSOLIDATE_PATTERN.getValue()));
            int i=1;
            while ((s = inputStream.readLine()) != null) {
                Matcher matcher = pattern.matcher(s);
                Matcher summaryMatcher = summaryPattern.matcher(s);
                Matcher consolidatedMatcher = consolidatedPattern.matcher(s);
                if (matcher.find()) {
                    IcmpMetrics metrics = new IcmpMetrics();
                    metrics.setSequence(i);
                    i++;
                    metrics.setTtl(Integer.parseInt(matcher.group(3)));
                    metrics.setRtt(Double.parseDouble(matcher.group(2)));
                    packets.put(i, metrics);

                } else if (summaryMatcher.find()) {

                    icmp.setPacketLossPercentage(Double.parseDouble(summaryMatcher.group(3)));
                    icmp.setPacketsSent(Integer.parseInt(summaryMatcher.group(1)));
                    icmp.setPacketsReceived(Integer.parseInt(summaryMatcher.group(2)));
                    icmp.setPackets(packets);
                } else if (consolidatedMatcher.find()) {

                    icmp.setAvgRtt(Double.parseDouble(consolidatedMatcher.group(3)));
                    icmp.setMinRtt(Double.parseDouble(consolidatedMatcher.group(1)));
                    icmp.setMaxRtt(Double.parseDouble(consolidatedMatcher.group(2)));
                }

            }
            return Convertor.convertIcmpEntityToModel(icmpRepository.save(Convertor.convertIcmpModelToEntity(icmp)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while executing the command!");
        }
    }

    @Override
    public List<Icmp> getIcmpMetricsByDate(IcmpSearchRequest request) {
        return icmpRepository.findByTimestampBetweenAndClientOrderByTimestampAsc(request.getStartTime(), request.getEndTime(), request.getClient())
                .stream().map(Convertor::convertIcmpEntityToModel).collect(Collectors.toList());
    }

    @Override
    public void cleanHistory() {

    }

    @Override
    public List<String> getClients() {
        return icmpRepository.findDistinctClients().stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

}
