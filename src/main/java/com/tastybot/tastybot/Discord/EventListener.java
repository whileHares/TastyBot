package com.tastybot.tastybot.Discord;

import com.tastybot.tastybot.BusinessLogic.AngebotsHandler;
import com.tastybot.tastybot.Database.*;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventListener extends ListenerAdapter {

    private final AngebotService angebotService;
    private final AngebotsHandler angebotsHandler;
    private final AngebotRepository angebotRepository;
    private final InteressentRepository interessentRepository;

    @Override //Server
    public void onGuildReady(GuildReadyEvent event) {
        ArrayList<CommandData> commandDataArrayList = new ArrayList<>();
        commandDataArrayList.add(Commands.slash("erstelle-angebot", "Erstelle ein Angebot")
                .addOptions(
                        new OptionData(OptionType.STRING, "angebotstitel", "Hier kommt dein Angebotstitel hin. Beispiel: '3 Brötchen in Altona'", true),
                        new OptionData(OptionType.STRING, "abholungsort", "Wo kann man dein Angebot abholen?", true)
                                .addChoice("hamburg-ost", "hamburg-ost")
                                .addChoice("hamburg-west", "hamburg-west")
                                .addChoice("hamburg-sued", "hamburg-süd")
                                .addChoice("hamburg-nord", "hamburg-nord")
                                .addChoice("hamburg-mitte", "hamburg-mitte"),
                        new OptionData(OptionType.STRING, "praeferenzen", "Erfüllt dein Angebot Ernährungspräferenzen wie zum Beispiel 'vegan'?", false)
                                .addChoice("vegetarisch", "vegetarisch")
                                .addChoice("vegan", "vegan")
                                .addChoice("low-carb", "low carb")
                                .addChoice("paleo", "paleo")
                                .addChoice("glutenfrei", "glutenfrei")
                                .addChoice("makrobiotisch", "makrobiotisch")
                                .addChoice("pescetarisch", "pescetarisch"),
                        new OptionData(OptionType.STRING, "verfuegbar-ab", "Ab wann ist das Angebot verfügbar? Bitte gib ein Datum an im Format dd.MM.yyyy", false),
                        new OptionData(OptionType.STRING, "verfuegbar-bis", "Bis wann ist das Angebot verfügbar? Bitte gib ein Datum an im Format dd.MM.yyyy", false),
                        new OptionData(OptionType.STRING, "anmerkungen", "Gibt es etwas zu beachten? Beispiel: '3. Stock ohne Fahrstuhl'", false),
                        new OptionData(OptionType.ATTACHMENT, "foto", "Lade hier ein Foto von den Köstlichkeiten hoch.", false)
                )
        );

        event.getGuild().updateCommands().addCommands(commandDataArrayList).queue();
    }

    @Override //Slash-Command-Events
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("erstelle-angebot")) {

            //angebotstitel
            OptionMapping angebotstitelOption = event.getOption("angebotstitel");
            assert angebotstitelOption != null;
            String angebotstitel = angebotstitelOption.getAsString();

            //preference
            String preference;
            OptionMapping preferenceOption = event.getOption("praeferenzen");

            if (preferenceOption != null){
                preference = preferenceOption.getAsString();
            }else  {
                preference = "-";
            }


            //verfugbar ab
            String verfuegbarAb;
            OptionMapping verfuegbarAbOption = event.getOption("verfuegbar-ab");

            if (verfuegbarAbOption != null){
                verfuegbarAb = verfuegbarAbOption.getAsString();
            }else  {
                verfuegbarAb = "-";
            }

            if(!verfuegbarAb.equals("-")) {
                if (!validateDate(verfuegbarAb)) {
                    event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                    return;
                }
            }

            //verfugbar bis
            String verfuegbarBis;
            OptionMapping verfuegbarBisOption = event.getOption("verfuegbar-bis");

            if (verfuegbarBisOption != null){
                verfuegbarBis = verfuegbarBisOption.getAsString();
            }else  {
                verfuegbarBis = "-";
            }

            if(!verfuegbarBis.equals("-")) {
                if (!validateDate(verfuegbarBis)) {
                    event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                    return;
                }
            }

            //anmerkungen
            String anmerkungen;
            OptionMapping anmerkungenOption = event.getOption("anmerkungen");

            if (anmerkungenOption != null){
                anmerkungen = anmerkungenOption.getAsString();
            }else  {
                anmerkungen = "-";
            }

            //abholungsort
            OptionMapping abholungsortOption = event.getOption("abholungsort");
            assert abholungsortOption != null;
            String abholungsort = abholungsortOption.getAsString();

            //foto
            OptionMapping fotoOption = event.getOption("foto");
            String foto;

            if (fotoOption != null){
                if(fotoOption.getAsAttachment().isImage()) {
                    foto = fotoOption.getAsAttachment().getProxyUrl();
                }else {
                    foto = null;
                }
            }else  {
                foto = null;
            }

            System.out.println(
                    "Angebotstitel: " + angebotstitel + "\n" +
                            "Präferenz: " + preference + "\n" +
                            "Verfügbar ab: " + verfuegbarAb + "\n" +
                            "Verfügbar bis: " + verfuegbarBis + "\n" +
                            "Anmerkungen: " + anmerkungen + "\n" +
                            "Abholungsort: " + abholungsort
            );

            Angebot angebot = angebotService.saveOfferInDatabase(angebotstitel, preference, verfuegbarAb, verfuegbarBis, anmerkungen, abholungsort, event.getUser().getIdLong(), foto);

            angebotsHandler.confirmOffer(event, angebot);

            event.reply("JUHU 😁 TastyBot hat dein Angebot erfolgreich angenommen!").setEphemeral(true).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("interessiert")) {

            Angebot angebot = angebotRepository.findByAngebotsNachrichtenId(event.getInteraction().getMessageIdLong());

            if (interessentRepository.findByUserIdAndAngebot(event.getUser().getIdLong(), angebot) != null){
               event.reply("Du hast dich für dieses Angebot schon als 'Interessiert' gemeldet!").setEphemeral(true).queue();
               return;
            }

            event.reply("Du hast dich erfolgreich für das Angebot '" + angebot.getAngebotstitel() + "' als interessiert gemeldet! Wir haben den Angebotsersteller benachrichtigt und ihm deinen Discord Usernamen mitgeteilt. Er wird nun mit dir in Kontakt treten, damit ihr die Einzelheiten klären könnt!").setEphemeral(true).queue();
            angebotsHandler.notifyOfferCreator(angebot, event.getUser());

            Interessent interessent = new Interessent();
            interessent.setUserId(event.getUser().getIdLong());
            interessent.setAngebot(angebot);

            angebot.getInteressenten().add(interessent);

            interessentRepository.save(interessent);
            angebotRepository.save(angebot);

            angebotsHandler.updateOffer(angebot);

        } else if (event.getComponentId().equals("angebot-loeschen")) {
            Angebot angebot = angebotRepository.findByAngebotLoeschenNachrichtenId(event.getInteraction().getMessageIdLong());
            angebotsHandler.deleteOffer(angebot);
            event.reply("Das Angebot wurde erfolgreich gelöscht!").queue();
        }
    }

    private boolean validateDate(String date) {
        if (date.length() == 10) {
            if (Integer.parseInt(date.substring(0, 2)) < 32 && Integer.parseInt(date.substring(0, 2)) > 0) {
                if (Integer.parseInt(date.substring(3, 5)) < 13 && Integer.parseInt(date.substring(3, 5)) > 0) {
                    return Integer.parseInt(date.substring(6, 10)) > 2022;
                }
            }
        }
        return false;
    }
}
