package com.tastybot.tastybot.Discord;
//Diese Klasse dient zum Starten des Discord-Bots

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
/*Eine Annotation in Spring, die kennzeichnet, dass diese Klasse
als Service-Klasse behandelt werden soll.
Spring erkennt sie als Bean und fügt sie dem Anwendungscontext(application.context) hinzu.*/
@Data
/*Eine Lombok-Annotation, die automatisch Standardmethoden
wie Getter, Setter, equals, hashCode und toString generiert.*/
@RequiredArgsConstructor
public class Bot {

    @Value("${discord_token}")
    /*Die @Value-Annotation in Spring ermöglicht es, Werte direkt in Felder von Spring-Komponenten
    (wie Klassen, Methoden oder Felder in Konfigurationsklassen) zu injizieren.
    Diese Werte können entweder aus den properties, Umgebungsvariablen oder anderen
    Quellen stammen.*/
    private String botToken; /*Verwendet Spring und setzt den Wert von botToken
    aus einer Konfigurationsdatei oder Umgebungsvariable.*/

    private JDA jda; /* Eine Instanzvariable vom Typ JDA, die die Instanz des Discord-Bots repräsentiert.*/

    private final EventListener eventListener;
    /*Farben für Statusausgabe*/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BOLD = "\u001B[1m";

    public JDA startBot() throws InterruptedException {
        JDA jda = JDABuilder.createDefault(botToken)
                .addEventListeners(eventListener) //für die slash_commands
                .setActivity(Activity.listening("Haftbefehl"))
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build()
                .awaitReady();
        setJda(jda);
        System.out.println(ANSI_GREEN + ANSI_BOLD + "###############\n  # ONLINE #\n###############" + ANSI_RESET); //status
        return jda;
    }
}

//TODO: Bisherigen Code erklären - wieso, weshalb, warum?
//TODO: Sequenz oder Aktivitätsdiagramm - Parallel zum Klassendiagramm.
//TODO: Was ist Dependency Injection - Unterschiede erklären und Einsatzzwecke.
//TODO: Nutzer kann über Slash-Commands Angebote erstellen und diese werden in der DB gespeichert.