package com.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Jiahui Li
 * @DATE 2019/11/30 23:37
 */
@SpringBootApplication(scanBasePackages = "com.mongodb")
public class MongoDBApp {

	public static void main(String[] args) {
		SpringApplication.run(MongoDBApp.class, args);
	}

}
