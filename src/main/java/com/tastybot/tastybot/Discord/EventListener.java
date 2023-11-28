package com.tastybot.tastybot.Discord;

import com.tastybot.tastybot.Database.AngebotService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class EventListener extends ListenerAdapter {

    private final AngebotService angebotService;

    @Override //Server
    public void onGuildReady(GuildReadyEvent event) {
        ArrayList<CommandData> commandDataArrayList = new ArrayList<>();
        commandDataArrayList.add(Commands.slash("erstelle-angebot", "Erstelle ein Angebot")
                .addOptions(
                        new OptionData(OptionType.STRING, "angebotstitel", "Hier kommt dein Angebotstitel hin. Beispiel: '3 Brötchen in Altona'", true),
                        new OptionData(OptionType.BOOLEAN, "vegan", "Vegan oder nicht vegan?", true),
                        new OptionData(OptionType.STRING, "verfuegbar-ab", "Ab wann ist das Angebot verfügbar? Bitte gib ein Datum an im Format dd.MM.yyyy", true),
                        new OptionData(OptionType.STRING, "verfuegbar-bis", "Bis wann ist das Angebot verfügbar? Bitte gib ein Datum an im Format dd.MM.yyyy", true),
                        new OptionData(OptionType.STRING, "anmerkungen", "Gibt es etwas zu beachten? Beispiel: '3. Stock ohne Fahrstuhl'", true),
                        new OptionData(OptionType.STRING, "abholungsort", "Wo kann man dein Angebot abholen?", true)
                                .addChoice("hamburg-ost", "hamburg-ost")
                                .addChoice("hamburg-west", "hamburg-west")
                                .addChoice("hamburg-sued", "hamburg-süd")
                                .addChoice("hamburg-nord", "hamburg-nord")
                                .addChoice("hamburg-mitte", "hamburg-mitte")
                )
        );

        event.getGuild().updateCommands().addCommands(commandDataArrayList).queue();
    }

    @Override //Slash-Command-Events
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("erstelle-angebot")) {

            //angebotstitel
            OptionMapping angebotstitelOption = event.getOption("angebotstitel");
            assert angebotstitelOption != null;
            String angebotstitel = angebotstitelOption.getAsString();

            //vegan
            OptionMapping veganOption = event.getOption("vegan");
            assert veganOption != null;
            Boolean vegan = veganOption.getAsBoolean();

            //verfugbar ab
            OptionMapping verfuegbarAbOption = event.getOption("verfuegbar-ab");
            assert verfuegbarAbOption != null;
            String verfuegbarAb = verfuegbarAbOption.getAsString();

            if (!validateDate(verfuegbarAb)) {
                event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                return;
            }

            //verfugbar bis
            OptionMapping verfuegbarBisOption = event.getOption("verfuegbar-bis");
            assert verfuegbarBisOption != null;
            String verfuegbarBis = verfuegbarBisOption.getAsString();

            if (!validateDate(verfuegbarBis)) {
                event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                return;
            }

            //anmerkungen
            OptionMapping anmerkungenOption = event.getOption("anmerkungen");
            assert anmerkungenOption != null;
            String anmerkungen = anmerkungenOption.getAsString();

            //abholungsort
            OptionMapping abholungsortOption = event.getOption("abholungsort");
            assert abholungsortOption != null;
            String abholungsort = abholungsortOption.getAsString();

            System.out.println(
                    "Angebotstitel: " + angebotstitel + "\n" +
                    "Vegan: " + vegan + "\n" +
                    "Verfügbar ab: " + verfuegbarAb + "\n" +
                    "Verfügbar bis: " + verfuegbarBis + "\n" +
                    "Anmerkungen: " + anmerkungen + "\n" +
                    "Abholungsort: " + abholungsort
            );

            angebotService.saveOfferInDatabase(angebotstitel, vegan, verfuegbarAb, verfuegbarBis, anmerkungen, abholungsort, event.getUser().getIdLong());

            event.reply("JUHU 😁 TastyBot hat dein Angebot erfolgreich angenommen!").setEphemeral(true).queue();
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
