package com.tastybot.tastybot.discord;

import com.tastybot.tastybot.business_logic.OfferHandler;
import com.tastybot.tastybot.database.entity.Applicant;
import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.ApplicantRepository;
import com.tastybot.tastybot.database.repository.OfferRepository;
import com.tastybot.tastybot.database.service.OfferService;
import lombok.RequiredArgsConstructor;
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


/**
 * Listens for Discord events and responds accordingly.
 */
@Component
@RequiredArgsConstructor
public class EventListener extends ListenerAdapter {

    /**
     * Service responsible for managing offer-related operations.
     */
    private final OfferService offerService;

    /**
     * Handler class for managing operations related to offers.
     */
    private final OfferHandler offerHandler;

    /**
     * Repository for accessing and managing offer entities in the database.
     */
    private final OfferRepository offerRepository;

    /**
     * Repository for accessing and managing applicant entities in the database.
     */
    private final ApplicantRepository applicantRepository;

    /**
     * Called when the guild is fully loaded and ready.
     *
     * @param event The GuildReadyEvent triggered when the guild is ready.
     */
    @Override
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

    /**
     * Called when a slash command is invoked.
     *
     * @param event The SlashCommandInteractionEvent triggered when a slash command is invoked.
     */
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("erstelle-angebot")) {

            OptionMapping offerTitleOption = event.getOption("angebotstitel");
            assert offerTitleOption != null;
            String offerTitle = offerTitleOption.getAsString();

            String preference;
            OptionMapping preferenceOption = event.getOption("praeferenzen");

            if (preferenceOption != null){
                preference = preferenceOption.getAsString();
            }else  {
                preference = "-";
            }

            String availableFrom;
            OptionMapping availableFromOption = event.getOption("verfuegbar-ab");

            if (availableFromOption != null){
                availableFrom = availableFromOption.getAsString();
            }else  {
                availableFrom = "-";
            }

            if(!availableFrom.equals("-")) {
                if (!validateDate(availableFrom)) {
                    event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                    return;
                }
            }

            String availableUntil;
            OptionMapping availableUntilOption = event.getOption("verfuegbar-bis");

            if (availableUntilOption != null){
                availableUntil = availableUntilOption.getAsString();
            }else  {
                availableUntil = "-";
            }

            if(!availableUntil.equals("-")) {
                if (!validateDate(availableUntil)) {
                    event.reply("Das Datum muss im Format dd.MM.yyyy sein! Bitte versuche es erneut.").setEphemeral(true).queue();
                    return;
                }
            }

            String notes;
            OptionMapping notesOption = event.getOption("anmerkungen");

            if (notesOption != null){
                notes = notesOption.getAsString();
            }else  {
                notes = "-";
            }

            OptionMapping pickupLocationOption = event.getOption("abholungsort");
            assert pickupLocationOption != null;
            String pickupLocation = pickupLocationOption.getAsString();

            String photo;
            OptionMapping photoOption = event.getOption("foto");

            if (photoOption != null){
                if(photoOption.getAsAttachment().isImage()) {
                    photo = photoOption.getAsAttachment().getProxyUrl();
                }else {
                    photo = null;
                }
            }else  {
                photo = null;
            }

            Offer offer = offerService.saveOfferInDatabase(offerTitle, preference, availableFrom, availableUntil, notes, pickupLocation, event.getUser().getIdLong(), photo);

            offerHandler.confirmOffer(event, offer);

            event.reply("JUHU 😁 TastyBot hat dein Angebot erfolgreich angenommen!").setEphemeral(true).queue();
        }
    }

    /**
     * Called when a button interaction occurs.
     *
     * @param event The ButtonInteractionEvent triggered when a button is interacted with.
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("interessiert")) {

            Offer offer = offerRepository.findByOfferMessageId(event.getInteraction().getMessageIdLong());

            if (applicantRepository.findByUserIdAndOffer(event.getUser().getIdLong(), offer) != null){
               event.reply("Du hast dich für dieses Angebot schon als 'Interessiert' gemeldet!").setEphemeral(true).queue();
               return;
            }

            if (event.getUser().getIdLong() == offer.getUserId()){
                event.reply("Du kannst dich nicht bei deinem eigenen Angebot als 'Interessiert' melden!").setEphemeral(true).queue();
                return;
            }

            event.reply("Du hast dich erfolgreich für das Angebot '" + offer.getOfferTitle() + "' als interessiert gemeldet! Wir haben den Angebotsersteller benachrichtigt und ihm deinen Discord Usernamen mitgeteilt. Er wird nun mit dir in Kontakt treten, damit ihr die Einzelheiten klären könnt!").setEphemeral(true).queue();
            offerHandler.notifyOfferCreator(offer, event.getUser());

            Applicant applicant = new Applicant();
            applicant.setUserId(event.getUser().getIdLong());
            applicant.setOffer(offer);

            offer.getApplicants().add(applicant);

            applicantRepository.save(applicant);
            offerRepository.save(offer);

            offerHandler.updateOffer(offer);

        } else if (event.getComponentId().equals("angebot-loeschen")) {
            Offer offer = offerRepository.findByOfferDeleteMessageId(event.getInteraction().getMessageIdLong());
            offerHandler.deleteOffer(offer);
            event.reply("Das Angebot wurde erfolgreich gelöscht!").queue();
        }
    }

    /**
     * Validates the format of the date.
     *
     * @param date The date to be validated.
     * @return True if the date is valid, false otherwise.
     */
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
