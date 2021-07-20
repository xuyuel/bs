package com.sjzc.kt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
@SpringBootApplication
@ServletComponentScan
public class ktApplication {

	public static void main(String[] args) {
		SpringApplication.run(ktApplication.class, args);
	}
}
