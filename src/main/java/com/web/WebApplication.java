package com.web;

import com.web.rocketMQ.Listener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WebApplication {
	public static void main(String[] args) throws Exception{
		ConfigurableApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
		SpringUtil.setAppContext(ctx);
		Listener listener = (Listener) ctx.getBean("listener");
		listener.start();
	}
}
