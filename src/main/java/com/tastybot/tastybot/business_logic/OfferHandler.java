package com.tastybot.tastybot.business_logic;

import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.OfferRepository;
import com.tastybot.tastybot.discord.Bot;
import com.tastybot.tastybot.discord.EmbedCreator;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Handles business logic related to offers, including notifications and interactions.
 */
@Component
@Service
@RequiredArgsConstructor
public class OfferHandler {

    /**
     * The embed creator for creating Discord message embeds.
     */
    private final EmbedCreator embedCreator;

    /**
     * The repository for managing Offer entities in the database.
     */
    private final OfferRepository offerRepository;

    /**
     * The creator for handling offers.
     */
    private final OfferCreator offerCreator;

    /**
     * The ID of the Discord server.
     */
    @Value("${server_id}")
    private String server_Id;

    /**
     * The instance of the Discord bot.
     */
    private Bot bot;

    /**
     * Sets the bot instance for the handler.
     *
     * @param bot The Bot instance.
     */
    public void setBot(Bot bot){
        this.bot = bot;
    }

    /**
     * Notifies the creator of an offer when someone is interested.
     *
     * @param offer The offer that has received interest.
     * @param user The user expressing interest.
     */
    public void notifyOfferCreator(Offer offer, User user) {
        bot.getJda().retrieveUserById(offer.getUserId()).queue(customer -> customer.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedCreator.createOfferInterestedNotification(offer, user).build()))
                .queue());
    }

    /**
     * Confirms the creation of an offer and sends a notification to the user.
     *
     * @param event The SlashCommandInteractionEvent triggering the offer confirmation.
     * @param offer The offer being confirmed.
     */
    public void confirmOffer(SlashCommandInteractionEvent event, Offer offer){
        event.getUser().openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedCreator.createOfferCreationNotification(offer).build()).addActionRow(Button.danger("angebot-loeschen", "Angebot löschen")))
                .queue((message) -> {
                    Offer newOffer = offerRepository.findById(offer.getOfferId()).orElse(null);
                    newOffer.setOfferDeleteMessageId(message.getIdLong());
                    offerRepository.save(newOffer);
                });
    }

    /**
     * Deletes an offer and its associated message.
     *
     * @param offer The offer to be deleted.
     */
    public void deleteOffer(Offer offer) {
        bot.getJda().getGuildById(server_Id).getTextChannelById(offerCreator.getTextChannel(offer)).retrieveMessageById(offer.getOfferMessageId()).queue(message -> message.delete().queue());
    }

    /**
     * Updates the message associated with an offer.
     *
     * @param offer The offer to be updated.
     */
    public void updateOffer(Offer offer){
        bot.getJda().getGuildById(server_Id).getTextChannelById(offerCreator.getTextChannel(offer)).retrieveMessageById(offer.getOfferMessageId()).queue(message -> message.editMessageEmbeds(embedCreator.createOfferEmbed(offer).build()).queue());
    }
}
