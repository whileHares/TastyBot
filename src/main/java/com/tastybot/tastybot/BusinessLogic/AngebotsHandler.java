package com.tastybot.tastybot.BusinessLogic;

import com.tastybot.tastybot.Database.Angebot;
import com.tastybot.tastybot.Database.AngebotRepository;
import com.tastybot.tastybot.Discord.Bot;
import com.tastybot.tastybot.Discord.EmbedErsteller;
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
public class AngebotsHandler {

    private final EmbedErsteller embedErsteller;
    private final AngebotRepository angebotRepository;
    private final AngebotsErsteller angebotsErsteller;

    @Value("${server_id}")
    private String server_Id;

    private Bot bot;

    public void setBot(Bot bot){
        this.bot = bot;
    }

    public void notifyOfferCreator(Angebot angebot, User user) {

        bot.getJda().retrieveUserById(angebot.getUserId()).queue(customer -> customer.openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedErsteller.createOfferInterestedNotification(angebot, user).build()))
                .queue());

    }

    public void confirmOffer(SlashCommandInteractionEvent event, Angebot angebot){
        event.getUser().openPrivateChannel()
                .flatMap(channel -> channel.sendMessageEmbeds(embedErsteller.createOfferCreationNotification(angebot).build()).addActionRow(Button.danger("angebot-loeschen", "Angebot löschen")))
                .queue((message) -> {
                    Angebot newAngebot = angebotRepository.findById(angebot.getAngebotId()).orElse(null);
                    newAngebot.setAngebotLoeschenNachrichtenId(message.getIdLong());
                    angebotRepository.save(newAngebot);
                });
    }

    public void deleteOffer(Angebot angebot) {
        bot.getJda().getGuildById(server_Id).getTextChannelById(angebotsErsteller.getTextChannel(angebot)).retrieveMessageById(angebot.getAngebotsNachrichtenId()).queue(message -> message.delete().queue());
    }

    public void updateOffer(Angebot angebot){
        bot.getJda().getGuildById(server_Id).getTextChannelById(angebotsErsteller.getTextChannel(angebot)).retrieveMessageById(angebot.getAngebotsNachrichtenId()).queue(message -> message.editMessageEmbeds(embedErsteller.createOfferEmbed(angebot).build()).queue());
    }
}
