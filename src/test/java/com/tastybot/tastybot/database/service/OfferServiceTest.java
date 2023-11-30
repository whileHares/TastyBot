package com.tastybot.tastybot.database.service;

import com.tastybot.tastybot.business_logic.OfferCreator;
import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.OfferRepository;

import com.tastybot.tastybot.database.service.OfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferCreator offerCreator;

    @InjectMocks
    private OfferService offerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveOfferInDatabase() {
        // Arrange
        String offerTitle = "Test Offer";
        String preference = "vegan";
        String availableFrom = "01.01.2023";
        String availableUntil = "31.01.2023";
        String notes = "Sample notes";
        String pickupLocation = "hamburg-west";
        Long userId = 123456789L;
        String photo = "http://example.com/photo.jpg";

        Offer offer = new Offer();
        offer.setOfferTitle(offerTitle);
        offer.setPreference(preference);
        offer.setAvailableFrom(availableFrom);
        offer.setAvailableUntil(availableUntil);
        offer.setNotes(notes);
        offer.setPickupLocation(pickupLocation);
        offer.setUserId(userId);
        offer.setPhotoUrl(photo);

        // Mock the behavior of the offerRepository.save method
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        // Mock the behavior of the offerCreator.sendOfferToChannel method
        when(offerCreator.sendOfferToChannel(any(Offer.class))).thenReturn(offer);

        // Act
        Offer result = offerService.saveOfferInDatabase(offerTitle, preference, availableFrom, availableUntil, notes, pickupLocation, userId, photo);

        // Assert
        verify(offerRepository, times(1)).save(any(Offer.class));
        verify(offerCreator, times(1)).sendOfferToChannel(any(Offer.class));

        assertEquals(offer, result, "The returned offer should match the expected offer");
    }
}

