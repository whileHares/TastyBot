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

@Service
@Data
@RequiredArgsConstructor
public class Bot {

    @Value("${discord_token}")
    private String botToken;

    private JDA jda;

    private final EventListener eventListener;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLD = "\u001B[1m";

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