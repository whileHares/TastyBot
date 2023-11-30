package com.tastybot.tastybot.database.repository;

import com.tastybot.tastybot.database.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Offer entities in the database.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

    /**
     * Retrieves an offer by its associated message ID.
     *
     * @param offerMessageId The message ID associated with the offer.
     * @return The offer with the specified message ID.
     */
    Offer findByOfferMessageId(Long offerMessageId);

    /**
     * Retrieves an offer by its associated delete message ID.
     *
     * @param offerDeleteMessageId The delete message ID associated with the offer.
     * @return The offer with the specified delete message ID.
     */
    Offer findByOfferDeleteMessageId(Long offerDeleteMessageId);
}

