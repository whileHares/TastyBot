package com.tastybot.tastybot.database.service;

import com.tastybot.tastybot.business_logic.OfferCreator;
import com.tastybot.tastybot.database.entity.Offer;
import com.tastybot.tastybot.database.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    private final OfferCreator offerCreator;

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

        return offerCreator.sendOfferToChannel(offerRepository.save(offer));
    }
}
