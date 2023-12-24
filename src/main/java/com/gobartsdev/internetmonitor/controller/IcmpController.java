package com.gobartsdev.internetmonitor.controller;

import com.gobartsdev.internetmonitor.model.request.IcmpSearchRequest;
import com.gobartsdev.internetmonitor.service.IcmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class IcmpController {

    @Autowired
    private IcmpService icmpService;

    @GetMapping("/api/v1/icmp")
    public ResponseEntity<?> getIcmpOutput() {
        return ResponseEntity.ok(icmpService.startPing());
    }


    @PostMapping("/api/v1/icmp/by-date")
    public ResponseEntity<?> getIcmpMetricsByDate(@RequestBody IcmpSearchRequest request){
        return ResponseEntity.ok(icmpService.getIcmpMetricsByDate(request));
    }

    @GetMapping("/api/v1/icmp/clients")
    public ResponseEntity<?> getClients() {
        return ResponseEntity.ok(icmpService.getClients());
    }

}
