package com.spring.spring;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@SpringBootApplication
public class Application  implements WebMvcConfigurer {
	public static void main(String[] args) {
		Thread thread = new Thread(new MDLPToken());
		thread.start();
		SpringApplication.run(Application.class, args);

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/resources/static/css/**").addResourceLocations("/resources/static/css/");

	}
}