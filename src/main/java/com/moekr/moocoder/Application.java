package com.moekr.moocoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.ZoneId;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.moekr.moocoder.data.dao")
@EnableJpaAuditing
public class Application extends SpringApplication {
	public static void main(String[] args) {
		// 强制写入默认时区
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("GMT+8")));
		SpringApplication.run(Application.class, args);
	}
}
