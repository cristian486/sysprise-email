package br.com.sysprise.email;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class SyspriseEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyspriseEmailApplication.class, args);
	}

}
