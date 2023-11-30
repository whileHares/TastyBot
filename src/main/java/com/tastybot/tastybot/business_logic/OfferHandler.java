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

@Component
@Service
@RequiredArgsConstructor
public class OfferHandler {

    private final EmbedCreator embedCreator;
    private final OfferRepository offerRepository;
    private final OfferCreator offerCreator;

    @Value("${server_id}")
    private String server_Id;

    private Bot bot;

    public void setBot(Bot bot){
        this.bot = bot;
    }

    public void notifyOfferCreator(Offer offer, User user) {

        bot.getJda().retrieveUserById(offer.getUserId()).queue(customer -> customer.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedCreator.createOfferInterestedNotification(offer, user).build()))
                .queue());

    }

    public void confirmOffer(SlashCommandInteractionEvent event, Offer offer){
        event.getUser().openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedCreator.createOfferCreationNotification(offer).build()).addActionRow(Button.danger("angebot-loeschen", "Angebot löschen")))
                .queue((message) -> {
                    Offer newOffer = offerRepository.findById(offer.getOfferId()).orElse(null);
                    newOffer.setOfferDeleteMessageId(message.getIdLong());
                    offerRepository.save(newOffer);
                });
    }

    public void deleteOffer(Offer offer) {
        bot.getJda().getGuildById(server_Id).getTextChannelById(offerCreator.getTextChannel(offer)).retrieveMessageById(offer.getOfferMessageId()).queue(message -> message.delete().queue());
    }

    public void updateOffer(Offer offer){
        bot.getJda().getGuildById(server_Id).getTextChannelById(offerCreator.getTextChannel(offer)).retrieveMessageById(offer.getOfferMessageId()).queue(message -> message.editMessageEmbeds(embedCreator.createOfferEmbed(offer).build()).queue());
    }
}
