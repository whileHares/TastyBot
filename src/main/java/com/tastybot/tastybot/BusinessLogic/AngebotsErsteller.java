package com.tastybot.tastybot.BusinessLogic;

import com.tastybot.tastybot.Database.Angebot;
import com.tastybot.tastybot.Database.AngebotRepository;
import com.tastybot.tastybot.Discord.Bot;
import com.tastybot.tastybot.Discord.EmbedErsteller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class AngebotsErsteller {

    private final Bot bot; //dependency injection

    private final AngebotRepository angebotRepository;

    private final EmbedErsteller embedErsteller;

    @Value("${hamburg_nord}")
    private String hamburgNord;

    @Value("${hamburg_sued}")
    private String hamburgSued;

    @Value("${hamburg_west}")
    private String hamburgWest;

    @Value("${hamburg_ost}")
    private String hamburgOst;

    @Value("${hamburg_mitte}")
    private String hamburgMitte;

    @Value("${server_id}")
    private String server_Id;

    public void sendOfferToChannel(Angebot angebot) {

        bot.getJda().getGuildById(server_Id).getTextChannelById(getTextChannel(angebot)).sendMessageEmbeds(embedErsteller.createOfferEmbed(angebot).build()).queue();
    }

    public String getTextChannel(Angebot angebot) {
        String abholungsOrt = angebot.getAbholungsort();

        switch (abholungsOrt){
            case "hamburg-ost":
                return hamburgOst;
            case "hamburg-west":
                return hamburgWest;
            case "hamburg-nord":
                return hamburgNord;
            case "hamburg-süd":
                return hamburgSued;
            case "hamburg-mitte":
                return hamburgMitte;
            default:
                return hamburgMitte;
        }
    }
}
