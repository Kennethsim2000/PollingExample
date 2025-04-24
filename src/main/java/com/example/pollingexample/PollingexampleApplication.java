package com.example.pollingexample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.example.pollingexample.mapper")
public class PollingexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollingexampleApplication.class, args);
	}

}

//Swagger URL: http://localhost:8080/swagger-ui/index.html
