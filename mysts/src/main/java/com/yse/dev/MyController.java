package com.yse.dev;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	@GetMapping("/")
	public String get001() {
		return "hello~~~ my sts site";
	}

	@GetMapping("/greet")
	public String greet() {
		return "안녕하세요 나의사이트에 오신걸 환영합니다.";
	}
}
