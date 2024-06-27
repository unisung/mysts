package com.yse.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Spring Data JPA의 Auditing 기능 활성화
@SpringBootApplication
public class MystsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MystsApplication.class, args);
	}

}
