package com.tastybot.tastybot.Discord;

import com.tastybot.tastybot.Database.Angebot;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class EmbedErsteller {

    public EmbedBuilder createOfferEmbed(Angebot angebot) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(angebot.getAngebotstitel() , null);

        eb.setDescription("Vegan:" + angebot.getVegan() + "\n" + "Verfügbar ab: " + angebot.getVerfuegbarAb() + "\n" + "Verfügbar bis: " + angebot.getVerfuegbarBis() + "\n" + "Anmerkungen: " + angebot.getAnmerkungen());

        eb.setColor(new Color(73, 152, 101));

        eb.setFooter("© TastyBot");
        //eb.setImage();
        eb.setTimestamp(Instant.now());

        return eb;
    }
}
