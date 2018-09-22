package com.springboot.dlc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(value = "com.springboot.dlc.mapper")
public class DlcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlcApplication.class, args);
	}
}
