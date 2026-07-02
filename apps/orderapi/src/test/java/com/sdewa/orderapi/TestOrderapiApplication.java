package com.sdewa.orderapi;

import org.springframework.boot.SpringApplication;

public class TestOrderapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(OrderapiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
