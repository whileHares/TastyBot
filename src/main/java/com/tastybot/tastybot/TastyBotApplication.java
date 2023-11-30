package com.tastybot.tastybot;

import com.tastybot.tastybot.business_logic.OfferCreator;
import com.tastybot.tastybot.business_logic.OfferHandler;
import com.tastybot.tastybot.discord.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * The main entry point for the TastyBot application.
 */
@SpringBootApplication
public class TastyBotApplication {

	/**
	 * The main method that initializes and starts the TastyBot application.
	 *
	 * @param args Command-line arguments provided to the application.
	 * @throws InterruptedException Thrown if an interruption occurs while starting the bot.
	 */
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
