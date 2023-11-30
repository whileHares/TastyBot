package com.tastybot.tastybot.business_logic;

import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.OfferRepository;
import com.tastybot.tastybot.discord.Bot;
import com.tastybot.tastybot.discord.EmbedCreator;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Handles the creation and sending of offers to Discord channels.
 */
@Component
@Service
@RequiredArgsConstructor
public class OfferCreator {

    /**
     * The instance of the Discord bot.
     */
    private Bot bot;

    /**
     * The repository for managing Offer entities in the database.
     */
    private final OfferRepository offerRepository;

    /**
     * The embed creator for creating Discord message embeds.
     */
    private final EmbedCreator embedCreator;

    /**
     * The Discord server ID.
     */
    @Value("${server_id}")
    private String server_Id;

    /**
     * The Discord text channel for Hamburg North.
     */
    @Value("${hamburg_north}")
    private String hamburgNorth;

    /**
     * The Discord text channel for Hamburg South.
     */
    @Value("${hamburg_south}")
    private String hamburgSouth;

    /**
     * The Discord text channel for Hamburg West.
     */
    @Value("${hamburg_west}")
    private String hamburgWest;

    /**
     * The Discord text channel for Hamburg East.
     */
    @Value("${hamburg_east}")
    private String hamburgEast;

    /**
     * The Discord text channel for Hamburg Mid.
     */
    @Value("${hamburg_mid}")
    private String hamburgMid;

    /**
     * Sets the bot instance for the offer creator.
     *
     * @param bot The Bot instance.
     */
    public void setBot(Bot bot){
        this.bot = bot;
    }

    /**
     * Sends an offer to the corresponding channel and updates the offer with the sent message ID.
     *
     * @param offer The offer to be sent.
     * @return The updated offer.
     */
    public Offer sendOfferToChannel(Offer offer) {
        bot.getJda().getGuildById(server_Id).getTextChannelById(getTextChannel(offer)).sendMessageEmbeds(embedCreator.createOfferEmbed(offer).build()).addActionRow(
                Button.primary("interessiert", "Interessiert")).queue((message) -> {
            Offer newOffer = offerRepository.findById(offer.getOfferId()).orElse(null);
            newOffer.setOfferMessageId(message.getIdLong());
            offerRepository.save(newOffer);
        });
        return offer;
    }

    /**
     * Determines the Discord text channel based on the pickup location of the offer.
     *
     * @param offer The offer containing the pickup location.
     * @return The ID of the corresponding text channel.
     */
    public String getTextChannel(Offer offer) {
        String pickupLocation = offer.getPickupLocation();
        String channel;

        switch (pickupLocation) {
            case "hamburg-ost":
                channel = hamburgEast;
                break;
            case "hamburg-west":
                channel = hamburgWest;
                break;
            case "hamburg-nord":
                channel = hamburgNorth;
                break;
            case "hamburg-süd":
                channel = hamburgSouth;
                break;
            case "hamburg-mitte":
                channel = hamburgMid;
                break;
            default:
                channel = hamburgMid;
                break;
        }

        return channel;
    }

}
