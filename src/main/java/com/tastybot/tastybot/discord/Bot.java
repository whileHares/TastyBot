package com.tastybot.tastybot.discord;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Represents the Discord bot and manages its initialization and startup.
 */
@Service
@Data
@RequiredArgsConstructor
public class Bot {

    /**
     * The Discord bot token for authentication.
     */
    @Value("${discord_token}")
    private String botToken;

    /**
     * The JDA instance representing the Discord bot.
     */
    private JDA jda;

    /**
     * The event listener for handling Discord events.
     */
    private final EventListener eventListener;

    /**
     * ANSI escape code for resetting text formatting.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * ANSI escape code for green text color.
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * ANSI escape code for bold text.
     */
    public static final String ANSI_BOLD = "\u001B[1m";

    /**
     * Initializes and starts the Discord bot.
     *
     * @return The JDA instance representing the Discord bot.
     * @throws InterruptedException If an error occurs during bot startup.
     */
    public JDA startBot() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(botToken)
                .addEventListeners(eventListener)
                .setActivity(Activity.listening("Haftbefehl"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build()
                .awaitReady();
        setJda(jda);
        System.out.println(ANSI_GREEN + ANSI_BOLD +
                "          ####################\n" +
                "            ##   ONLINE   ##          \n" +
                "          #################### \n\n" +
                "   _______        _         ____        _   \n" +
                " |__   __|      | |       |  _ \\      | |  \n" +
                "    | | __ _ ___| |_ _   _| |_) | ___ | |_ \n" +
                "    | |/ _` / __| __| | | |  _ < / _ \\| __|\n" +
                "    | | (_| \\__ \\ |_| |_| | |_) | (_) | |_ \n" +
                "    |_|\\__,_|___/\\__|\\__, |____/ \\___/ \\__|\n" +
                "                      __/ |                \n" +
                "                     |___/  "
                + ANSI_RESET);
        return jda;
    }
}
