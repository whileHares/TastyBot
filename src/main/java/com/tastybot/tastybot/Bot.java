package com.tastybot.tastybot;
//Diese Klasse dient zum starten des Discord-Bots

import lombok.Data;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service //Annotation Bean erkennen Application.context Spring erstellt bein Start Beans und speichert diese im Application.Context
@Data //Setter/Getter etc.
public class Bot {

    @Value("${discord_token}")
    private String botToken;

    private JDA jda;

    public JDA startBot() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(botToken)
                .addEventListeners() //für die slash_commands
                .setActivity(Activity.listening("Haftbefehl"))
                .build()
                .awaitReady();
        setJda(jda);
        System.out.println("online"); //status
        return jda;
    }
}
//TODO: Eventlisteners alg. und Discord Bots informieren JAVA/JDA
//TODO: Bisherigen Code erklären - wieso, weshalb, warum?
//TODO: Git pushen
//TODO: Dokumentation