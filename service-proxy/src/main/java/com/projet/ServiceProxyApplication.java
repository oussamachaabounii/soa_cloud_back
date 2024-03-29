package com.projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.CrossOrigin;


@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProxyApplication.class, args);
	}

}
