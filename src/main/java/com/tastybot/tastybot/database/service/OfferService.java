package com.tastybot.tastybot.database.service;

import com.tastybot.tastybot.business_logic.OfferCreator;
import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service providing methods for managing offers in the database.
 */
@Service
@RequiredArgsConstructor
public class OfferService {

    /**
     * The repository for offers.
     */
    private final OfferRepository offerRepository;

    /**
     * The creator for offers.
     */
    private final OfferCreator offerCreator;

    /**
     * Saves an offer in the database and sends it to a channel.
     *
     * @param offerTitle       Title of the offer.
     * @param preference       Preference for the offer.
     * @param availableFrom    Available from date/time.
     * @param availableUntil   Available until date/time.
     * @param notes            Additional notes for the offer.
     * @param pickupLocation   Pickup location of the offer.
     * @param userId           ID of the user creating the offer.
     * @param photo            URL of the photo for the offer.
     * @return The saved offer.
     */
    public Offer saveOfferInDatabase(String offerTitle, String preference, String availableFrom, String availableUntil, String notes, String pickupLocation, Long userId, String photo) {

        Offer offer = new Offer();
        offer.setOfferTitle(offerTitle);
        offer.setPreference(preference);
        offer.setAvailableFrom(availableFrom);
        offer.setAvailableUntil(availableUntil);
        offer.setNotes(notes);
        offer.setPickupLocation(pickupLocation);
        offer.setUserId(userId);
        offer.setPhotoUrl(photo);

        // Sends the offer to a channel using the offer creator.
        return offerCreator.sendOfferToChannel(offerRepository.save(offer));
    }
}

