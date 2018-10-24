package com.springboot.dlc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@EnableScheduling
@EnableTransactionManagement
@MapperScan(value = "com.springboot.dlc.mapper")
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class},
		scanBasePackages = {"com.springboot.dlc"})
public class DlcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DlcApplication.class, args);
	}

}
