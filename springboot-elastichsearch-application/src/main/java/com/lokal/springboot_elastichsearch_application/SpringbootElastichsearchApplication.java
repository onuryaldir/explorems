package com.lokal.springboot_elastichsearch_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringbootElastichsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootElastichsearchApplication.class, args);
	}

}
