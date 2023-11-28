package com.tastybot.tastybot;

import com.tastybot.tastybot.BusinessLogic.AngebotsErsteller;
import com.tastybot.tastybot.BusinessLogic.AngebotsHandler;
import com.tastybot.tastybot.Discord.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TastyBotApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(TastyBotApplication.class, args);
		Bot bot = applicationContext.getBean(Bot.class);
		AngebotsErsteller angebotsErsteller = applicationContext.getBean(AngebotsErsteller.class);
		angebotsErsteller.setBot(bot);
		AngebotsHandler angebotsHandler = applicationContext.getBean(AngebotsHandler.class);
		angebotsHandler.setBot(bot);
		bot.startBot(); //erwartet exceptionHndl. in der Methodensignatur
	}

}
