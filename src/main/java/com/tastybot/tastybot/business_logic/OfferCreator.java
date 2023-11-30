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

@Component
@Service
@RequiredArgsConstructor
public class OfferCreator {
    private Bot bot;

   public void setBot(Bot bot){
       this.bot = bot;
   }

    private final OfferRepository offerRepository;

    private final EmbedCreator embedCreator;

    @Value("${hamburg_north}")
    private String hamburgNorth;

    @Value("${hamburg_south}")
    private String hamburgSouth;

    @Value("${hamburg_west}")
    private String hamburgWest;

    @Value("${hamburg_east}")
    private String hamburgEast;

    @Value("${hamburg_mid}")
    private String hamburgMid;

    @Value("${server_id}")
    private String server_Id;

    public Offer sendOfferToChannel(Offer offer) {

        bot.getJda().getGuildById(server_Id).getTextChannelById(getTextChannel(offer)).sendMessageEmbeds(embedCreator.createOfferEmbed(offer).build()).addActionRow(
                Button.primary("interessiert", "Interessiert")).queue((message) -> {
                    Offer newOffer = offerRepository.findById(offer.getOfferId()).orElse(null);
                    newOffer.setOfferMessageId(message.getIdLong());
                    offerRepository.save(newOffer);
                });
        return offer;
    }

    public String getTextChannel(Offer offer) {
        String pickupLocation = offer.getPickupLocation();

        return switch (pickupLocation) {
            case "hamburg-ost" -> hamburgEast;
            case "hamburg-west" -> hamburgWest;
            case "hamburg-nord" -> hamburgNorth;
            case "hamburg-süd" -> hamburgSouth;
            case "hamburg-mitte" -> hamburgMid;
            default -> hamburgMid;
        };
    }
}
