package com.tastybot.tastybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TastyBotApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(TastyBotApplication.class, args);
		Bot bot = applicationContext.getBean(Bot.class);
		bot.startBot(); //erwartet exceptionHndl. in der Methodensignatur
	}

}
