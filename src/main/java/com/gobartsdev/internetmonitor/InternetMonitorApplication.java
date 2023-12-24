package com.gobartsdev.internetmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternetMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetMonitorApplication.class, args);
	}

}
