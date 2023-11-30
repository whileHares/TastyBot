package com.tastybot.tastybot;

import com.tastybot.tastybot.business_logic.OfferCreator;
import com.tastybot.tastybot.business_logic.OfferHandler;
import com.tastybot.tastybot.discord.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TastyBotApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext = SpringApplication.run(TastyBotApplication.class, args);
		Bot bot = applicationContext.getBean(Bot.class);
		OfferCreator offerCreator = applicationContext.getBean(OfferCreator.class);
		offerCreator.setBot(bot);
		OfferHandler offerHandler = applicationContext.getBean(OfferHandler.class);
		offerHandler.setBot(bot);
		bot.startBot();
	}

}
