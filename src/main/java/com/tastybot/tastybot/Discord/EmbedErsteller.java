package com.tastybot.tastybot.Discord;

import com.tastybot.tastybot.Database.Angebot;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class EmbedErsteller {

    public EmbedBuilder createOfferEmbed(Angebot angebot) {

        EmbedBuilder eb = new EmbedBuilder();

        if (angebot.getInteressenten().size() >= 5){
            eb.setTitle(":fire: "+ angebot.getAngebotstitel() , null);
            eb.setColor(new Color(255, 165, 0));
            eb.setDescription("**Dieses Angebot ist sehr gefragt!**\n\n**Präferenz:** " + angebot.getPreference() + "\n" + "**Verfügbar ab:** " + angebot.getVerfuegbarAb() + "\n" + "**Verfügbar bis:** " + angebot.getVerfuegbarBis() + "\n" + "**Anmerkungen:** " + angebot.getAnmerkungen() + "\n \n" + "**Interessenten:** " + angebot.getInteressenten().size());
        } else {
            eb.setTitle(angebot.getAngebotstitel() , null);
            eb.setColor(new Color(73, 152, 101));
            eb.setDescription("**Präferenz:** " + angebot.getPreference() + "\n" + "**Verfügbar ab:** " + angebot.getVerfuegbarAb() + "\n" + "**Verfügbar bis:** " + angebot.getVerfuegbarBis() + "\n" + "**Anmerkungen:** " + angebot.getAnmerkungen() + "\n \n" + "**Interessenten:** " + angebot.getInteressenten().size());
        }

        if(angebot.getFoto() != null){
            eb.setImage(angebot.getFoto());
        }

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    public EmbedBuilder createOfferInterestedNotification(Angebot angebot, User user){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Der Nutzer '" + user.getGlobalName() + "' hat Interesse an deinem Angebot '" + angebot.getAngebotstitel() + "'" , null);

        eb.setDescription("Bitte kontaktiere ihn unter dem Usernamen '" + user.getGlobalName() + "', damit ihr die Einzelheiten klären könnt!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    public EmbedBuilder createOfferCreationNotification(Angebot angebot){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Du hast das Angebot '" + angebot.getAngebotstitel() + "' erstellt" , null);

        eb.setDescription("Du kannst das Angebot wieder durch den Button unter der Nachricht löschen, wenn es jemand abgeholt hat oder du dein Angebot zurückziehen möchtest!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }
}
