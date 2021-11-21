package com.zcc.ticketviewer;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@Log4j2
@SpringBootApplication
//@EnableFeignClients
public class TicketviewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketviewerApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		log.error("executing command line interface");
//
//		final Scanner in = new Scanner(System.in);
//		while(true){
//			System.out.println("What is your name?");
//			String input = in.nextLine();
//			System.out.println(input + " welcome to springboot");
//			if(input.equals("exit")){
//				break;
//			}
//		}
//		log.error("came here");
//		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(TicketviewerApplication.class)
//				.web(WebApplicationType.NONE).run();
//		SpringApplication.exit(ctx, () -> 0);
//
//
//	}
}
