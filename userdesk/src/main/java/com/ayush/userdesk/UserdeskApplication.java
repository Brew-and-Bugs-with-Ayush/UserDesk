package com.ayush.userdesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class UserdeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserdeskApplication.class, args);
	}

}
