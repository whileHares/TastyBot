package com.tastybot.tastybot.BusinessLogic;

import com.tastybot.tastybot.Database.Angebot;
import com.tastybot.tastybot.Database.AngebotRepository;
import com.tastybot.tastybot.Discord.Bot;
import com.tastybot.tastybot.Discord.EmbedErsteller;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class AngebotsErsteller {
    private Bot bot;

   public void setBot(Bot bot){
       this.bot = bot;
   }

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

    public Angebot sendOfferToChannel(Angebot angebot) {

        bot.getJda().getGuildById(server_Id).getTextChannelById(getTextChannel(angebot)).sendMessageEmbeds(embedErsteller.createOfferEmbed(angebot).build()).addActionRow(
                Button.primary("interessiert", "Interessiert")).queue((message) -> {
                    Angebot newAngebot = angebotRepository.findById(angebot.getAngebotId()).orElse(null);
                    newAngebot.setAngebotsNachrichtenId(message.getIdLong());
                    angebotRepository.save(newAngebot);
                });
        return angebot;
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
