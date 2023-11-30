package com.tastybot.tastybot.discord;

import com.tastybot.tastybot.database.entity.Offer;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Instant;

/**
 * Creates Discord message embeds for offers and notifications.
 */
@Component
@RequiredArgsConstructor
public class EmbedCreator {

    /**
     * Creates an embed for displaying an offer.
     *
     * @param offer The offer to create an embed for.
     * @return The created EmbedBuilder.
     */
    public EmbedBuilder createOfferEmbed(Offer offer) {
        EmbedBuilder eb = new EmbedBuilder();

        if (offer.getApplicants().size() >= 5){
            eb.setTitle(":fire: "+ offer.getOfferTitle() , null);
            eb.setColor(new Color(255, 165, 0));
            eb.setDescription("**This offer is highly sought after!**\n\n**Preference:** " + offer.getPreference() + "\n" + "**Available from:** " + offer.getAvailableFrom() + "\n" + "**Available until:** " + offer.getAvailableUntil() + "\n" + "**Notes:** " + offer.getNotes() + "\n \n" + "**Applicants:** " + offer.getApplicants().size());
        } else {
            eb.setTitle(offer.getOfferTitle() , null);
            eb.setColor(new Color(73, 152, 101));
            eb.setDescription("**Preference:** " + offer.getPreference() + "\n" + "**Available from:** " + offer.getAvailableFrom() + "\n" + "**Available until:** " + offer.getAvailableUntil() + "\n" + "**Notes:** " + offer.getNotes() + "\n \n" + "**Applicants:** " + offer.getApplicants().size());
        }

        if(offer.getPhotoUrl() != null){
            eb.setImage(offer.getPhotoUrl());
        }

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    /**
     * Creates an embed for notifying the offer creator about user interest.
     *
     * @param offer The offer for which there is user interest.
     * @param user The user expressing interest.
     * @return The created EmbedBuilder.
     */
    public EmbedBuilder createOfferInterestedNotification(Offer offer, User user){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("User '" + user.getGlobalName() + "' is interested in your offer '" + offer.getOfferTitle() + "'" , null);

        eb.setDescription("Please contact them with the username '" + user.getGlobalName() + "' to discuss the details!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }

    /**
     * Creates an embed for notifying the user about the creation of their offer.
     *
     * @param offer The offer that has been created.
     * @return The created EmbedBuilder.
     */
    public EmbedBuilder createOfferCreationNotification(Offer offer){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("You have created the offer '" + offer.getOfferTitle() + "'" , null);

        eb.setDescription("You can delete the offer using the button below the message once it has been picked up or if you want to withdraw your offer!");

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");

        eb.setThumbnail("https://i.imgur.com/r85wmLK.png");

        eb.setTimestamp(Instant.now());

        return eb;
    }
}
