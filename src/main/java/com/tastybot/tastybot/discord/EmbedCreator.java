package com.tastybot.tastybot.discord;

import com.tastybot.tastybot.database.entity.Offer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class EmbedCreator {

    public EmbedBuilder createOfferEmbed(Offer offer) {

        EmbedBuilder eb = new EmbedBuilder();

        if (offer.getApplicants().size() >= 5){
            eb.setTitle(":fire: "+ offer.getOfferTitle() , null);
            eb.setColor(new Color(255, 165, 0));
            eb.setDescription("**Dieses Angebot ist sehr gefragt!**\n\n**Präferenz:** " + offer.getPreference() + "\n" + "**Verfügbar ab:** " + offer.getAvailableFrom() + "\n" + "**Verfügbar bis:** " + offer.getAvailableUntil() + "\n" + "**Anmerkungen:** " + offer.getNotes() + "\n \n" + "**Interessenten:** " + offer.getApplicants().size());
        } else {
            eb.setTitle(offer.getOfferTitle() , null);
            eb.setColor(new Color(73, 152, 101));
            eb.setDescription("**Präferenz:** " + offer.getPreference() + "\n" + "**Verfügbar ab:** " + offer.getAvailableFrom() + "\n" + "**Verfügbar bis:** " + offer.getAvailableUntil() + "\n" + "**Anmerkungen:** " + offer.getNotes() + "\n \n" + "**Interessenten:** " + offer.getApplicants().size());
        }

        if(offer.getPhotoUrl() != null){
            eb.setImage(offer.getPhotoUrl());
        }

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    public EmbedBuilder createOfferInterestedNotification(Offer offer, User user){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Der Nutzer '" + user.getGlobalName() + "' hat Interesse an deinem Angebot '" + offer.getOfferTitle() + "'" , null);

        eb.setDescription("Bitte kontaktiere ihn unter dem Usernamen '" + user.getGlobalName() + "', damit ihr die Einzelheiten klären könnt!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    public EmbedBuilder createOfferCreationNotification(Offer offer){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Du hast das Angebot '" + offer.getOfferTitle() + "' erstellt" , null);

        eb.setDescription("Du kannst das Angebot wieder durch den Button unter der Nachricht löschen, wenn es jemand abgeholt hat oder du dein Angebot zurückziehen möchtest!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }
}
