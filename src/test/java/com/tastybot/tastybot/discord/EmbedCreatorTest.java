package com.tastybot.tastybot.discord;

import com.tastybot.tastybot.database.entity.Offer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EmbedCreatorTest {

    @Test
    void testCreateOfferEmbed() {
        // Arrange
        Offer offer = new Offer();
        offer.setOfferTitle("Test Offer");
        offer.setPreference("Vegetarian");
        offer.setAvailableFrom("2023-01-01");
        offer.setAvailableUntil("2023-01-10");
        offer.setNotes("Test notes");
        offer.setApplicants(new ArrayList<>());

        EmbedCreator embedCreator = new EmbedCreator();

        // Act
        MessageEmbed result = embedCreator.createOfferEmbed(offer).build();

        // Assert
        assertEquals("Test Offer", result.getTitle());
        assertEquals(new Color(73, 152, 101), result.getColor());
        assertEquals("© TastyBot", result.getFooter().getText());
        assertEquals("https://i.imgur.com/r85wmLK.png", result.getThumbnail().getUrl());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void testCreateOfferInterestedNotification() {
        // Arrange
        Offer offer = new Offer();
        offer.setOfferTitle("Test Offer");

        User user = mock(User.class);
        when(user.getGlobalName()).thenReturn("TestUser");

        EmbedCreator embedCreator = new EmbedCreator();

        // Act
        MessageEmbed result = embedCreator.createOfferInterestedNotification(offer, user).build();

        // Assert
        assertEquals("Der Nutzer 'TestUser' hat Interesse an deinem Angebot 'Test Offer'", result.getTitle());
        assertEquals(new Color(73, 152, 101), result.getColor());
        assertEquals("© TastyBot", result.getFooter().getText());
        assertEquals("https://i.imgur.com/r85wmLK.png", result.getThumbnail().getUrl());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void testCreateOfferCreationNotification() {
        // Arrange
        Offer offer = new Offer();
        offer.setOfferTitle("Test Offer");

        EmbedCreator embedCreator = new EmbedCreator();

        // Act
        MessageEmbed result = embedCreator.createOfferCreationNotification(offer).build();

        // Assert
        assertEquals("Du hast das Angebot 'Test Offer' erstellt", result.getTitle());
        assertEquals(new Color(73, 152, 101), result.getColor());
        assertEquals("© TastyBot", result.getFooter().getText());
        assertEquals("https://i.imgur.com/r85wmLK.png", result.getThumbnail().getUrl());
        assertNotNull(result.getTimestamp());
    }
}

